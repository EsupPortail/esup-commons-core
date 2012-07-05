/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.dao;

import org.esupportail.commons.utils.Assert;
import org.esupportail.commons.services.paginator.AbstractPaginator;
import org.springframework.beans.factory.InitializingBean;

/**
 * A Hibernate paginator, i.e. that has a DaoService attribute to perform HQL queries.
 * @param <E> the class of the results
 */
@SuppressWarnings("serial")
public abstract class AbstractHibernatePaginator<E> extends AbstractPaginator<E> implements InitializingBean {

	/**
	 * The daoService.
	 */
	private PaginatorDaoService daoService;

	/**
	 * Constructor.
	 */
	public AbstractHibernatePaginator() {
		super();
	}

	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {
		Assert.notNull(this.daoService,
				"property daoService of class " + this.getClass().getName() + " can not be null");
	}

	/**
	 * @return the daoService
	 */
	protected PaginatorDaoService getDaoService() {
		return daoService;
	}

	/**
	 * @param daoService the daoService to set
	 */
	public void setDaoService(final PaginatorDaoService daoService) {
		this.daoService = daoService;
	}

}

