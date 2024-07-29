package services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.jvnet.hk2.annotations.Service;

import dao.implementations.UserDAO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import models.User;

/**
 * Service class responsible for authenticating users and generating JWT tokens.
 */
@Service
public class AuthService {
	// Secret key for signing JWT tokens (should be stored securely, e.g.,
	// environment variables)
	private static final String SECRET_KEY = "D68157A11D5F73DE1F7F368FE288AFAE";
	private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

	@Inject
	private UserDAO userDAO;

	/**
	 * Generates a JWT token for the authenticated user.
	 *
	 * @param user the authenticated user
	 * @return a JWT token string
	 */
	private String generateToken(User user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", user.getId());

		return Jwts.builder().claims(claims).subject(user.getUsername()).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + 3600000*4)) // 1 hour 3600000
				.signWith(KEY).compact();
	}

	/**
	 * Authenticates a user based on the provided username and password.
	 *
	 * @param username the username of the user
	 * @param password the password of the user
	 * @return a Response containing a JWT token if authentication is successful, or
	 *         an unauthorized status if authentication fails
	 */
	public Response authenticate(String username, String password) {
		User user = userDAO.findByUsername(username);

		if (user != null && user.getPass().equals(password)) {
			String token = generateToken(user);
			String refreshToken = generateRefreshToken(user);
			Map<String, String> response = new HashMap<>();
			response.put("token", token);
			response.put("refreshToken", refreshToken);
			return Response.ok(response).build();
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

	private String generateRefreshToken(User user) {
		return Jwts.builder().subject(user.getUsername()).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + 86400000)) // 24 hours
				.signWith(KEY).compact();
	}

	public Response refreshToken(String refreshToken) {
		try {
			// Validar el refresh token
			Jws<Claims> claims = Jwts.parser().verifyWith(KEY).build().parseSignedClaims(refreshToken);
			String username = claims.getPayload().getSubject();

			// Obtener el usuario por el nombre de usuario
			User user = userDAO.findByUsername(username);

			if (user != null) {
				// Generar un nuevo token de acceso
				String newAccessToken = generateToken(user);
				Map<String, String> response = new HashMap<>();
				response.put("token", newAccessToken);
				return Response.ok(response).build();
			} else {
				return Response.status(Response.Status.UNAUTHORIZED).build();
			}
		} catch (Exception e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

}
