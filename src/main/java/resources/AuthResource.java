package resources;

import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import models.LoginRequest;
import models.User;
import services.AuthService;

@Path("/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthResource {

	@Inject
	private AuthService authService;

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Authenticate user", responses = {
			@ApiResponse(description = "Authentication successful", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(description = "Internal Server Error", responseCode = "500") })
	public Response login(LoginRequest loginRequest) {
		return authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
	}
	
	@POST
	@Path("/refresh")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Refresh access token", responses = {
	    @ApiResponse(description = "Token refreshed successfully", responseCode = "200", content = @Content(mediaType = "application/json")),
	    @ApiResponse(responseCode = "401", description = "Unauthorized"),
	    @ApiResponse(description = "Internal Server Error", responseCode = "500")
	})
	public Response refreshToken(Map<String, String> requestBody) {
	    String refreshToken = requestBody.get("refreshToken");
	    return authService.refreshToken(refreshToken);
	}
}
