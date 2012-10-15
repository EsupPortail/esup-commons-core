/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.mock;

import java.io.InputStream;

import org.apache.myfaces.custom.fileupload.StorageStrategy;
import org.apache.myfaces.custom.fileupload.UploadedFile;

/**
 * An uploaded file mock.
 *
 */
public class MockUploadedFile implements UploadedFile {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 6640876144695737829L;

	/**
	 * The input stream.
	 */
	private InputStream stream;

	/**
	 * The file name.
	 */
	private String name;

	/**
	 * @param stream
	 * @param name
	 */
	public MockUploadedFile(
			final InputStream stream,
			final String name) {
		super();
		this.stream = stream;
		this.name = name;
	}

	@Override
	public byte[] getBytes() {
		return null;
	}

	@Override
	public String getContentType() {
		return null;
	}

	@Override
	public InputStream getInputStream() {
		return stream;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public long getSize() {
		return 0;
	}

	@Override
	public StorageStrategy getStorageStrategy() {
		// TODO CL V2 : new implement method injsf 1.2
		return null;
	}

}
