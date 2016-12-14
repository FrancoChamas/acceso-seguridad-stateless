package com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.services;

import org.springframework.security.core.userdetails.UserDetails;

import com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.exception.BadCreateTockenException;


/**
 * Interfaz que se implementa para autenticar a los usuarios.
 * @author Franco Antonio Chamas.
 *
 */
public interface AccesoAutenticacionService {
	
	/**
	 * Método que autentica al usuario.
	 * @param accesoUserDetails objeto que contiene las credenciales.
	 * @return una implementacion de UserDetails si se auntentica correctamente
	 * @throws BadCreateTockenException en caso que exista un error en la autenticación.
	 */
	UserDetails autenticar(UserDetails accesoUserDetails) throws BadCreateTockenException;
}
