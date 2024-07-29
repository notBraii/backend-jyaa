package resources;

import java.util.List;

import dao.implementations.CategoryDAO;
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
import models.Category;

@Path("/categories")
@Tag(name = "Categories", description = "Operations related to categories")
public class CategoryResource {

	@Inject
	private CategoryDAO categoryDAO;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get all categories", responses = {
			@ApiResponse(description = "List of categories", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Category.class))),
			@ApiResponse(description = "Internal Server Error", responseCode = "500") })
	public Response getCategories() {
		try {
			List<Category> categories = categoryDAO.getAll();
			return Response.ok(categories).build();
		} catch (Exception e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Operation(summary = "Get category by ID", description = "Retrieves a category by their ID.")
	@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Category.class)))
	@ApiResponse(responseCode = "404", description = "Category not found")
	public Response getCategoryById(@PathParam("id") Long id) {
		try {
			Category category = categoryDAO.get(id);

			return Response.ok(category).build();
		} catch (Exception e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.NOT_FOUND).entity(errorResponse).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Create a new category", description = "Creates a new category.")
	@ApiResponse(responseCode = "201", description = "Category created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Category.class)))
	@ApiResponse(responseCode = "500", description = "Internal server error")
	public Response saveCategory(Category category) {
		try {
			categoryDAO.save(category);
			return Response.status(Status.CREATED).entity(category).build();
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
	@Operation(summary = "Update an existing category", description = "Updates an existing category.")
	@ApiResponse(responseCode = "200", description = "Category updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Category.class)))
	@ApiResponse(responseCode = "500", description = "Internal server error")
	public Response updateCategory(Category updatedCategory) {
		try {
			categoryDAO.update(updatedCategory, null);
			return Response.ok(updatedCategory).build();
		} catch (Exception e) {
			System.err.println(e);
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
		}
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Operation(summary = "Delete a category by ID", description = "Deletes a category by their ID.")
	@ApiResponse(responseCode = "204", description = "Category deleted")
	@ApiResponse(responseCode = "404", description = "Category not found")
	public Response deleteCategory(@PathParam("id") Long id) {
		try {
			categoryDAO.delete(id); // Eliminar de la base de datos
			return Response.noContent().build();
		} catch (Exception e) {
			System.err.println(e);
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
		}

	}

}
