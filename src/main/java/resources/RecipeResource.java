package resources;

import java.util.List;

import dao.implementations.RecipeDAO;
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
import models.Recipe;

@Path("/recipes")
@Tag(name = "Recipes", description = "Operations related to recipes")
public class RecipeResource {

	@Inject
	private RecipeDAO recipeDAO;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get all recipes", responses = {
			@ApiResponse(description = "List of recipes", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))),
			@ApiResponse(description = "Internal Server Error", responseCode = "500") })
	public Response getRecipes() {
		try {
			List<Recipe> recipes = recipeDAO.getAll();
			return Response.ok(recipes).build();
		} catch (Exception e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Operation(summary = "Get recipe by ID", description = "Retrieves a recipe by their ID.")
	@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class)))
	@ApiResponse(responseCode = "404", description = "Recipe not found")
	public Response getRecipeById(@PathParam("id") Long id) {
		try {
			Recipe recipe = recipeDAO.get(id);

			return Response.ok(recipe).build();
		} catch (Exception e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.NOT_FOUND).entity(errorResponse).build();
		}
	}

	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Search recipes with pagination", description = "Search recipes by categoryName with pagination.")
	@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class)))
	@ApiResponse(responseCode = "500", description = "Internal server error")
	public Response searchProducts(@QueryParam("categoryName") String categoryName
//	                               @QueryParam("page") @DefaultValue("1") int page, 
//	                               @QueryParam("size") @DefaultValue("5") int size
	                               ) {
	    try {
	        List<Recipe> productGroups = recipeDAO.search(categoryName);
	        return Response.ok(productGroups).build();
	    } catch (Exception e) {
	        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
	        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
	    }
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Create a new recipe", description = "Creates a new recipe.")
	@ApiResponse(responseCode = "201", description = "Recipe created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class)))
	@ApiResponse(responseCode = "500", description = "Internal server error")
	public Response saveRecipe(Recipe recipe) {
		try {
			recipeDAO.save(recipe);
			return Response.status(Status.CREATED).entity(recipe).build();
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
	@Operation(summary = "Update an existing recipe", description = "Updates an existing recipe.")
	@ApiResponse(responseCode = "200", description = "Recipe updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class)))
	@ApiResponse(responseCode = "500", description = "Internal server error")
	public Response updateRecipe(Recipe updatedRecipe) {
		try {
			recipeDAO.update(updatedRecipe, null);
			return Response.ok(updatedRecipe).build();
		} catch (Exception e) {
			System.err.println(e);
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
		}
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Operation(summary = "Delete a recipe by ID", description = "Deletes a recipe by their ID.")
	@ApiResponse(responseCode = "204", description = "Recipe deleted")
	@ApiResponse(responseCode = "404", description = "Recipe not found")
	public Response deleteRecipe(@PathParam("id") Long id) {
		try {
			recipeDAO.delete(id); // Eliminar de la base de datos
			return Response.noContent().build();
		} catch (Exception e) {
			System.err.println(e);
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
		}

	}

}
