/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.mail.model;

/**
 * A class that encapsulates data representing an SMTP server.
 */
public final class SmtpServerData {

	/**
	 * The default host.
	 */
	public static final String DEFAULT_HOST = "localhost";

	/**
	 * The default port.
	 */
	public static final int DEFAULT_PORT = 25;

	/**
	 * The host name or IP number of the server.
	 */
	private final String host;

	/**
	 * The port number the SMTP server is running on.
	 */
	private final Integer port;

	/**
	 * The name of the user used to connect to the SMTP server.
	 */
	private final String user;

	/**
	 * The password of the user used to connect to the SMTP server.
	 */
	private final String password;

    private SmtpServerData(String host, Integer port, String user, String password) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String
                host = "localhost",
                user = "",
                password = "";
        private Integer port = 25;

        public Builder host(final String host) {
            this.host = host;
            return this;
        }

        public Builder user(final String user) {
            this.user = user;
            return this;
        }

        public Builder password(final String password) {
            this.password = password;
            return this;
        }

        public Builder port(final Integer port) {
            this.port = port;
            return this;
        }

        public SmtpServerData build() {
            return new SmtpServerData(host, port, user, password);
        }
    }

    public String getHost() { return host; }

    public Integer getPort() { return port; }

    public String getUser() { return user; }

    public String getPassword() { return password; }
}
