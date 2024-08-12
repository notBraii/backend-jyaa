package resources;

import java.util.Date;
import java.util.List;

import dao.implementations.StockRawMaterialDAO;
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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import models.StockRawMaterial;

@Path("/stockRawMaterials")
@Tag(name = "StockRawMaterials", description = "Operations related to stockRawMaterials")
public class StockRawMaterialResource {

	@Inject
	private StockRawMaterialDAO stockRawMaterialDAO;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get all stockRawMaterials", responses = {
			@ApiResponse(description = "List of stockRawMaterials", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StockRawMaterial.class))),
			@ApiResponse(description = "Internal Server Error", responseCode = "500") })
	public Response getStockRawMaterials() {
		try {
			List<StockRawMaterial> stockRawMaterials = stockRawMaterialDAO.getAll();
			return Response.ok(stockRawMaterials).build();
		} catch (Exception e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Operation(summary = "Get stockRawMaterial by ID", description = "Retrieves a stockRawMaterial by their ID.")
	@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StockRawMaterial.class)))
	@ApiResponse(responseCode = "404", description = "StockRawMaterial not found")
	public Response getStockRawMaterialById(@PathParam("id") Long id) {
		try {
			StockRawMaterial stockRawMaterial = stockRawMaterialDAO.get(id);

			return Response.ok(stockRawMaterial).build();
		} catch (Exception e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.NOT_FOUND).entity(errorResponse).build();
		}
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Search stockRawMaterial with pagination", description = "Search stockRawMaterial by various attributes with pagination.")
	@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StockRawMaterial.class)))
	@ApiResponse(responseCode = "500", description = "Internal server error")
	public Response searchProducts(@QueryParam("resourceTagName") String resourceTagName,
	                               @QueryParam("expiredAt") Date expiredAt
//	                               @QueryParam("page") @DefaultValue("1") int page, 
//	                               @QueryParam("size") @DefaultValue("5") int size
	                               ) {
	    try {
	        List<StockRawMaterial> productGroups = stockRawMaterialDAO.search(resourceTagName, expiredAt);
	        return Response.ok(productGroups).build();
	    } catch (Exception e) {
	        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
	        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
	    }
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Create a new stockRawMaterial", description = "Creates a new stockRawMaterial.")
	@ApiResponse(responseCode = "201", description = "StockRawMaterial created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StockRawMaterial.class)))
	@ApiResponse(responseCode = "500", description = "Internal server error")
	public Response saveStockRawMaterial(StockRawMaterial stockRawMaterial) {
		try {
			stockRawMaterialDAO.save(stockRawMaterial);
			return Response.status(Status.CREATED).entity(stockRawMaterial).build();
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
	@Operation(summary = "Update an existing stockRawMaterial", description = "Updates an existing stockRawMaterial.")
	@ApiResponse(responseCode = "200", description = "StockRawMaterial updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StockRawMaterial.class)))
	@ApiResponse(responseCode = "500", description = "Internal server error")
	public Response updateStockRawMaterial(StockRawMaterial updatedStockRawMaterial) {
		try {
			stockRawMaterialDAO.update(updatedStockRawMaterial, null);
			return Response.ok(updatedStockRawMaterial).build();
		} catch (Exception e) {
			System.err.println(e);
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
		}
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Operation(summary = "Delete a stockRawMaterial by ID", description = "Deletes a stockRawMaterial by their ID.")
	@ApiResponse(responseCode = "204", description = "StockRawMaterial deleted")
	@ApiResponse(responseCode = "404", description = "StockRawMaterial not found")
	public Response deleteStockRawMaterial(@PathParam("id") Long id) {
		try {
			stockRawMaterialDAO.delete(id); // Eliminar de la base de datos
			return Response.noContent().build();
		} catch (Exception e) {
			System.err.println(e);
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
		}

	}

}
