package resources;

import java.util.List;

import dao.implementations.LogDAO;
import exception.DuplicateEntityException;
import exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.media.*;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import models.Log;

@Path("/logs")
@Tag(name = "Logs", description = "Operations related to logs")
public class LogResource {

	@Inject
	private LogDAO logDAO;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get all logs", responses = {
			@ApiResponse(description = "List of logs", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Log.class))),
			@ApiResponse(description = "Internal Server Error", responseCode = "500") })
	public Response getLogs() {
		try {
			List<Log> logs = logDAO.getAll();
			return Response.ok(logs).build();
		} catch (Exception e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Operation(summary = "Get log by ID", description = "Retrieves a log by their ID.")
	@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Log.class)))
	@ApiResponse(responseCode = "404", description = "Log not found")
	public Response getLogById(@PathParam("id") Long id) {
		try {
			Log log = logDAO.get(id);

			return Response.ok(log).build();
		} catch (Exception e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.NOT_FOUND).entity(errorResponse).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Create a new log", description = "Creates a new log.")
	@ApiResponse(responseCode = "201", description = "Log created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Log.class)))
	@ApiResponse(responseCode = "500", description = "Internal server error")
	public Response saveLog(Log log) {
		try {
			logDAO.save(log);
			return Response.status(Status.CREATED).entity(log).build();
		} catch (DuplicateEntityException e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.CONFLICT).entity(errorResponse).build();
		} catch (Exception e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Update an existing log", description = "Updates an existing log.")
	@ApiResponse(responseCode = "200", description = "Log updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Log.class)))
	@ApiResponse(responseCode = "500", description = "Internal server error")
	public Response updateLog(Log updatedLog) {
		try {
			logDAO.update(updatedLog, null);
			return Response.ok(updatedLog).build();
		} catch (Exception e) {
			System.err.println(e);
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
		}
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Operation(summary = "Delete a log by ID", description = "Deletes a log by their ID.")
	@ApiResponse(responseCode = "204", description = "Log deleted")
	@ApiResponse(responseCode = "404", description = "Log not found")
	public Response deleteLog(@PathParam("id") Long id) {
		try {
			logDAO.delete(id); // Eliminar de la base de datos
			return Response.noContent().build();
		} catch (Exception e) {
			System.err.println(e);
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
		}

	}

}
