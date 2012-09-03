/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.utils.lock;

import java.io.File;
import java.io.IOException;


/**
 * A cross-JVMs file lock.
 */
public class FileLockImpl implements Lock {

	/**
	 * The lock filename.
	 */
	private String filename;

	/**
	 * Constructor.
	 * @param filename
	 */
	public FileLockImpl(final String filename) {
		super();
		this.filename = filename;
	}

	@Override
	public void lock() throws AlreadyLockedException {
	    try {
	        File file = new File(filename);
	        if (!file.createNewFile()) {
	        	throw new AlreadyLockedException("file [" + filename + "] is already locked");
	        }
	    } catch (IOException e) {
	    	throw new LockException("could not lock file [" + filename + "]");
	    }
	}

	@Override
	public boolean tryLock() {
	    try {
	        lock();
	        return true;
	    } catch (AlreadyLockedException e) {
	    	return false;
	    }
	}

	@Override
	public void unlock() {
        File file = new File(filename);
        if (!file.delete()) {
        	throw new NotLockedException("file [" + filename + "] is not locked");
        }
	}

}
