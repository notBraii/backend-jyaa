package filters;

import java.io.IOException;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JWTAuthenticationFilter implements ContainerRequestFilter {

	private static final String SECRET_KEY = "D68157A11D5F73DE1F7F368FE288AFAE";
	private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String BEARER_PREFIX = "Bearer ";

	private Jws<Claims> validateToken(String token) {
		return Jwts.parser().verifyWith(KEY).build().parseSignedClaims(token);
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// TODO Auto-generated method stub
		String authorizationHeader = requestContext.getHeaderString(AUTHORIZATION_HEADER);
		String path = requestContext.getUriInfo().getPath();
		if (path.equals("auth/login") || path.equals("auth/refresh")) {
			 // Excluye la ruta de inicio de sesión
			// Permite que la solicitud continúe sin validar el token
			return;
		}
		if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_PREFIX)) {
			abortWithUnauthorized(requestContext);
			return;
		}

		String token = authorizationHeader.substring(BEARER_PREFIX.length());
		try {
			Jws<Claims> claims = validateToken(token);
			requestContext.setProperty("claims", claims);
		} catch (Exception e) {
			// TODO: handle exception
			abortWithUnauthorized(requestContext);
		}
	}

	private void abortWithUnauthorized(ContainerRequestContext requestContext) {
		requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
	}

}
