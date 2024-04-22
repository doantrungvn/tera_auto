package org.terasoluna.qp.app.message.translator.microsoft;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class ProxyAuthenticator extends Authenticator {
	/** The username to authenticate with. */
	private final String userName;
	/** The password to authenticate with. */
	private final char password[];

	/**
	 * Create a ProxyAuthenticator with the specified username and password.
	 *
	 * @param userName
	 *            the username to authenticate with
	 * @param password
	 *            the password to authenticate with
	 */
	public ProxyAuthenticator(String userName, String password) {
		this.userName = userName;
		this.password = password.toCharArray();
	}

	/**
	 * Return a PasswordAuthentication instance using the userName and password
	 * specified in the constructor. Only applies to PROXY request types.
	 *
	 * @return a PasswordAuthentication instance to use for authenticating with
	 *         the proxy
	 */
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		switch (getRequestorType()) {
		case PROXY:
			return new PasswordAuthentication(userName, password);
		case SERVER:
			break;
		default:
			break;
		}
		return null;
	}
}