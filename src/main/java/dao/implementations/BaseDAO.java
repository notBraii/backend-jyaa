package dao.implementations;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import org.hibernate.exception.ConstraintViolationException;
import org.jvnet.hk2.annotations.Service;

import dao.interfaces.DAO;
import exception.DuplicateEntityException;
import exception.EntityNotFoundException;
import jakarta.inject.Inject;

/**
 * Abstract base class for Data Access Objects (DAOs). Provides common CRUD
 * operations and injects the EntityManager for database interactions.
 *
 * @param <T> The entity type this DAO manages.
 */
@Service
public abstract class BaseDAO<T> implements DAO<T> {

	/**
	 * Abstract method to be implemented by concrete DAOs to specify the entity
	 * class.
	 *
	 * @return The Class object representing the entity type.
	 */
	protected abstract Class<T> getEntityClass();

	/**
	 * Injected EntityManager (RequestScoped) for database operations.
	 */
	@Inject
	protected EntityManager em;

	/**
	 * Retrieves an entity by its ID.
	 *
	 * @param id The ID of the entity to retrieve.
	 * @return The retrieved entity or null if not found.
	 * @throws Exception If an error occurs during the retrieval.
	 */
	@Override
	public T get(Long id) {
		try {
			System.out.println("Entro al get con ID:" + id);
			T entity = em.find(getEntityClass(), id);
			if (entity == null) {
				throw new EntityNotFoundException("Entity with ID " + id + " not found.");
			}
			return entity;
		} catch (EntityNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException("Error retrieving entity with ID " + id, e);
		}
	}

	/**
	 * Retrieves all entities of the managed type.
	 *
	 * @return A list of all entities or an empty list if none are found.
	 * @throws Exception If an error occurs during the retrieval.
	 */
	@Override
	public List<T> getAll() {
		try {
			TypedQuery<T> query = em.createQuery("SELECT e FROM " + getEntityClass().getSimpleName() + " e",
					getEntityClass());
			return query.getResultList();
		} catch (PersistenceException e) {
			// Manejo específico para excepciones relacionadas con la persistencia
			throw new RuntimeException("Error retrieving all entities: " + e.getMessage(), e);
		} catch (Exception e) {
			// Manejo general para cualquier otro tipo de excepción
			throw new RuntimeException("Unexpected error occurred while retrieving all entities.", e);
		}
	}

	/**
	 * Saves a new entity to the database.
	 *
	 * @param t The entity to save.
	 * @throws Exception If an error occurs during the save operation.
	 */
	@Override
	public void save(T t) {
		EntityTransaction etx = em.getTransaction();
		try {
			etx.begin();
			em.persist(t);
			etx.commit();
		} catch (RollbackException e) {
			Throwable cause = e.getCause();
			while (cause != null) {
				if (cause instanceof ConstraintViolationException) {
					throw new DuplicateEntityException("Duplicate entry error.");
				}
				cause = cause.getCause();
			}
		} catch (Exception e) {
			System.out.println();
			if (etx.isActive())
				etx.rollback();
			throw new RuntimeException("Error saving entity.", e);
		}
	}

	/**
	 * Updates an existing entity in the database.
	 *
	 * @param t      The entity with updated values.
	 * @param params Additional parameters (currently unused).
	 * @throws Exception If an error occurs during the update operation.
	 */
	@Override
	public void update(T t, String[] params) {
		// TODO should we use params? NO
		EntityTransaction etx = em.getTransaction();
		try {
			etx.begin();
			em.merge(t);
			etx.commit();
		} catch (Exception e) {
			if (etx.isActive())
				etx.rollback();
			throw new RuntimeException("Error updating entity.", e);
		}
	}

	/**
	 * Deletes an entity by its ID.
	 *
	 * @param id The ID of the entity to delete.
	 * @throws Exception If an error occurs during the delete operation.
	 */
	@Override
	public void delete(Long id) {
		// TODO should we use a logical delete?
		EntityTransaction etx = em.getTransaction();
		try {
			T entity = em.find(getEntityClass(), id);
			if (entity == null) {
				throw new EntityNotFoundException("Entity with ID " + id + " not found.");
			}
			etx.begin();
			em.remove(entity);
			etx.commit();
		} catch (EntityNotFoundException e) {
			throw e;
		} catch (Exception e) {
			if (etx.isActive())
				etx.rollback();
			throw new RuntimeException("Error deleting entity with ID " + id, e);
		}
	}

}
