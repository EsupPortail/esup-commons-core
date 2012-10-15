/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.services.database;

/**
 * An abstract class for non updatable database managers.
 */
@SuppressWarnings("serial")
public abstract class AbstractBasicDatabaseManager implements DatabaseManager {

    @Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public void create() {
		throw new UnsupportedOperationException(getClass().getCanonicalName() + ".create()");
	}

	@Override
	public void upgrade() {
		throw new UnsupportedOperationException(getClass().getCanonicalName() + ".upgrade()");
	}

}
