package resources;

import java.util.List;

import dao.implementations.ResourceTagDAO;
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
import models.ResourceTag;

@Path("/resourceTags")
@Tag(name = "ResourceTags", description = "Operations related to resourceTags")
public class ResourceTagResource {

	@Inject
	private ResourceTagDAO resourceTagDAO;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get all resourceTags", responses = {
			@ApiResponse(description = "List of resourceTags", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceTag.class))),
			@ApiResponse(description = "Internal Server Error", responseCode = "500") })
	public Response getResourceTags() {
		try {
			List<ResourceTag> resourceTags = resourceTagDAO.getAll();
			return Response.ok(resourceTags).build();
		} catch (Exception e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Operation(summary = "Get resourceTag by ID", description = "Retrieves a resourceTag by their ID.")
	@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceTag.class)))
	@ApiResponse(responseCode = "404", description = "ResourceTag not found")
	public Response getResourceTagById(@PathParam("id") Long id) {
		try {
			ResourceTag resourceTag = resourceTagDAO.get(id);

			return Response.ok(resourceTag).build();
		} catch (Exception e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.NOT_FOUND).entity(errorResponse).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Create a new resourceTag", description = "Creates a new resourceTag.")
	@ApiResponse(responseCode = "201", description = "ResourceTag created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceTag.class)))
	@ApiResponse(responseCode = "500", description = "Internal server error")
	public Response saveResourceTag(ResourceTag resourceTag) {
		try {
			resourceTagDAO.save(resourceTag);
			return Response.status(Status.CREATED).entity(resourceTag).build();
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
	@Operation(summary = "Update an existing resourceTag", description = "Updates an existing resourceTag.")
	@ApiResponse(responseCode = "200", description = "ResourceTag updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceTag.class)))
	@ApiResponse(responseCode = "500", description = "Internal server error")
	public Response updateResourceTag(ResourceTag updatedResourceTag) {
		try {
			resourceTagDAO.update(updatedResourceTag, null);
			return Response.ok(updatedResourceTag).build();
		} catch (Exception e) {
			System.err.println(e);
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
		}
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Operation(summary = "Delete a resourceTag by ID", description = "Deletes a resourceTag by their ID.")
	@ApiResponse(responseCode = "204", description = "ResourceTag deleted")
	@ApiResponse(responseCode = "404", description = "ResourceTag not found")
	public Response deleteResourceTag(@PathParam("id") Long id) {
		try {
			resourceTagDAO.delete(id); // Eliminar de la base de datos
			return Response.noContent().build();
		} catch (Exception e) {
			System.err.println(e);
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
		}

	}

}
