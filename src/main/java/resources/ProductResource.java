package resources;

import java.util.List;

import dao.implementations.ProductDAO;
import exception.DuplicateEntityException;
import exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.media.*;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
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
import models.Product;
import utils.PaginatedResponse;

@Path("/products")
@Tag(name = "Products", description = "Operations related to products")
public class ProductResource {

	@Inject
	private ProductDAO productDAO;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get all products", responses = {
			@ApiResponse(description = "List of products", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))),
			@ApiResponse(description = "Internal Server Error", responseCode = "500") })
	public Response getProducts() {
		try {
			List<Product> products = productDAO.getAll();
			return Response.ok(products).build();
		} catch (Exception e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
		}
	}

	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Search products with pagination", description = "Search products by various attributes with pagination.")
	@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)))
	@ApiResponse(responseCode = "500", description = "Internal server error")
	public Response searchProducts(@QueryParam("name") List <String> names, 
	                               @QueryParam("batch") String batch,
//	                               @QueryParam("productionDate") String productionDate, 
	                               @QueryParam("quantity") Integer quantity,
//	                               @QueryParam("productGroup") Long productGroupId,
	                               @QueryParam("productGroupName") String productGroupName,
	                               @QueryParam("categoryName") String categoryName
//	                               @QueryParam("page") @DefaultValue("1") int page, 
//	                               @QueryParam("size") @DefaultValue("5") int size
	                               ) {
//	    Limitar el tamaño de página
//	    int maxSize = 20; // Límite máximo
//	    size = Math.min(size, maxSize); // Aplicar el límite

	    try {
	        List<Product> products = productDAO.search(names, batch, quantity, productGroupName, categoryName);
	        //long totalItems = productDAO.count(name, batch, quantity, productGroupName, categoryName);
	        //int totalPages = (int) Math.ceil((double) totalItems / size);

	        //PaginatedResponse<Product> response = new PaginatedResponse<>(products, page, totalPages, totalItems, size);
	        return Response.ok(products).build();
	    } catch (Exception e) {
	        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
	        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
	    }
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Operation(summary = "Get product by ID", description = "Retrieves a product by their ID.")
	@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)))
	@ApiResponse(responseCode = "404", description = "Product not found")
	public Response getProductById(@PathParam("id") Long id) {
		try {
			Product product = productDAO.get(id);

			return Response.ok(product).build();
		} catch (Exception e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.NOT_FOUND).entity(errorResponse).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Create a new product", description = "Creates a new product.")
	@ApiResponse(responseCode = "201", description = "Product created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)))
	@ApiResponse(responseCode = "500", description = "Internal server error")
	public Response saveProduct(Product product) {
		try {
			productDAO.save(product);
			return Response.status(Status.CREATED).entity(product).build();
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
	@Operation(summary = "Update an existing product", description = "Updates an existing product.")
	@ApiResponse(responseCode = "200", description = "Product updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)))
	@ApiResponse(responseCode = "500", description = "Internal server error")
	public Response updateProduct(Product updatedProduct) {
		try {
			productDAO.update(updatedProduct, null);
			return Response.ok(updatedProduct).build();
		} catch (Exception e) {
			System.err.println(e);
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
		}
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Operation(summary = "Delete a product by ID", description = "Deletes a product by their ID.")
	@ApiResponse(responseCode = "204", description = "Product deleted")
	@ApiResponse(responseCode = "404", description = "Product not found")
	public Response deleteProduct(@PathParam("id") Long id) {
		try {
			productDAO.delete(id); // Eliminar de la base de datos
			return Response.noContent().build();
		} catch (Exception e) {
			System.err.println(e);
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
		}

	}

}
