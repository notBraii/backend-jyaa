package utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import jakarta.inject.Inject;

import org.glassfish.hk2.api.Factory;

/**
 * Factory class to create and manage EntityManager instances. Provides
 * EntityManager objects on demand and handles their disposal.
 */
public class EMFactory implements Factory<EntityManager> {
	private final EntityManagerFactory emf;

	/**
	 * Constructor that receives the EntityManagerFactory as a dependency. This
	 * allows the factory to use the provided EntityManagerFactory to create
	 * EntityManager instances.
	 *
	 * @param emf The EntityManagerFactory used to create EntityManager instances.
	 */
	@Inject
	public EMFactory(EntityManagerFactory emf) {
		this.emf = emf;
	}

	/**
	 * Creates a new EntityManager instance. Prints a log message indicating that an
	 * EntityManager has been created.
	 *
	 * @return A new EntityManager instance.
	 */
	@Override
	public EntityManager provide() {
		System.out.println("EntityManager created.");
		return emf.createEntityManager();
	}

	/**
	 * Disposes of an EntityManager instance. Closes the EntityManager if it is open
	 * and prints a log message indicating the closure.
	 *
	 * @param instance The EntityManager instance to dispose of.
	 */
	@Override
	public void dispose(EntityManager instance) {
		if (instance.isOpen()) {
			instance.close();
			System.out.println("EntityManager closed.");
		}
	}

}
