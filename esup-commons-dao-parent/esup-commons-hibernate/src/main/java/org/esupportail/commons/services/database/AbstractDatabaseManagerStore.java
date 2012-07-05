/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.services.database;

import java.util.List;

/**
 * An abstract implementation of the database manager store.
 */
@SuppressWarnings("serial")
public abstract class AbstractDatabaseManagerStore implements DatabaseManagerStore {

	/**
	 * Bean constructor.
	 */
	public AbstractDatabaseManagerStore() {
		super();
	}

	/**
	 * @see org.esupportail.commons.services.database.DatabaseManagerStore#open()
	 */
	public void open() throws DatabaseException {
		try {
			if (getDatabaseManagers() != null) {
				for (DatabaseManager databaseManager : getDatabaseManagers()) {
					databaseManager.openSession();
				}
			}
		} catch (Throwable t) {
			throw new DatabaseException(t);
		}
	}

	/**
	 * @see org.esupportail.commons.services.database.DatabaseManagerStore#begin()
	 */
	public void begin() throws DatabaseException {
		try {
			if (getDatabaseManagers() != null) {
				for (DatabaseManager databaseManager : getDatabaseManagers()) {
					databaseManager.beginTransaction();
				}
			}
		} catch (Throwable t) {
			throw new DatabaseException(t);
		}
	}

	/**
	 * @see org.esupportail.commons.services.database.DatabaseManagerStore#end(boolean)
	 */
	public void end(final boolean commit) throws DatabaseException {
		Throwable error = null;
		if (getDatabaseManagers() != null) {
			for (DatabaseManager databaseManager : getDatabaseManagers()) {
				try {
					databaseManager.endTransaction(commit);
				} catch (Throwable t) {
					error = t;
				}
			}
		}
		if (error != null) {
			throw new DatabaseException(error);
		}
	}

	/**
	 * @see org.esupportail.commons.services.database.DatabaseManagerStore#close()
	 */
	public void close() throws DatabaseException {
		Throwable error = null;
		if (getDatabaseManagers() != null) {
			for (int i = getDatabaseManagers().size() - 1; i >= 0; i--) {
				try {
					getDatabaseManagers().get(i).closeSession();
				} catch (Throwable t) {
					error = t;
				}
			}
		}
		if (error != null) {
			throw new DatabaseException(error);
		}
	}

	/**
	 * @see org.esupportail.commons.services.database.DatabaseManagerStore#test()
	 */
	public void test() throws DatabaseException {
		try {
			if (getDatabaseManagers() != null) {
				for (DatabaseManager databaseManager : getDatabaseManagers()) {
					databaseManager.test();
				}
			}
		} catch (Throwable t) {
			throw new DatabaseException(t);
		}
	}

	/**
	 * @see org.esupportail.commons.services.database.DatabaseManagerStore#create()
	 */
	public void create() throws DatabaseException {
		try {
			if (getDatabaseManagers() != null) {
				for (DatabaseManager databaseManager : getDatabaseManagers()) {
					if (databaseManager.isUpgradable()) {
						databaseManager.create();
					}
				}
			}
		} catch (Throwable t) {
			throw new DatabaseException(t);
		}
	}

	/**
	 * @see org.esupportail.commons.services.database.DatabaseManagerStore#update()
	 */
	public void update() throws DatabaseException {
		try {
			if (getDatabaseManagers() != null) {
				for (DatabaseManager databaseManager : getDatabaseManagers()) {
					if (databaseManager.isUpgradable()) {
						databaseManager.upgrade();
					}
				}
			}
		} catch (Throwable t) {
			throw new DatabaseException(t);
		}
	}

	/**
	 * @return the database mangers of the store.
	 */
	protected abstract List<DatabaseManager> getDatabaseManagers();

}
