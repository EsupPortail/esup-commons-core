/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.services.application;

import org.esupportail.commons.exceptions.ConfigException;

/**
 * A versionning service that does nothing.
 */
public class VoidVersionningServiceImpl implements VersionningService {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 4821925955078137251L;

	/**
	 * Bean constructor.
	 */
	public VoidVersionningServiceImpl() {
		super();
	}

	@Override
	public void checkVersion(final boolean throwException, final boolean printLatestVersion)
	throws ConfigException {
		// nothing to do here
	}

	@Override
	public void initDatabase() {
		// nothing to do here
	}

	@Override
	public boolean upgradeDatabase() {
		return false;
	}

	@Override
	public void checkVersion() throws VersionException {
		// TODO Auto-generated method stub
	}

}
