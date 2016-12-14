package com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.services;

import javax.servlet.ServletRequest;

import com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.exception.BadTockenException;
/**
 * Interfaz que genera un tocken autenticando, autorizando y haciendo una auditoria del usuario que se quiere logear
 * @author Franco Antonio Chamás.
 */
public interface AccesoCreateTokenService {
	/**
	 * Método que genera un tocken previo una autenticación, autorización y auditoria.
	 * @param request contiene el usuario en el campo USER_NAME y la contraseña en el campo USER_PASSWORD.
	 * @return un Strig que representa un tocken.
	 * @throws BadTockenException
	 */
	String createTokenForUser(final ServletRequest request) throws BadTockenException;
	int getIdAplicacion();
	void setIdAplicacion(int idAplicacion);
}
