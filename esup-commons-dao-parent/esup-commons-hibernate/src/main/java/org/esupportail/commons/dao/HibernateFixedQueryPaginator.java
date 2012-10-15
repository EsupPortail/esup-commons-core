/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.dao;

/**
 * A Hibernate paginator that uses a fixed query.
 * @param <E> the class of the results
 */
public class HibernateFixedQueryPaginator<E> extends AbstractHibernateQueryPaginator<E> {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -4330439368349986870L;

	/**
	 * The fixed query string.
	 */
	private String queryString;

	/**
	 * make the hqlQuery.
	 */
	private HqlQueryPojo hqlQueryPojo;

	/**
	 * Constructor.
	 * @param queryString
	 * @param hql
	 */
	public HibernateFixedQueryPaginator(
			final String queryString,
			final HqlQueryPojo hql) {
		super();
		this.queryString = queryString;
		this.hqlQueryPojo = hql;
	}

	/**
	 * Constructor.
	 */
	public HibernateFixedQueryPaginator() {
		super();
	}

	@Override
	protected final String getQueryString() {
		return queryString;
	}

	/**
	 * @param queryString the queryString to set
	 */
	public void setQueryString(final String queryString) {
		this.queryString = queryString;
	}

	@Override
	protected HqlQueryPojo getHqlQueryPojo() {
		return hqlQueryPojo;
	}

	/**
	 * @param hqlQueryPojo the hqlQueryPojo to set
	 */
	public void setHqlQueryPojo(final HqlQueryPojo hqlQueryPojo) {
		this.hqlQueryPojo = hqlQueryPojo;
	}

}

