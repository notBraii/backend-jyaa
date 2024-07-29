package resources;

import java.util.List;

import dao.implementations.DeliveryDAO;
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
import models.Delivery;

@Path("/deliveries")
@Tag(name = "Deliveries", description = "Operations related to deliveries")
public class DeliveryResource {

	@Inject
	private DeliveryDAO deliveryDAO;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get all deliveries", responses = {
			@ApiResponse(description = "List of deliveries", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Delivery.class))),
			@ApiResponse(description = "Internal Server Error", responseCode = "500") })
	public Response getDeliveries() {
		try {
			List<Delivery> deliveries = deliveryDAO.getAll();
			return Response.ok(deliveries).build();
		} catch (Exception e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Operation(summary = "Get delivery by ID", description = "Retrieves a delivery by their ID.")
	@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Delivery.class)))
	@ApiResponse(responseCode = "404", description = "Delivery not found")
	public Response getDeliveryById(@PathParam("id") Long id) {
		try {
			Delivery delivery = deliveryDAO.get(id);

			return Response.ok(delivery).build();
		} catch (Exception e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.NOT_FOUND).entity(errorResponse).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Create a new delivery", description = "Creates a new delivery.")
	@ApiResponse(responseCode = "201", description = "Delivery created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Delivery.class)))
	@ApiResponse(responseCode = "500", description = "Internal server error")
	public Response saveDelivery(Delivery delivery) {
		try {
			deliveryDAO.save(delivery);
			return Response.status(Status.CREATED).entity(delivery).build();
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
	@Operation(summary = "Update an existing delivery", description = "Updates an existing delivery.")
	@ApiResponse(responseCode = "200", description = "Delivery updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Delivery.class)))
	@ApiResponse(responseCode = "500", description = "Internal server error")
	public Response updateDelivery(Delivery updatedDelivery) {
		try {
			deliveryDAO.update(updatedDelivery, null);
			return Response.ok(updatedDelivery).build();
		} catch (Exception e) {
			System.err.println(e);
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
		}
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Operation(summary = "Delete a delivery by ID", description = "Deletes a delivery by their ID.")
	@ApiResponse(responseCode = "204", description = "Delivery deleted")
	@ApiResponse(responseCode = "404", description = "Delivery not found")
	public Response deleteDelivery(@PathParam("id") Long id) {
		try {
			deliveryDAO.delete(id); // Eliminar de la base de datos
			return Response.noContent().build();
		} catch (Exception e) {
			System.err.println(e);
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
		}

	}

}
