package dao.interfaces;

import java.util.List;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface DAO<T> {

	/**
	 * Retrieves an entity by its ID.
	 * 
	 * @param id The ID of the entity to retrieve.
	 * @return An Optional containing the entity if found, or empty if not found.
	 */
	T get(Long id);

	/**
	 * Retrieves all entities.
	 * 
	 * @return A list of all entities.
	 */
	List<T> getAll();

	/**
	 * Saves a new entity to the database.
	 * 
	 * @param t The entity to save.
	 */
	void save(T t);

	/**
	 * Updates an existing entity with new parameters.
	 * 
	 * @param t      The entity to update.
	 * @param params The new parameters of the entity.
	 */
	void update(T t, String[] params);

	/**
	 * Deletes an entity from the database.
	 * 
	 * @param id The ID of the entity to delete.
	 */
	void delete(Long id);
}
