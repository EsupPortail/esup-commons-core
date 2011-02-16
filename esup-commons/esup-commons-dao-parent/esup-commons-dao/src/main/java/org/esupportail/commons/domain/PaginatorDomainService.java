/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.domain;

import org.esupportail.commons.dao.HqlQueryPojo;
import org.esupportail.commons.dao.ResultPaginator;

/**
 * @author ylecuyer
 * Esup Domain Service Interface
 */
public interface PaginatorDomainService {

	/**
	 * execute query to the paginator.
	 * @param hqlQuery
	 * @return a Query object that corresponds to a query string.
	 */
	ResultPaginator executeQuery(String queryString, HqlQueryPojo hqlPojo, Integer currentPage, Integer pageSize);

}
