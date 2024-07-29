package resources;

import java.util.List;

import dao.implementations.StockResourceDAO;
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
import models.StockResource;

@Path("/stockResources")
@Tag(name = "StockResources", description = "Operations related to stockResources")
public class StockResourceResource {

	@Inject
	private StockResourceDAO stockResourceDAO;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get all stockResources", responses = {
			@ApiResponse(description = "List of stockResources", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StockResource.class))),
			@ApiResponse(description = "Internal Server Error", responseCode = "500") })
	public Response getStockResources() {
		try {
			List<StockResource> stockResources = stockResourceDAO.getAll();
			return Response.ok(stockResources).build();
		} catch (Exception e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Operation(summary = "Get stockResource by ID", description = "Retrieves a stockResource by their ID.")
	@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StockResource.class)))
	@ApiResponse(responseCode = "404", description = "StockResource not found")
	public Response getStockResourceById(@PathParam("id") Long id) {
		try {
			StockResource stockResource = stockResourceDAO.get(id);

			return Response.ok(stockResource).build();
		} catch (Exception e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.NOT_FOUND).entity(errorResponse).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Create a new stockResource", description = "Creates a new stockResource.")
	@ApiResponse(responseCode = "201", description = "StockResource created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StockResource.class)))
	@ApiResponse(responseCode = "500", description = "Internal server error")
	public Response saveStockResource(StockResource stockResource) {
		try {
			stockResourceDAO.save(stockResource);
			return Response.status(Status.CREATED).entity(stockResource).build();
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
	@Operation(summary = "Update an existing stockResource", description = "Updates an existing stockResource.")
	@ApiResponse(responseCode = "200", description = "StockResource updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StockResource.class)))
	@ApiResponse(responseCode = "500", description = "Internal server error")
	public Response updateStockResource(StockResource updatedStockResource) {
		try {
			stockResourceDAO.update(updatedStockResource, null);
			return Response.ok(updatedStockResource).build();
		} catch (Exception e) {
			System.err.println(e);
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
		}
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Operation(summary = "Delete a stockResource by ID", description = "Deletes a stockResource by their ID.")
	@ApiResponse(responseCode = "204", description = "StockResource deleted")
	@ApiResponse(responseCode = "404", description = "StockResource not found")
	public Response deleteStockResource(@PathParam("id") Long id) {
		try {
			stockResourceDAO.delete(id); // Eliminar de la base de datos
			return Response.noContent().build();
		} catch (Exception e) {
			System.err.println(e);
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
		}

	}

}
