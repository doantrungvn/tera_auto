package org.terasoluna.qp.app.common.ultils;

/**
 * Runtimeexception that indicates something went wrong with message-resolving during the initialization-phase.
 *
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class MessageInitializationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MessageInitializationException(String message, RuntimeException e) {
		super(message, e);
	}
}
