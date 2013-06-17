/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.mail;

import java.io.Serializable;


/**
 * A class that represents SMTP servers.
 */
public class SmtpServer implements Serializable {

	/**
	 * The default host.
	 */
	public static final String DEFAULT_HOST = "localhost";

	/**
	 * The default port.
	 */
	public static final int DEFAULT_PORT = 25;

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -3312752352514102329L;

	/**
	 * The host name or IP number of the server.
	 */
	private String host;

	/**
	 * The port number the SMTP server is running on.
	 */
	private Integer port;

	/**
	 * The name of the user used to connect to the SMTP server.
	 */
	private String user;

	/**
	 * The password of the user used to connect to the SMTP server.
	 */
	private String password;

	/**
	 * Constructor.
	 */
	private SmtpServer() {
		this.host = null;
		this.port = null;
	}
	
	public final static SmtpServer createInstance(final String host, final Integer port, final String user, final String password) {
		SmtpServer server = new SmtpServer()
			.withHost(host)
			.withPort(port)
			.withUser(user)
			.withPassword(password);
		if (server.host == null) {
			server.setDefaultHost();
		}
		if (server.port == null) {
			server.setDefaultPort();
		}
		if (server.user == null || "".equals(server.user.trim())) {
			server.setUser(null);
		} else if (server.password == null) {
			server.setPassword("");
		}
		return server;
	}
	
	public final static SmtpServer createInstance(final String host, final int port) {
		return createInstance(host, port, null, null);
	}
	
	public final static SmtpServer createInstance() {
		return createInstance(DEFAULT_HOST, DEFAULT_PORT, null, null);
	}

	/**
	 * @return The host.
	 */
	public String getHost() {
		return this.host;
	}
	
	/**
	 * @param host The host to set.
	 */
	public void setHost(final String host) {
		this.host = host;
	}
	
	/**
	 * @param host The host to set.
	 */
	public SmtpServer withHost(final String host) {
		this.host = host;
		return this;
	}
	
	/**
	 * Set the default host.
	 */
	public void setDefaultHost() {
		setHost(DEFAULT_HOST);
	}
	
	/**
	 * Set the default host.
	 * @return 
	 */
	public SmtpServer withDefaultHost() {
		setDefaultHost();
		return this;
	}

	/**
	 * @return The port.
	 */
	public int getPort() {
		return this.port;
	}
	
	/**
	 * @param port The port to set.
	 */
	public void setPort(final Integer port) {
		this.port = port;
	}
	
	/**
	 * @param port The port to set.
	 */
	public SmtpServer withPort(final Integer port) {
		this.port = port;
		return this;
	}

	/**
	 * Set the default port.
	 */
	public void setDefaultPort() {
		setPort(DEFAULT_PORT);
	}
	
	/**
	 * Set the default port.
	 * @return 
	 */
	public SmtpServer withDefaultPort() {
		setDefaultPort();
		return this;
	}

	/**
	 * @return The password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password The password to set.
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * @param password The password to set.
	 * @return 
	 */
	public SmtpServer withPassword(final String password) {
		this.password = password;
		return this;
	}

	/**
	 * @return The user.
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user The user to set.
	 */
	public void setUser(final String user) {
		this.user = user;
	}
	
	/**
	 * @param user The user to set.
	 * @return 
	 */
	public SmtpServer withUser(final String user) {
		this.user = user;
		return this;
	}

}
