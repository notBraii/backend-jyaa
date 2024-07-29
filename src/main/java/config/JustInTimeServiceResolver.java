package config;

import java.lang.reflect.Type;
import java.util.List;

import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.JustInTimeInjectionResolver;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.jvnet.hk2.annotations.Service;

import jakarta.inject.Inject;

@Service
public class JustInTimeServiceResolver implements JustInTimeInjectionResolver {

	@Inject
	private ServiceLocator serviceLocator;

	@Override
	public boolean justInTimeResolution(Injectee injectee) {
		Type requiredType = injectee.getRequiredType();
		if (!(requiredType instanceof Class<?>)) {
			return false;
		}

		Class<?> requiredClass = (Class<?>) requiredType;
		String className = requiredClass.getName();

		// Clases que no hace falta resolver
		List<String> ignoredClasses = List.of(
				"org.glassfish.jersey.jackson.internal.jackson.jaxrs.base.JsonParseExceptionMapper",
				"org.glassfish.jersey.jackson.internal.DefaultJacksonJaxbJsonProvider",
				"org.glassfish.jersey.jackson.internal.jackson.jaxrs.base.JsonMappingExceptionMapper",
				"org.glassfish.jersey.server.internal.inject.ConfiguredValidator");

		if (ignoredClasses.contains(className)) {
			System.out.println("Ignorando dependencia no utilizada: " + className);
			return false;
		}

		// Verificar si la clase requerida está anotada con @Service
		if (requiredClass.isAnnotationPresent(Service.class)) {
			System.out.println("Intentando resolver dependencia de tipo: " + className);

			List<ActiveDescriptor<?>> descriptors = ServiceLocatorUtilities.addClasses(serviceLocator, requiredClass);

			if (!descriptors.isEmpty()) {
				System.out.println("Dependencia resuelta: " + className);
				return true;
			} else {
				System.out.println("No se encontraron implementaciones para la dependencia: " + className);
			}
		} else {
			System.out.println("La dependencia no está anotada con @Service: " + className);
		}

		return false;
	}
}
