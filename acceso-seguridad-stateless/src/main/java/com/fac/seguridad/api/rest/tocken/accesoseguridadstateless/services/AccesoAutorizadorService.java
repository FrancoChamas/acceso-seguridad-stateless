package com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.services;

import org.springframework.security.core.userdetails.UserDetails;

import com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.exception.BadCreateTockenException;

/**
 * Interfaz que se implementa para autorizar a los usuarios.
 * @author Franco Antonio Chamas.
 *
 */
public interface AccesoAutorizadorService {
	/**
	 * M�todo que autoriza al usuario.
	 * @param accesoUserDetails objeto que contiene las credenciales.
	 * @param idAplicativo identificador del aplicativo.
	 * @return una implementacion de UserDetails con la lista de roles.
	 * @throws BadCreateTockenException en caso que exista un error en la autorizaci�n.
	 */
	UserDetails autorizar(UserDetails accesoUserDetails, int idAplicacion) throws BadCreateTockenException;
}
