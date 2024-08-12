package config;

import org.glassfish.jersey.server.ResourceConfig;

import filters.JWTAuthenticationFilter;

/**
 * Main configuration class for the Jersey RESTful web application. Extends
 * ResourceConfig to register necessary components and resource packages.
 */
public class JerseyApplication extends ResourceConfig {

	/**
	 * Constructor that initializes the Jersey application. Registers the
	 * ApplicationBinder for dependency injection configuration and scans the
	 * "resources" package for JAX-RS annotated classes.
	 */
	public JerseyApplication() {
		register(new ApplicationBinder()); // Register the binder for dependency injection
		//register(JWTAuthenticationFilter.class); // Register JWT filter
		packages(true, "resources"); // Scan the "resources" package for JAX-RS resources
	}
}
