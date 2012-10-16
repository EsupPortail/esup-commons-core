package org.esupportail.commons.domain;

import java.io.Serializable;

import org.esupportail.commons.dao.HqlQueryPojo;
import org.esupportail.commons.dao.PaginatorDaoService;
import org.esupportail.commons.dao.ResultPaginator;

/**
 * @author cleprous
 *
 */
public class PaginatorDomainServiceImpl implements PaginatorDomainService, Serializable {


	/*
	 *************************** PROPERTIES ******************************** */

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 1787051179168760305L;

	/**
	 * {@link PaginatorDaoService}.
	 */
	private PaginatorDaoService hibernateDaoService;
	/*
	 *************************** INIT ************************************** */


	/**
	 * Constructors.
	 */
	public PaginatorDomainServiceImpl() {
		super();
	}




	/*
	 *************************** METHODS *********************************** */

	@Override
	public ResultPaginator executeQuery(final String queryString,
			final HqlQueryPojo hqlPojo, final Integer currentPage, final Integer pageSize) {

		ResultPaginator r = hibernateDaoService.executeQuery(
				queryString, hqlPojo,
				currentPage, pageSize);
		return r;
	}


	/*
	 *************************** ACCESSORS ********************************* */

	/**
	 * @param hibernateDaoService the hibernateDaoService to set
	 */
	public void setHibernateDaoService(final PaginatorDaoService hibernateDaoService) {
		this.hibernateDaoService = hibernateDaoService;
	}

}
