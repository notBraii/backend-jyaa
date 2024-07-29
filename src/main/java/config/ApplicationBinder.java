package config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.glassfish.hk2.api.JustInTimeInjectionResolver;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;

import utils.EMFSingleton;
import utils.EMFactory;

/**
 * HK2 binder to configure dependency injection in the application.
 */
public class ApplicationBinder extends AbstractBinder {

	@Override
	protected void configure() {
		// EntityManager Injection with Request Scope
		// -----------------------------------------------------
		// We use a factory (EMFactory) to create EntityManager instances.
		// Proxies are enabled to ensure each request gets its own instance.
		// Force proxy creation even if the requesting object is also RequestScoped
		// (proxyForSameScope(false)).
		// source:
		// https://psamsotha.github.io/jersey/2015/12/16/request-scope-into-singleton-scope.html
		bindFactory(EMFactory.class).proxy(true).proxyForSameScope(false).to(EntityManager.class)
				.in(RequestScoped.class);

		// EntityManagerFactory Injection as Singleton
		// ----------------------------------------------
		// Bind the single EntityManagerFactory instance (obtained from the
		// EMFSingleton)
		// to be available for injection throughout the application.
		bind(EMFSingleton.INSTANCE.getEMF()).to(EntityManagerFactory.class);		

		// Just-In-Time Injection (Optional)
		// ---------------------------------
		// Allows Just-In-Time resolution of services not explicitly bound.
		bind(JustInTimeServiceResolver.class).to(JustInTimeInjectionResolver.class);
	}

}
