/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.domain;

import org.esupportail.commons.utils.Assert;
import org.esupportail.commons.services.paginator.AbstractPaginator;
import org.springframework.beans.factory.InitializingBean;

/**
 * A Hibernate paginator, i.e. that has a DaoService attribute to perform HQL queries.
 * @param <E> the class of the results
 */
@SuppressWarnings("serial")
public abstract class AbstractDomainPaginator<E> extends AbstractPaginator<E> implements InitializingBean {

	/**
	 * The daoService.
	 */
	private PaginatorDomainService domainService;

	/**
	 * Constructor.
	 */
	public AbstractDomainPaginator() {
		super();
	}

	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {
		Assert.notNull(this.domainService,
				"property domainService of class " + this.getClass().getName() + " can not be null");
	}

	/**
	 * @return the domainService
	 */
	public PaginatorDomainService getDomainService() {
		return domainService;
	}

	/**
	 * @param domainService the domainService to set
	 */
	public void setDomainService(final PaginatorDomainService domainService) {
		this.domainService = domainService;
	}


}

