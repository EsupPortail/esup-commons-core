/**
* 
* 
* 
*/
package org.esupportail.commons.dao;

import org.esupportail.commons.exceptions.ConfigException;
import org.esupportail.commons.utils.BeanUtils;
//TODO CL V2: lien vers module web
//import org.esupportail.commons.utils.ContextUtils;
import org.hibernate.SessionFactory;

/**
 * @author ylecuyer
 *
 */
public class HibernateTransactionManager 
	extends org.springframework.orm.hibernate3.HibernateTransactionManager {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 1274615968543130119L;

	/*
	 *************************** PROPERTIES ******************************** */

	/**
	 * True to use JNDI.
	 */
	private boolean useJndi;
	
	/**
	 * True to use JNDI.
	 */
	private boolean isUseJdbc;
	
	/**
	 * The name of the JDBC session factory bean (upgrade mode).
	 */
	private String jdbcSessionFactoryBeanName;
	
	/**
	 * The name of the JNDI session factory bean (upgrade mode).
	 */
	private String jndiSessionFactoryBeanName;

	/*
	 *************************** INIT ************************************** */

	/**
	 * Constructor.
	 */
	public HibernateTransactionManager() {
		super();
	}

	/**
	 * Constructor.
	 */
	public HibernateTransactionManager(final SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTransactionManager#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() {
		isUseJdbc = true;
	}

	/*
	 *************************** METHODS *********************************** */

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTransactionManager#getSessionFactory()
	 */
	@Override
	public SessionFactory getSessionFactory() {
		return (SessionFactory) BeanUtils.getBean(getSessionFactoryBeanName());

	}
	
	
	/**
	 * @return the name of the upgrade session factory, depending on the context (web or not).
	 */
	protected String getSessionFactoryBeanName() {
		String sessionFactoryBeanName;
		//TODO CL V2: lien vers module web
		//if (ContextUtils.isWeb()) {
			if (isUseJndi()) {
				sessionFactoryBeanName = jndiSessionFactoryBeanName;
			} else {
				sessionFactoryBeanName = jdbcSessionFactoryBeanName;
			}
//		} else {
//			if (!isUseJdbc) {
//				throw new ConfigException(getClass() + ": batch commands "
//						+ "can not be used when property [useJdbc] "
//						+ "is false");
//			}
//			sessionFactoryBeanName = jdbcSessionFactoryBeanName;
//		}
		return sessionFactoryBeanName;
	}
	
	/*
	 *************************** ACCESSORS ********************************* */

	/**
	 * @return the useJndi
	 */
	public boolean isUseJndi() {
		return useJndi;
	}

	/**
	 * @param useJndi the useJndi to set
	 */
	public void setUseJndi(final boolean useJndi) {
		this.useJndi = useJndi;
	}
	
	

	/**
	 * @return the jdbcSessionFactoryBeanName
	 */
	public String getJdbcSessionFactoryBeanName() {
		return jdbcSessionFactoryBeanName;
	}

	/**
	 * @param jdbcSessionFactoryBeanName the jdbcSessionFactoryBeanName to set
	 */
	public void setJdbcSessionFactoryBeanName(final String jdbcSessionFactoryBeanName) {
		this.jdbcSessionFactoryBeanName = jdbcSessionFactoryBeanName;
	}

	/**
	 * @return the jndiSessionFactoryBeanName
	 */
	public String getJndiSessionFactoryBeanName() {
		return jndiSessionFactoryBeanName;
	}

	/**
	 * @param jndiSessionFactoryBeanName the jndiSessionFactoryBeanName to set
	 */
	public void setJndiSessionFactoryBeanName(final String jndiSessionFactoryBeanName) {
		this.jndiSessionFactoryBeanName = jndiSessionFactoryBeanName;
	}

	

}

