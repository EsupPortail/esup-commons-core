/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.dao;

import java.util.List;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * An abstract DAO implementation.
 */
public abstract class AbstractGenericHibernateDaoService 
implements PaginatorDaoService, InitializingBean {

	/**
	 * A logger.
	 */
	private Logger logger = new LoggerImpl(getClass()); 

	/**
	 * Bean constructor.
	 */
	protected AbstractGenericHibernateDaoService() {
		super();
	}

	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public final void afterPropertiesSet() throws Exception {
		initDao();
	}

	/**
	 * Initialize.
	 * @throws Exception
	 */
	protected abstract void initDao() throws Exception;

	/**
	 * @return the Hibernate template used for database operations
	 */
	protected abstract HibernateTemplate getHibernateTemplate();

	/**
	 * @see org.esupportail.commons.dao.HibernateDaoService#getQuery(java.lang.String)
	 */
	public Query getQuery(
			final String hqlQuery) {
		return (Query) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(final Session session) throws HibernateException {
						return session.createQuery(hqlQuery);
					}
				}
		);
	}


	/**
	 * @see org.esupportail.commons.dao.HibernateDaoService
	 * #executeQuery(java.lang.String, org.esupportail.commons.dao.HqlQueryPojo,
	 *  java.lang.Integer, java.lang.Integer)
	 */
	public ResultPaginator executeQuery(String queryString, HqlQueryPojo hqlQueryPojo, Integer currentPage, Integer pageSize) {
		//key row number and visibleItems
		ResultPaginator r = new ResultPaginator();
		if (hqlQueryPojo != null && !hqlQueryPojo.isEmpty()) {
			Query query = getQuery(hqlQueryPojo.buildHql());
			if (logger.isDebugEnabled()) {
				logger.debug("executing " + query.getQueryString() + "...");
			}

			query.setFirstResult(currentPage * pageSize);
			query.setMaxResults(pageSize);

			StringBuilder count = new StringBuilder("SELECT ");
			count.append("count(");
			String select = hqlQueryPojo.getSelect();
			if (select == null) {
				count.append("*");
			} else {
				count.append(select);
			}
			count.append(") ");
			count.append(hqlQueryPojo.geteClauseFrom());
			count.append(hqlQueryPojo.geteClauseWhere());
			if (logger.isDebugEnabled()) {
				logger.debug("executing " + count.toString() + "...");
			}
			r.setRowNumber(getQueryIntResult(count.toString()));

			r.setVisibleItems(query.list());
		} else if (org.springframework.util.StringUtils.hasText(queryString)) {
			Query query = getQuery(queryString);
			ScrollableResults scrollableResults = query.scroll();
			/*
			 * We set the max results to one more than the specfied pageSize to
			 * determine if any more results exist (i.e. if there is a next page
			 * to display). The result set is trimmed down to just the pageSize
			 * before being displayed later (in getList()).
			 */
			if (logger.isDebugEnabled()) {
				logger.debug("executing " + query.getQueryString() + "...");
			}
			query.setFirstResult(currentPage * pageSize);
			query.setMaxResults(pageSize);

			// the total number of results is computed here since scrolling is not allowed when rendering
			scrollableResults.last();
			r.setRowNumber((scrollableResults.getRowNumber() + 1));

			r.setVisibleItems(query.list());
		}


		return r;

	}

	/**
	 * Count entries of the database.
	 * @param countQuery the query
	 * @return an integer.
	 */
	protected int getQueryIntResult(final String countQuery) {
		return DataAccessUtils.intResult(getHibernateTemplate().find(countQuery));
	}

	/**
	 * do updates in the database.
	 * @param queryString
	 */
	protected void executeUpdate(final String queryString) {
		getQuery(queryString).executeUpdate();
	}

	/**
	 * @see org.esupportail.commons.dao.HibernateDaoService#getSqlQuery(java.lang.String)
	 */
	public SQLQuery getSqlQuery(
			final String sqlQuery) {
		return (SQLQuery) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(final Session session) throws HibernateException {
						return session.createSQLQuery(sqlQuery);
					}
				}
		);
	}

	//////////////////////////////////////////////////////////////
	// misc
	//////////////////////////////////////////////////////////////

	/**
	 * Add an object into the database.
	 * @param object 
	 */
	protected void addObject(final Object object) {
		if (logger.isDebugEnabled()) {
			logger.debug("adding " + object + "...");
		}
		getHibernateTemplate().save(object);
		if (logger.isDebugEnabled()) {
			logger.debug("done.");
		}
	}

	/**
	 * Update an object in the database.
	 * @param object 
	 */
	protected void updateObject(final Object object) {
		if (logger.isDebugEnabled()) {
			logger.debug("merging " + object + "...");
		}
		Object merged = getHibernateTemplate().merge(object);
		if (logger.isDebugEnabled()) {
			logger.debug("done, updating " + merged + "...");
		}
		getHibernateTemplate().update(merged);
		if (logger.isDebugEnabled()) {
			logger.debug("done.");
		}
	}

	/**
	 * Delete an object from the database.
	 * @param object 
	 */
	protected void deleteObject(final Object object) {
		if (logger.isDebugEnabled()) {
			logger.debug("merging " + object + "...");
		}
		Object merged = getHibernateTemplate().merge(object);
		if (logger.isDebugEnabled()) {
			logger.debug("done, deleting " + merged + "...");
		}
		getHibernateTemplate().delete(merged);
		if (logger.isDebugEnabled()) {
			logger.debug("done.");
		}
	}

	/**
	 * Delete a list of objects from the database.
	 * @param objects 
	 */
	@SuppressWarnings("unchecked")
	protected void deleteObjects(final List objects) {
		getHibernateTemplate().deleteAll(objects);
	}

}
