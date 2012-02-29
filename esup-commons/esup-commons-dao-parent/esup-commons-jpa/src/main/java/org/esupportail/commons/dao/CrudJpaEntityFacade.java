/**
 * ESUP-Portail Commons - Copyright (c) 2006-2012 ESUP-Portail consortium.
 */
package org.esupportail.commons.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.OptimisticLockException;

import org.hibernate.StaleStateException;

import fr.univlille1.paperless.domain.beans.Administrateur;

/**
 * @author Yves Deschamps. Interface for CRUD JPA
 * @param <T>
 *            Class bean
 * 
 */
public interface CrudJpaEntityFacade<T> {

	/**
	 * Create an entity.
	 * 
	 * @param entity
	 *            the entity
	 * @return T a new persist entity
	 */
	T create(T entity);

	/**
	 * Read an entity.
	 * 
	 * @param primaryKey
	 *            the primaryKey for this entity
	 * @return T an entity
	 */
	T read(Serializable primaryKey);

	/**
	 * Update an entity.
	 * 
	 * @param T
	 *            entity an entity
	 * @return T an updated entity
	 */
	T update(T entity);

	/**
	 * Delete an entity.
	 * 
	 * @param entity
	 *            the entity to delete
	 * @param primaryKey
	 *            the primaryKey for this entity
	 */
	void delete(T entity, Serializable primaryKey);

	/**
	 * 
	 * @return count of entities for T type.
	 */
	int getCount();

	/**
	 * 
	 * @return count of entities with filter.
	 */
	int getFilteringCount(String queryName, Map<String, Object> filter);

	/**
	 * Find a list of entities with query.
	 * 
	 * @param queryName
	 *            a Query
	 * @return List<T> a list of entities
	 */
	List<T> find(String queryName);

	/**
	 * Find a partial list of entities with query.
	 * 
	 * @param queryName
	 *            a Query
	 * @param start
	 *            start index
	 * @param max
	 *            maximum number of lines
	 * @return List<T> a list of entities
	 */
	List<T> find(String queryName, int start, int max);

	/**
	 * Find a list of entities with query and filter.
	 * 
	 * @param queryName
	 *            a Query
	 * @param filter
	 *            a filter
	 * @return List<T> a list of entities
	 */
	List<T> findFiltering(String queryName, Map<String, Object> filter);

	/**
	 * Find a partial list of entities with query and filter.
	 * 
	 * @param queryName
	 *            a Query
	 * @param filter
	 *            a filter
	 * @return List<T> a list of entities
	 */
	List<T> findFiltering(String queryName, Map<String, Object> filter,
			int start, int max);

	/**
	 * Find an unique entity with query and filter.
	 * 
	 * @param queryName
	 *            a Query
	 * @param filter
	 *            a filter
	 * @return T an entity
	 */
	T findUniqueFiltering(String queryName, Map<String, Object> filter);

}
