/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.dao;

import javax.persistence.EntityManager;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import javax.persistence.Query;
import org.springframework.beans.factory.InitializingBean;

/**
 * An abstract DAO implementation.
 */
public abstract class AbstractGenericJPADaoService 
implements PaginatorDaoService, InitializingBean {

	/**
	 * A logger.
	 */
	private Logger logger = new LoggerImpl(getClass()); 

	/**
	 * Bean constructor.
	 */
	protected AbstractGenericJPADaoService() {
		super();
	}


	/**
	 * @return the Hibernate template used for database operations
	 */
	protected abstract EntityManager getEntityManager();

	/**
	 * @see org.esupportail.commons.dao.HibernateDaoService#getQuery(java.lang.String)
	 */
	public Query getQuery(
			final String hqlQuery) {
		return (Query) getEntityManager().createQuery(hqlQuery);
	}


	/**
	 * @see org.esupportail.commons.dao.HibernateDaoService
	 * #executeQuery(java.lang.String, org.esupportail.commons.dao.HqlQueryPojo,
	 *  java.lang.Integer, java.lang.Integer)
	 */
	public ResultPaginator executeQuery(String queryString, HqlQueryPojo hqlQueryPojo, Integer currentPage, Integer pageSize) {
		//key row number and visibleItems
		ResultPaginator r = new ResultPaginator();




		//		if (hqlQueryPojo != null && !hqlQueryPojo.isEmpty()) {
		//			Query query = getQuery(hqlQueryPojo.buildHql());
		//			if (logger.isDebugEnabled()) {
		//				logger.debug("executing " + query.getQueryString() + "...");
		//			}
		//
		//			query.setFirstResult(currentPage * pageSize);
		//			query.setMaxResults(pageSize);
		//
		//			StringBuilder count = new StringBuilder("SELECT ");
		//			count.append("count(");
		//			String select = hqlQueryPojo.getSelect();
		//			if (select == null) {
		//				count.append("*");
		//			} else {
		//				count.append(select);
		//			}
		//			count.append(") ");
		//			count.append(hqlQueryPojo.geteClauseFrom());
		//			count.append(hqlQueryPojo.geteClauseWhere());
		//			if (logger.isDebugEnabled()) {
		//				logger.debug("executing " + count.toString() + "...");
		//			}
		//			r.setRowNumber(getQueryIntResult(count.toString()));
		//
		//			r.setVisibleItems(query.list());
		//		} else if (org.springframework.util.StringUtils.hasText(queryString)) {



		Query query = getQuery(queryString);
		query.setFirstResult(currentPage * pageSize);
		query.setMaxResults(pageSize);
		
		//genere le count
		StringBuilder count = new StringBuilder("SELECT ");
		count.append("count(*) ");
		String fromAndWhereClause = queryString.substring(queryString.indexOf("FROM"), queryString.length());
		
		count.append(fromAndWhereClause);
		
		logger.debug(count.toString());
		Long l = (Long) getQuery(count.toString()).getSingleResult();
		
		r.setRowNumber(l.intValue());

		r.setVisibleItems(query.getResultList());
		//}


		return r;

	}

	/**
	 * Count entries of the database.
	 * @param countQuery the query
	 * @return an integer.
	 */
	//	protected int getQueryIntResult(final String countQuery) {
	//		return DataAccessUtils.intResult(getEntityManager().find(countQuery));
	//	}

	/**
	 * do updates in the database.
	 * @param queryString
	 */
	protected void executeUpdate(final String queryString) {
		getQuery(queryString).executeUpdate();
	}





}
