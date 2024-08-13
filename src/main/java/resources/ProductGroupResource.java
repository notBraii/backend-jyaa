package resources;

import java.util.List;

import dao.implementations.ProductGroupDAO;
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
import models.ProductGroup;

@Path("/productGroups")
@Tag(name = "ProductGroups", description = "Operations related to productGroups")
public class ProductGroupResource {

	@Inject
	private ProductGroupDAO productGroupDAO;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get all productGroups", responses = {
			@ApiResponse(description = "List of productGroups", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductGroup.class))),
			@ApiResponse(description = "Internal Server Error", responseCode = "500") })
	public Response getProductGroups() {
		try {
			List<ProductGroup> productGroups = productGroupDAO.getAll();
			return Response.ok(productGroups).build();
		} catch (Exception e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
		}
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Search productGroups with pagination", description = "Search products by various attributes with pagination.")
	@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductGroup.class)))
	@ApiResponse(responseCode = "500", description = "Internal server error")
	public Response searchProducts(@QueryParam("name") String name,
	                               @QueryParam("categoryName") String categoryName
//	                               @QueryParam("page") @DefaultValue("1") int page, 
//	                               @QueryParam("size") @DefaultValue("5") int size
	                               ) {
	    try {
	        List<ProductGroup> productGroups = productGroupDAO.search(name, categoryName);
	        return Response.ok(productGroups).build();
	    } catch (Exception e) {
	        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
	        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
	    }
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Operation(summary = "Get productGroup by ID", description = "Retrieves a productGroup by their ID.")
	@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductGroup.class)))
	@ApiResponse(responseCode = "404", description = "ProductGroup not found")
	public Response getProductGroupById(@PathParam("id") Long id) {
		try {
			ProductGroup productGroup = productGroupDAO.get(id);

			return Response.ok(productGroup).build();
		} catch (Exception e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.NOT_FOUND).entity(errorResponse).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Create a new productGroup", description = "Creates a new productGroup.")
	@ApiResponse(responseCode = "201", description = "ProductGroup created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductGroup.class)))
	@ApiResponse(responseCode = "500", description = "Internal server error")
	public Response saveProductGroup(ProductGroup productGroup) {
		try {
			productGroupDAO.save(productGroup);
			return Response.status(Status.CREATED).entity(productGroup).build();
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
	@Operation(summary = "Update an existing productGroup", description = "Updates an existing productGroup.")
	@ApiResponse(responseCode = "200", description = "ProductGroup updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductGroup.class)))
	@ApiResponse(responseCode = "500", description = "Internal server error")
	public Response updateProductGroup(ProductGroup updatedProductGroup) {
		try {
			productGroupDAO.update(updatedProductGroup, null);
			return Response.ok(updatedProductGroup).build();
		} catch (Exception e) {
			System.err.println(e);
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
		}
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Operation(summary = "Delete a productGroup by ID", description = "Deletes a productGroup by their ID.")
	@ApiResponse(responseCode = "204", description = "ProductGroup deleted")
	@ApiResponse(responseCode = "404", description = "ProductGroup not found")
	public Response deleteProductGroup(@PathParam("id") Long id) {
		try {
			productGroupDAO.delete(id); // Eliminar de la base de datos
			return Response.noContent().build();
		} catch (Exception e) {
			System.err.println(e);
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
		}

	}

}
