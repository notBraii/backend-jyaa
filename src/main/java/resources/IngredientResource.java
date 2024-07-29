package resources;

import java.util.List;

import dao.implementations.IngredientDAO;
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
import models.Ingredient;

@Path("/ingredients")
@Tag(name = "Ingredients", description = "Operations related to ingredients")
public class IngredientResource {

	@Inject
	private IngredientDAO ingredientDAO;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get all ingredients", responses = {
			@ApiResponse(description = "List of ingredients", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ingredient.class))),
			@ApiResponse(description = "Internal Server Error", responseCode = "500") })
	public Response getIngredients() {
		try {
			List<Ingredient> ingredients = ingredientDAO.getAll();
			return Response.ok(ingredients).build();
		} catch (Exception e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Operation(summary = "Get ingredient by ID", description = "Retrieves a ingredient by their ID.")
	@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ingredient.class)))
	@ApiResponse(responseCode = "404", description = "Ingredient not found")
	public Response getIngredientById(@PathParam("id") Long id) {
		try {
			Ingredient ingredient = ingredientDAO.get(id);

			return Response.ok(ingredient).build();
		} catch (Exception e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.NOT_FOUND).entity(errorResponse).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Create a new ingredient", description = "Creates a new ingredient.")
	@ApiResponse(responseCode = "201", description = "Ingredient created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ingredient.class)))
	@ApiResponse(responseCode = "500", description = "Internal server error")
	public Response saveIngredient(Ingredient ingredient) {
		try {
			ingredientDAO.save(ingredient);
			return Response.status(Status.CREATED).entity(ingredient).build();
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
	@Operation(summary = "Update an existing ingredient", description = "Updates an existing ingredient.")
	@ApiResponse(responseCode = "200", description = "Ingredient updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ingredient.class)))
	@ApiResponse(responseCode = "500", description = "Internal server error")
	public Response updateIngredient(Ingredient updatedIngredient) {
		try {
			ingredientDAO.update(updatedIngredient, null);
			return Response.ok(updatedIngredient).build();
		} catch (Exception e) {
			System.err.println(e);
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
		}
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Operation(summary = "Delete a ingredient by ID", description = "Deletes a ingredient by their ID.")
	@ApiResponse(responseCode = "204", description = "Ingredient deleted")
	@ApiResponse(responseCode = "404", description = "Ingredient not found")
	public Response deleteIngredient(@PathParam("id") Long id) {
		try {
			ingredientDAO.delete(id); // Eliminar de la base de datos
			return Response.noContent().build();
		} catch (Exception e) {
			System.err.println(e);
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
		}

	}

}
