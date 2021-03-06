/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.services.database.hibernate;

import org.esupportail.commons.exceptions.ConfigException;
import org.esupportail.commons.services.database.AbstractBasicDatabaseManager;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.strings.StringUtils;

import org.springframework.beans.factory.InitializingBean;
//TODO CL V2: lien vers module web
//import org.esupportail.commons.utils.ContextUtils;

/**
 * An abstract class for non updatable database managers.
 */
@SuppressWarnings("serial")
public abstract class AbstractHibernateDatabaseManagerImpl
extends AbstractBasicDatabaseManager
implements InitializingBean {

	/**
	 * Holds the thread data.
	 */
	private ThreadLocal<HibernateThreadData> ts = new ThreadLocal<HibernateThreadData>();

	/**
	 * A logger.
	 */
	private final Logger logger = new LoggerImpl(getClass());

	/**
	 * True to use JDBC.
	 */
	private boolean useJdbc;

	/**
	 * True to use JNDI.
	 */
	private boolean useJndi;

	/**
	 * The name of the JDBC session factory bean (normal mode).
	 */
	private String jdbcSessionFactoryBeanName;

	/**
	 * The name of the JNDI session factory bean (normal mode).
	 */
	private String jndiSessionFactoryBeanName;

	/**
	 * Bean constructor.
	 */
	public AbstractHibernateDatabaseManagerImpl() {
		super();
	}

	@Override
    public void afterPropertiesSet() throws Exception {
		if (jdbcSessionFactoryBeanName == null && jndiSessionFactoryBeanName == null) {
			throw new ConfigException("properties [jdbcSessionFactoryBeanName] "
					+ "and [jndiSessionFactoryBeanName] of class ["
					+ getClass().getName() + "] can not be both null");
		}
	}

	/**
	 * @return the name of the session factory, depending on the context (web or not).
	 */
	protected String getSessionFactoryBeanName() {
		String sessionFactoryBeanName;
		//TODO CL V2: lien vers module web
		//if (ContextUtils.isWeb()) {
			if (useJndi) {
				sessionFactoryBeanName = jndiSessionFactoryBeanName;
			} else {
				sessionFactoryBeanName = jdbcSessionFactoryBeanName;
			}
//		} else {
//			if (!useJdbc) {
//				throw new ConfigException(getClass() + ": batch commands "
//						+ "can not be used when property [useJdbc] "
//						+ "is false");
//			}
//			sessionFactoryBeanName = jdbcSessionFactoryBeanName;
//		}
		return sessionFactoryBeanName;
	}

	@Override
	public void openSession() {
		String sessionFactoryBeanName = getSessionFactoryBeanName();
		HibernateThreadData td = ts.get();
		if (logger.isDebugEnabled()) {
			logger.debug("td=" + td);
		}
		if (td == null) {
			td = new HibernateThreadData();
			ts.set(td);
			if (logger.isDebugEnabled()) {
				logger.debug("OPEN(" + sessionFactoryBeanName + ")");
			}
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("OPEN(" + sessionFactoryBeanName + ") ***** td is not null!");
			}
		}
		td.openSession(sessionFactoryBeanName);
	}

	@Override
	public void beginTransaction() {
		String sessionFactoryBeanName = getSessionFactoryBeanName();
		if (!isTransactionnal()) {
			if (logger.isDebugEnabled()) {
				logger.debug("BEGIN(" + sessionFactoryBeanName + ") ***** not transactionnal!");
			}
			return;
		}
		HibernateThreadData td = ts.get();
		if (logger.isDebugEnabled()) {
			logger.debug("td=" + td);
		}
		if (td != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("BEGIN(" + sessionFactoryBeanName + ")");
			}
			td.beginTransaction(sessionFactoryBeanName);
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("BEGIN(" + sessionFactoryBeanName + ") ***** td is null!");
			}
		}
	}

	@Override
    public void endTransaction(
			final boolean commit) {
		String sessionFactoryBeanName = getSessionFactoryBeanName();
		if (!isTransactionnal()) {
			if (logger.isDebugEnabled()) {
				logger.debug(
						"END(" + sessionFactoryBeanName + ", "
						+ commit + ") ***** not transactionnal!");
			}
			return;
		}
		HibernateThreadData td = ts.get();
		if (logger.isDebugEnabled()) {
			logger.debug("td=" + td);
		}
		if (td != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("END(" + sessionFactoryBeanName + ", " + commit + ")");
			}
			td.endTransaction(sessionFactoryBeanName, commit);
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("END(" + sessionFactoryBeanName + ", " + commit + ") ***** td is null!");
			}
		}
	}

	@Override
    public void closeSession() {
		String sessionFactoryBeanName = getSessionFactoryBeanName();
		HibernateThreadData td = ts.get();
		if (logger.isDebugEnabled()) {
			logger.debug("td=" + td);
		}
		if (td != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("CLOSE(" + sessionFactoryBeanName + ")");
			}
			td.closeSession(sessionFactoryBeanName);
			ts.set(null);
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("CLOSE(" + sessionFactoryBeanName + ") ***** td is null!");
			}
		}
	}

	@Override
    public void test() {
		openSession();
		beginTransaction();
		endTransaction(false);
		closeSession();
	}

	/**
	 * @param jdbcSessionFactoryBeanName the jdbcSessionFactoryBeanName to set
	 */
	public void setJdbcSessionFactoryBeanName(final String jdbcSessionFactoryBeanName) {
		this.jdbcSessionFactoryBeanName = StringUtils.nullIfEmpty(jdbcSessionFactoryBeanName);
	}

	/**
	 * @param jndiSessionFactoryBeanName the jndiSessionFactoryBeanName to set
	 */
	public void setJndiSessionFactoryBeanName(final String jndiSessionFactoryBeanName) {
		this.jndiSessionFactoryBeanName = StringUtils.nullIfEmpty(jndiSessionFactoryBeanName);
	}

	/**
	 * @param unused
	 */
	public void setSessionFactoryBeanName(final String unused) {
		throw new ConfigException(getClass()
				+ ": property [sessionFactoryBeanName] is obsolete, "
				+ "use [jdbcSessionFactoryBeanName] instead and "
				+ "optionnaly [jndiSessionFactoryBeanName]");
	}

	/**
	 * @return the useJdbc
	 */
	protected boolean isUseJdbc() {
		return useJdbc;
	}

	/**
	 * @param useJdbc the useJdbc to set
	 */
	public void setUseJdbc(final boolean useJdbc) {
		this.useJdbc = useJdbc;
	}

	/**
	 * @return the useJndi
	 */
	protected boolean isUseJndi() {
		return useJndi;
	}

	/**
	 * @param useJndi the useJndi to set
	 */
	public void setUseJndi(final boolean useJndi) {
		this.useJndi = useJndi;
	}

}
