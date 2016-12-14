package com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.services;

import org.springframework.security.core.userdetails.UserDetails;

import com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.exception.BadCreateTockenException;
import com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.mock.MockAutenticarAutorizarAuditoria;

/**
 * @author Franco Antonio Chamás.
 * Clase implementación Autorizador, simula una BD, solo a modo de test.
 *
 */
public class DefaultAccesoAutorizadorServiceImpl implements AccesoAutorizadorService {

	public UserDetails autorizar(UserDetails accesoUserDetails, int idAplicacion) throws BadCreateTockenException {
		UserDetails retVal = null;
		validarCredenciales(accesoUserDetails);
		retVal = MockAutenticarAutorizarAuditoria.autorizar(accesoUserDetails, idAplicacion);
		accesoUserDetails = retVal;
		
		if(retVal == null) {
			throw new BadCreateTockenException(BadCreateTockenException.MSJ_AUTENTICATION);
		}
		return retVal;
	}
	

	/**
	 * Método que valida que el usuario o la contraseï¿½a tengan contenido
	 * @param accesoUserDetails
	 */
	private void validarCredenciales(UserDetails accesoUserDetails) throws BadCreateTockenException {
		
		if(accesoUserDetails.getUsername() == null | accesoUserDetails.getUsername().trim().equals("") ) {
			throw new BadCreateTockenException(BadCreateTockenException.MSJ_USUARIO_BLANCO);
		}
		
		if(accesoUserDetails.getPassword() == null | accesoUserDetails.getPassword().trim().equals("") ) {
			throw new BadCreateTockenException(BadCreateTockenException.MSJ_USUARIO_BLANCO);
		}
	}

}
