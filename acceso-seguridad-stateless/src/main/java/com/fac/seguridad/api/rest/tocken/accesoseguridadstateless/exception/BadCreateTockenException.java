package com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.exception;

/**
 * Exception general para el trabajo con JWT.
 * @author Franco Antonio Chamas.
 *  
 */
public class BadCreateTockenException extends BadTockenException {
	static final long serialVersionUID = -3387516993124229448L;
	public static final String MSJ_ERROR = "Error creando tocken ";
	public static final String MSJ_AUTENTICATION = "Error al crear el tocken, usuario inexistente";
	public static final String MSJ_AUTHORIZATION = "Error al crear el tocken, no tiene roles para esta aplicacion";
	public static final String MSJ_USUARIO_BLANCO= "Error al crear el tocken, usuario no puede estan en blanco";
	public static final String MSJ_CONTRASENIA_BLANCO= "Error al crear el tocken, la contrase�a sno puede estan en blanco";
	
	/**
	 * Constructor
	 */
	 public BadCreateTockenException() {
		 super();
	 }

	/**
	 * Constructor con par�metro
	 * 
	 * @param message
	 *            mensaje a mostrar.
	 */
	public BadCreateTockenException(String message) {
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
	public BadCreateTockenException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
