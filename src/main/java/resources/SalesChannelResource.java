package resources;

import java.util.List;

import dao.implementations.SalesChannelDAO;
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
import models.SalesChannel;

@Path("/salesChannels")
@Tag(name = "SalesChannels", description = "Operations related to salesChannels")
public class SalesChannelResource {

	@Inject
	private SalesChannelDAO salesChannelDAO;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get all salesChannels", responses = {
			@ApiResponse(description = "List of salesChannels", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SalesChannel.class))),
			@ApiResponse(description = "Internal Server Error", responseCode = "500") })
	public Response getSalesChannels() {
		try {
			List<SalesChannel> salesChannels = salesChannelDAO.getAll();
			return Response.ok(salesChannels).build();
		} catch (Exception e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Operation(summary = "Get salesChannel by ID", description = "Retrieves a salesChannel by their ID.")
	@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SalesChannel.class)))
	@ApiResponse(responseCode = "404", description = "SalesChannel not found")
	public Response getSalesChannelById(@PathParam("id") Long id) {
		try {
			SalesChannel salesChannel = salesChannelDAO.get(id);

			return Response.ok(salesChannel).build();
		} catch (Exception e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.NOT_FOUND).entity(errorResponse).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Create a new salesChannel", description = "Creates a new salesChannel.")
	@ApiResponse(responseCode = "201", description = "SalesChannel created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SalesChannel.class)))
	@ApiResponse(responseCode = "500", description = "Internal server error")
	public Response saveSalesChannel(SalesChannel salesChannel) {
		try {
			salesChannelDAO.save(salesChannel);
			return Response.status(Status.CREATED).entity(salesChannel).build();
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
	@Operation(summary = "Update an existing salesChannel", description = "Updates an existing salesChannel.")
	@ApiResponse(responseCode = "200", description = "SalesChannel updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SalesChannel.class)))
	@ApiResponse(responseCode = "500", description = "Internal server error")
	public Response updateSalesChannel(SalesChannel updatedSalesChannel) {
		try {
			salesChannelDAO.update(updatedSalesChannel, null);
			return Response.ok(updatedSalesChannel).build();
		} catch (Exception e) {
			System.err.println(e);
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
		}
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Operation(summary = "Delete a salesChannel by ID", description = "Deletes a salesChannel by their ID.")
	@ApiResponse(responseCode = "204", description = "SalesChannel deleted")
	@ApiResponse(responseCode = "404", description = "SalesChannel not found")
	public Response deleteSalesChannel(@PathParam("id") Long id) {
		try {
			salesChannelDAO.delete(id); // Eliminar de la base de datos
			return Response.noContent().build();
		} catch (Exception e) {
			System.err.println(e);
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
		}

	}

}
