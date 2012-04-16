/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-blank
 */
package org.esupportail.example.services.application; 

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.esupportail.commons.beans.AbstractApplicationAwareBean;
import org.esupportail.commons.exceptions.ConfigException;
import org.esupportail.commons.services.application.Version;
import org.esupportail.commons.services.application.VersionException;
import org.esupportail.commons.services.application.VersionningService;
import org.esupportail.commons.utils.Assert;
import org.esupportail.example.domain.DomainService;
import org.springframework.beans.factory.InitializingBean;

/**
 * A bean for versionning management.
 */
public class VersionningServiceImpl extends AbstractApplicationAwareBean implements VersionningService {

	private static final long serialVersionUID = 1L;

	/**
	 * A logger.
	 */
	private static final Log log = LogFactory.getLog(VersionningServiceImpl.class);
	
	/**
	 * The injected DomainService
	 */
	DomainService domainService;

	/**
	 * @see org.esupportail.commons.services.application.VersionningService#initDatabase()
	 */
	public void initDatabase() {
		log.info("init database !");
		setDatabaseVersion("0.0.0", true);
		//code here data initialization
		upgradeDatabase();
	}

	/**
	 * Set the database version.
	 * @param version 
	 * @param silent true to omit info messages
	 */
	private void setDatabaseVersion(
			final String version, 
			final boolean silent) {
		getDomainService().updateDatabaseVersion(version);
		if (!silent) {
			log.info("database version set to " + version + ".");
		}
	}


	/**
	 * @see org.esupportail.commons.services.application.VersionningService#checkVersion()
	 */
	public void checkVersion() {
		checkVersion(true, false);
	};
	
	/**
	 * @see org.esupportail.commons.services.application.VersionningService#checkVersion(boolean, boolean)
	 */
	public void checkVersion(
			final boolean throwException,
			final boolean printLatestVersion) throws VersionException {
		Version databaseVersion = getDomainService().getDatabaseVersion();
		Version applicationVersion = getApplicationService().getVersion();
		if (databaseVersion == null) {
			String msg = "Your database is not initialized!";
			if (throwException) {
				throw new VersionException(msg);
			}
			log.error(msg);
			if (printLatestVersion) {
				printLastVersion();
			}
			return;
		}
		if (applicationVersion.equals(databaseVersion)) {
			String msg = "The database is up to date.";
			if (throwException) {
				if (log.isDebugEnabled()) {
					log.debug(msg);
				}
			} else {
				log.info(msg);
			}
			if (printLatestVersion) {
				printLastVersion();
			}
			return;
		}
		if (applicationVersion.isSameMajorAndMinor(databaseVersion)) {
			log.info("Database version is " + databaseVersion + ", upgrading...");
			updateDatabase();
			if (printLatestVersion) {
				printLastVersion();
			}
			return;
		}
		if (databaseVersion.isOlderThan(applicationVersion)) {
			String msg = "The database is too old (" + databaseVersion + ")!";
			if (throwException) {
				throw new VersionException(msg);
			}
			log.error(msg);
			if (printLatestVersion) {
				printLastVersion();
			}
			return;
		}
		String msg = "The application is too old (" + databaseVersion + "), please upgrade!";
		if (throwException) {
			throw new VersionException(msg);
		}
		if (printLatestVersion) {
			printLastVersion();
		}
		log.error(msg);
	}

	/**
	 * print the last version available.
	 */
	private void printLastVersion() {
		Version latestVersion = getApplicationService().getLatestVersion();
		if (latestVersion != null) {
			log.info("Latest version available: " + latestVersion);
		}
	}

	/**
	 * @see org.esupportail.commons.services.application.VersionningService#upgradeDatabase()
	 */
	public boolean upgradeDatabase() {
		log.info("update database !");
		if (getDatabaseVersion().equals(getApplicationService().getVersion())) {
			log.info("The database is up to date, no need to upgrade.");
			return false;
		}
		//code here data update
		upgradeDatabaseIfNeeded("0.0.1");
		if (!getDatabaseVersion().equals(getApplicationService().getVersion())) {
			setDatabaseVersion(getApplicationService().getVersion().toString(), false);
		}
		return false;
	}

	/**
	 * update the database content without altering objects
	 * @return if database need to be updated
	 * @see org.esupportail.commons.services.application.VersionningService#upgradeDatabase()
	 */
	public boolean updateDatabase() {
		log.info("update database !");
		if (getDatabaseVersion().equals(getApplicationService().getVersion())) {
			log.info("The database is up to date, no need to upgrade.");
			return false;
		}
		upgradeDatabaseIfNeeded("0.0.1");
		if (!getDatabaseVersion().equals(getApplicationService().getVersion())) {
			setDatabaseVersion(getApplicationService().getVersion().toString(), false);
		}
		return false;
	}

	/**
	 * Upgrade the database to version 0.0.1.
	 */
	public void upgrade0d0d1() {
		// nothing to do yet
		log.info("upgrade0d1d0 !");
	}

	/**
	 * Upgrade the database to a given version, if needed.
	 * @param version 
	 */
	@SuppressWarnings("unchecked")
	private void upgradeDatabaseIfNeeded(final String version) {
		if (!getDatabaseVersion().isOlderThan(version)) {
			return;
		}
		printOlderThanMessage(version);
		String methodName = "upgrade" + version.replace('.', 'd');
		Class [] methodArgs = new Class [] {};
		Method method;
		try {
			method = getClass().getMethod(methodName, methodArgs);
		} catch (SecurityException e) {
			throw new ConfigException(
					"access to the information of class " + getClass() + " was denied", e);
		} catch (NoSuchMethodException e) {
			throw new ConfigException(
					"could no find method " + getClass() + "." + methodName + "()", e);
		}
		Exception invocationException = null;
		try {
			method.invoke(this, new Object[] {});
			setDatabaseVersion(version, false);
			return;
		} catch (IllegalArgumentException e) {
			invocationException = e;
		} catch (IllegalAccessException e) {
			invocationException = e;
		} catch (InvocationTargetException e) {
			if (e.getCause() == null) {
				invocationException = e;
			} else if (e.getCause() instanceof Exception) {
				invocationException = (Exception) e.getCause();
			} else {
				invocationException = e;
			}
		}
		throw new ConfigException(
				"could no invoke method " + getClass() + "." + methodName + "()", 
				invocationException);
	}

	/**
	 * Print a message saying that the database version is older than ...
	 * @param version the new version
	 */	
	private void printOlderThanMessage(final String version) {
		log.info(new StringBuffer("database version (")
		.append(getDomainService().getDatabaseVersion())
		.append(") is older than ")
		.append(version)
		.append(", upgrading..."));
	}

	/**
	 * @return the database version.
	 */
	private Version getDatabaseVersion() {
		return getDomainService().getDatabaseVersion();
	}

	/**
	 * @return the domainService
	 */
	public DomainService getDomainService() {
		return domainService;
	}

	/**
	 * @param domainService the domainService to set
	 */
	public void setDomainService(DomainService domainService) {
		this.domainService = domainService;
	}

	/* (non-Javadoc)
	 * @see org.esupportail.commons.beans.AbstractApplicationAwareBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		Assert.notNull(this.domainService, 
				"property domainService of class " + this.getClass().getName() 
				+ " can not be null");

	}

}
