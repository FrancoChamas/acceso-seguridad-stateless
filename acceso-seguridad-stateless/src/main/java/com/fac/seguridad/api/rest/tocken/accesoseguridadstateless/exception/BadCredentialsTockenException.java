package com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.exception;

/**
 * @author Franco Antonio Chamas.
 *  Exception validando credenciales con JWT.
 */
public class BadCredentialsTockenException extends BadTockenException {
	static final long serialVersionUID = -3387516993124229448L;
	public static final String MSJ_ERROR = "Error validando tocken";
	public static final String MSJ_ERROR_NO_TOCKEN = "Error, el campo del header authentication no contiene el tocken";
	public static final String MSJ_ERROR_EXPIRO_TOCKEN = "Error validando tocken, tocken expir�";
	

	/**
	 * Constructor
	 */
	 public BadCredentialsTockenException() {
		 super();
	 }

	/**
	 * Constructor con par�metro
	 * 
	 * @param message
	 *            mensaje a mostrar.
	 */
	public BadCredentialsTockenException(String message) {
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
	public BadCredentialsTockenException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
