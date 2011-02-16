/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.dao;


/**
 * An abstract DAO implementation.
 */
public interface PaginatorDaoService {

	
	/**
	 * execute query to the paginator.
	 * If hqlQuery and hqlPojo != null use hqlPojo
	 * @param hqlQuery if null use hqlPojo
	 * @param hqlPojo if null use hqlQuery
	 * @return a Query object that corresponds to a query string.
	 */
	ResultPaginator executeQuery(String queryString, HqlQueryPojo hqlPojo, Integer currentPage, Integer pageSize);

}
