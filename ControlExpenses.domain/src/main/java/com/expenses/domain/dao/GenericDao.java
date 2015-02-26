package com.expenses.domain.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author Andres
 * 
 */
public interface GenericDao<T> {

	// Search Methods

	/**
	 * Returns the instance of the model corresponding to the id and class
	 * passed as parameter.
	 * 
	 * @param id
	 * @param persistentType
	 * @return
	 */
	T findById(Serializable id, Class<? extends T> persistentType);

	/**
	 * Returns all instances of the model corresponding to the class passed as
	 * parameter.
	 * 
	 * @param clazz
	 * @return
	 */
	List<T> findAll(Class clazz);

	// metodos para persistir
	/**
	 * Save the instance passed as parameter.
	 * 
	 * @param object
	 */
	void save(T object);

	/**
	 * Update the instance passed as parameter.
	 * 
	 * @param object
	 */
	void update(T object);

	/**
	 * Delete the instance passed as parameter.
	 * 
	 * @param object
	 */
	void delete(T object);

	/**
	 * Returns all instances of the model that complied with the id restriction.
	 * 
	 * @param fieldName
	 *            Name of the attribute in the model. (Eg: idUsser)
	 * @param fieldValue
	 *            Value of the attribute.
	 * @param clazz
	 * @return
	 */
	List<T> findAllWithId(String fieldName, Integer fieldValue,
			final Class clazz);

}
