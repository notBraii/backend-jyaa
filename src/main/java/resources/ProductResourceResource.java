package resources;

import java.util.List;

import dao.implementations.ProductResourceDAO;
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
import models.ProductResource;

@Path("/productResources")
@Tag(name = "ProductResources", description = "Operations related to productResources")
public class ProductResourceResource {

	@Inject
	private ProductResourceDAO productResourceDAO;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get all productResources", responses = {
			@ApiResponse(description = "List of productResources", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResource.class))),
			@ApiResponse(description = "Internal Server Error", responseCode = "500") })
	public Response getProductResources() {
		try {
			List<ProductResource> productResources = productResourceDAO.getAll();
			return Response.ok(productResources).build();
		} catch (Exception e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Operation(summary = "Get productResource by ID", description = "Retrieves a productResource by their ID.")
	@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResource.class)))
	@ApiResponse(responseCode = "404", description = "ProductResource not found")
	public Response getProductResourceById(@PathParam("id") Long id) {
		try {
			ProductResource productResource = productResourceDAO.get(id);

			return Response.ok(productResource).build();
		} catch (DuplicateEntityException e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.CONFLICT).entity(errorResponse).build();
		} catch (Exception e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Create a new productResource", description = "Creates a new productResource.")
	@ApiResponse(responseCode = "201", description = "ProductResource created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResource.class)))
	@ApiResponse(responseCode = "500", description = "Internal server error")
	public Response saveProductResource(ProductResource productResource) {
		try {
			productResourceDAO.save(productResource);
			return Response.status(Status.CREATED).entity(productResource).build();
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
	@Operation(summary = "Update an existing productResource", description = "Updates an existing productResource.")
	@ApiResponse(responseCode = "200", description = "ProductResource updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResource.class)))
	@ApiResponse(responseCode = "500", description = "Internal server error")
	public Response updateProductResource(ProductResource updatedProductResource) {
		try {
			productResourceDAO.update(updatedProductResource, null);
			return Response.ok(updatedProductResource).build();
		} catch (Exception e) {
			System.err.println(e);
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
		}
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Operation(summary = "Delete a productResource by ID", description = "Deletes a productResource by their ID.")
	@ApiResponse(responseCode = "204", description = "ProductResource deleted")
	@ApiResponse(responseCode = "404", description = "ProductResource not found")
	public Response deleteProductResource(@PathParam("id") Long id) {
		try {
			productResourceDAO.delete(id); // Eliminar de la base de datos
			return Response.noContent().build();
		} catch (Exception e) {
			System.err.println(e);
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
		}

	}

}
