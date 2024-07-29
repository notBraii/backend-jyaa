package utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Singleton class to manage the EntityManagerFactory instance. Ensures that
 * only one EntityManagerFactory is created for the entire application.
 */
public enum EMFSingleton {
	INSTANCE; // The single instance of this enum

	private EntityManagerFactory emf;

	/**
	 * Private constructor to prevent external instantiation. Initializes the
	 * EntityManagerFactory using the "miUP" persistence unit defined in
	 * persistence.xml.
	 */
	private EMFSingleton() {
		// Initializes EMF with the name of the persistence unit in persistence.xml
		emf = Persistence.createEntityManagerFactory("miUP");
	}

	/**
	 * Returns the singleton EntityManagerFactory instance.
	 *
	 * @return The EntityManagerFactory
	 */
	public EntityManagerFactory getEMF() {
		return emf;
	}

	/**
	 * Closes the EntityManagerFactory if it's open. This should be called when the
	 * application is shutting down.
	 */
	public void close() {
		if (emf != null && emf.isOpen())
			emf.close();
	}
}
