package com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Franco Antonio Chamas.
 *  Exception general para el trabajo con JWT.
 */
public class BadTockenException extends AuthenticationException {
	static final long serialVersionUID = -3387516993124229448L;
	public static final String MSJ_ERROR = "Error tocken";

	/**
	 * Constructor
	 */
	 public BadTockenException() {
		 super(MSJ_ERROR);
	 }

	/**
	 * Constructor con par�metro
	 * 
	 * @param message
	 *            mensaje a mostrar.
	 */
	public BadTockenException(String message) {
		super(message);
	}

	/**
	 * Constructor con par�metros
	 * 
	 * @param message
	 *            mensaje a mostrar
	 * @param throwable
	 *            excepci�n.
	 */
	public BadTockenException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
