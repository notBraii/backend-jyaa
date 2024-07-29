package listeners;

import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import utils.EMFSingleton;

/**
 * ServletContextListener to close the EntityManagerFactory and Hibernate
 * SessionFactory when the web application shuts down.
 */
public class EMFCloseListener implements ServletContextListener {

	/**
	 * Called when the web application starts. No action is needed here since the
	 * EntityManagerFactory is initialized in the EMFSingleton.
	 *
	 * @param sce The ServletContextEvent
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("Inicia el servidor");
	}

	/**
	 * Called when the web application shuts down. Closes the EntityManagerFactory
	 * and the associated Hibernate SessionFactory to release resources.
	 *
	 * @param sce The ServletContextEvent
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		EntityManagerFactory emf = EMFSingleton.INSTANCE.getEMF();
		if (emf != null) {
			if (emf.isOpen()) {
				SessionFactory sessionFactory = emf.unwrap(SessionFactory.class);
				if (sessionFactory != null) {
					sessionFactory.close();
				}
				emf.close();
			}
		}
	}

}
