package com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.services;

import org.springframework.security.core.userdetails.UserDetails;

import com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.exception.BadCreateTockenException;

/**
 * Interfaz que se implementa para hacer la auditoria al momento de generar el tocken.
 * @author Franco Antonio Chamas.
 *
 */
public interface AccesoAuditoriaService {
	/**
	 * Método que realiza la auditoria al generar el tocken.
	 * @param accesoUserDetails objeto que contiene las credenciales y roles.
	 * @param idAplicativo identificador del aplicativo.
	 * @throws BadCreateTockenException en caso que exista un error en la autorizaciï¿½n.
	 */
	void auditoria(UserDetails accesoUserDetails, int idAplicativo) throws BadCreateTockenException;
}
