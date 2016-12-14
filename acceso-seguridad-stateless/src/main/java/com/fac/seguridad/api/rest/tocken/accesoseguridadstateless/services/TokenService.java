package com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.services;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Interfaz Utilidad para trabajar con JWT
 * @author Franco Antonio Chamás.
 *
 */
public interface TokenService {

	/**
	 * Metodo que obtiene un 
	 * @param token
	 * @return
	 */
	UserDetails parseUserFromToken(String token);
	
    /**
     * M�todo que genera un JWT en funcion de un UserDetails previamente autenticado y autorizado.
     * @param user usuario a partir del cual se genera el tocken.
     * @return un tocken en formato String
     */
    String createTokenForUser(UserDetails user);
}
