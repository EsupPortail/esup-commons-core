/**
 * ESUP-Portail Commons - Copyright (c) 2006-2012 ESUP-Portail consortium.
 */
package org.esupportail.commons.dao;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

/**
 * @author Yves Deschamps.
 * @param <T>
 *            Class bean
 * 
 */
public class CrudJpaEntityFacadeImpl<T> implements CrudJpaEntityFacade<T> {

	/**
	 * JPA entity Class.
	 */
	private Class<T> entityClass;

	/**
	 * JPA entity manager.
	 */
	private EntityManager entityManager;

	/**
	 * Bean constructor.
	 */
	CrudJpaEntityFacadeImpl() {
		super();
	}

	protected EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * @param entityManager
	 *            the entityManager to set
	 */
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	public final void setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public T create(final T entity) {
		T myEntity = entityManager.merge(entity);
		entityManager.persist(myEntity);
		entityManager.flush();
		return myEntity;
	}

	@Override
	public T read(final Serializable primaryKey) {
		T entity = entityManager.find(entityClass, primaryKey);
		return entity;
	}

	@Override
	public T update(final T entity) {
		entityManager.merge(entity);
		entityManager.flush();
		return entity;
	}

	@Override
	public void delete(T entity, Serializable primaryKey) {
		// entityManager.clear();
		T myEntity = entityManager.find(entityClass, primaryKey);
		if (myEntity != null) {
			entityManager.remove(myEntity);
			entityManager.flush();
		}
	}

	@Override
	public int getCount() {
		Query query = entityManager.createQuery("SELECT COUNT(*) FROM "
				+ entityClass.getSimpleName() + " entity");
		Number result = (Number) query.getSingleResult();
		return result.intValue();
	}

	/**
	 * @param entityClass
	 *            the entityClass to set
	 */
	public void setEntityClass(final Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(String queryName) {
		Query query = entityManager.createNamedQuery(queryName);
		List<T> results = query.getResultList();
		for (T result : results) {
			entityManager.refresh(result);
		}
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(String queryName, int start, int max) {
		Query query = entityManager.createNamedQuery(queryName);
		query.setFirstResult(start);
		query.setMaxResults(max);
		List<T> results = query.getResultList();
		for (T result : results) {
			entityManager.refresh(result);
		}
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findFiltering(String queryName, Map<String, Object> filter) {
		Query query = entityManager.createNamedQuery(queryName);
		Iterator<Entry<String, Object>> it = filter.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> pairs = it.next();
			query.setParameter(pairs.getKey(), pairs.getValue());
		}
		List<T> results = query.getResultList();
		for (T result : results) {
			entityManager.refresh(result);
		}
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findFiltering(String queryName, Map<String, Object> filter,
			int start, int max) {
		Query query = entityManager.createNamedQuery(queryName);
		Iterator<Entry<String, Object>> it = filter.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> pairs = it.next();
			query.setParameter(pairs.getKey(), pairs.getValue());
		}
		query.setFirstResult(start);
		query.setMaxResults(max);
		List<T> results = query.getResultList();
		for (T result : results) {
			entityManager.refresh(result);
		}
		return results;
	}

	@Override
	public int getFilteringCount(String queryName, Map<String, Object> filter) {
		Query query = entityManager.createNamedQuery(queryName);
		Iterator<Entry<String, Object>> it = filter.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> pairs = it.next();
			query.setParameter(pairs.getKey(), pairs.getValue());
		}
		Number result = (Number) query.getSingleResult();
		return result.intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findUniqueFiltering(String queryName, Map<String, Object> filter) {
		Query query = entityManager.createNamedQuery(queryName);
		Iterator<Entry<String, Object>> it = filter.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> pairs = it.next();
			query.setParameter(pairs.getKey(), pairs.getValue());
		}
		query.setMaxResults(1);
		List<T> results = query.getResultList();
		if (results == null || results.size() == 0) {
			return null;
		}
		entityManager.refresh(results.get(0));
		return results.get(0);
	}

}
