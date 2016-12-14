package com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;

import com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.exception.BadCreateTockenException;
import com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.mock.MockAutenticarAutorizarAuditoria;

/**
 * Implementaci�n del autenticador, simula una BD, solo a modo de test.
 * @author Franco Antonio Chamas.
 */
public class DefaultAccesoAutenticacionServiceImpl implements AccesoAutenticacionService {

	private static Logger logger = LoggerFactory.getLogger(DefaultAccesoAutenticacionServiceImpl.class);
	
	public UserDetails autenticar(UserDetails accesoUserDetails) throws BadCreateTockenException {
		logger.debug("autenticando usuario ");
		UserDetails retVal = null;
		validarCredenciales(accesoUserDetails);
		retVal = MockAutenticarAutorizarAuditoria.autenticar(accesoUserDetails);
		
		if(retVal == null) {
			logger.error("error al autenticar:" + BadCreateTockenException.MSJ_AUTENTICATION);
			throw new BadCreateTockenException(BadCreateTockenException.MSJ_AUTENTICATION);
		}
		return retVal;
	}
	
	/**
	 * Método que valida que el usuario o la contraseña tengan contenido.
	 * @param accesoUserDetails objeto que contiene las credenciales.
	 */
	private void validarCredenciales(UserDetails accesoUserDetails) throws BadCreateTockenException {
		logger.debug("Validando credenciales");
		if(accesoUserDetails.getUsername() == null || accesoUserDetails.getUsername().trim().equals("") ) {
			throw new BadCreateTockenException(BadCreateTockenException.MSJ_USUARIO_BLANCO);
		}
		
		if(accesoUserDetails.getPassword() == null || accesoUserDetails.getPassword().trim().equals("") ) {
			throw new BadCreateTockenException(BadCreateTockenException.MSJ_USUARIO_BLANCO);
		}
	}

}
