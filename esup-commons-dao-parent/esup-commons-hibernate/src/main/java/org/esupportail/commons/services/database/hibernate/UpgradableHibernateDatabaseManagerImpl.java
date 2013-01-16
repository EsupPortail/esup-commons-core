/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.services.database.hibernate;

import org.esupportail.commons.context.ApplicationContextHolder;
import org.esupportail.commons.exceptions.ConfigException;
import org.esupportail.commons.utils.Assert;
import org.esupportail.commons.utils.strings.StringUtils;
//TODO CL V2: lien vers module web
//import org.esupportail.commons.utils.ContextUtils;

/**
 * An abstract class for non updatable database managers.
 */
public class UpgradableHibernateDatabaseManagerImpl extends BasicHibernateDatabaseManagerImpl {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -9047128715359882698L;

	/**
	 * The name of the session factory bean (create mode).
	 */
	private String createSessionFactoryBeanName;

	/**
	 * The name of the JDBC session factory bean (upgrade mode).
	 */
	private String jdbcUpgradeSessionFactoryBeanName;

	/**
	 * The name of the JNDI session factory bean (upgrade mode).
	 */
	private String jndiUpgradeSessionFactoryBeanName;

	/**
	 * Bean constructor.
	 */
	public UpgradableHibernateDatabaseManagerImpl() {
		super();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		if (jdbcUpgradeSessionFactoryBeanName == null && jndiUpgradeSessionFactoryBeanName == null) {
			throw new ConfigException("properties [jdbcUpgradeSessionFactoryBeanName] "
					+ "and [jndiUpgradeSessionFactoryBeanName] of class ["
					+ getClass().getName() + "] can not be both null");
		}
		Assert.hasText(createSessionFactoryBeanName,
				"property [createSessionFactoryBeanName] of class ["
				+ getClass().getName() + "] can not be null");
	}

	/**
	 * @return the name of the upgrade session factory, depending on the context (web or not).
	 */
	protected String getUpgradeSessionFactoryBeanName() {
		String sessionFactoryBeanName;
		//TODO CL V2: lien vers module web
		//if (ContextUtils.isWeb()) {
			if (isUseJndi()) {
				sessionFactoryBeanName = jndiUpgradeSessionFactoryBeanName;
			} else {
				sessionFactoryBeanName = jdbcUpgradeSessionFactoryBeanName;
			}
//		} else {
//			if (!isUseJdbc()) {
//				throw new ConfigException(getClass() + ": batch commands "
//						+ "can not be used when property [useJdbc] "
//						+ "is false");
//			}
//			sessionFactoryBeanName = jdbcUpgradeSessionFactoryBeanName;
//		}
		return sessionFactoryBeanName;
	}

	@Override
	public boolean isUpgradable() {
		return true;
	}

	@Override
	public void create() {
		ApplicationContextHolder.getContext().getBean(createSessionFactoryBeanName);
	}

	@Override
	public void upgrade() {
		ApplicationContextHolder.getContext().getBean(getUpgradeSessionFactoryBeanName());
	}

	/**
	 * @param createSessionFactoryBeanName the createSessionFactoryBeanName to set
	 */
	public void setCreateSessionFactoryBeanName(final String createSessionFactoryBeanName) {
		this.createSessionFactoryBeanName = createSessionFactoryBeanName;
	}

	/**
	 * @param jdbcUpgradeSessionFactoryBeanName the jdbcUpgradeSessionFactoryBeanName to set
	 */
	public void setJdbcUpgradeSessionFactoryBeanName(
			final String jdbcUpgradeSessionFactoryBeanName) {
		this.jdbcUpgradeSessionFactoryBeanName =
			StringUtils.nullIfEmpty(jdbcUpgradeSessionFactoryBeanName);
	}

	/**
	 * @param jndiUpgradeSessionFactoryBeanName the jndiUpgradeSessionFactoryBeanName to set
	 */
	public void setJndiUpgradeSessionFactoryBeanName(
			final String jndiUpgradeSessionFactoryBeanName) {
		this.jndiUpgradeSessionFactoryBeanName =
			StringUtils.nullIfEmpty(jndiUpgradeSessionFactoryBeanName);
	}

	/**
	 * @param unused
	 */
	public void setUpgradeSessionFactoryBeanName(final String unused) {
		throw new ConfigException(getClass()
				+ ": property [upgradeSessionFactoryBeanName] is obsolete, "
				+ "use [jdbcSessionFactoryBeanName] instead and "
				+ "optionnaly [jndiSessionFactoryBeanName]");
	}

	@Override
	public boolean isTransactionnal() {
		return true;
	}

	@Override
	public void setTransactionnal(final boolean transactionnal) {
		throw new ConfigException(
				"property [" + transactionnal + "] is always true for class ["
				+ getClass().getName() + "]");
	}

}
