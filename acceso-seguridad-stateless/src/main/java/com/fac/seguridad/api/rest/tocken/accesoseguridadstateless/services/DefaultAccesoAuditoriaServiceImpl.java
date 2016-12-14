package com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.services;

import org.springframework.security.core.userdetails.UserDetails;

import com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.exception.BadCreateTockenException;
import com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.mock.MockAutenticarAutorizarAuditoria;

/**
 * @author Franco Antonio Chamás.
 * Clase implementación de Auditoria, simula una BD, solo a modo de test.
 *
 */
public class DefaultAccesoAuditoriaServiceImpl implements AccesoAuditoriaService {

	public void auditoria(UserDetails accesoUserDetails, int idAplicativo) throws BadCreateTockenException {
		validarUsuarioContrasenia(accesoUserDetails);
		
		MockAutenticarAutorizarAuditoria.auditar(accesoUserDetails);
		
	}
	
	/**
	 * Mï¿½todo que valida que el usuario o la contraseï¿½a tengan contenido
	 * @param accesoUserDetails
	 */
	private void validarUsuarioContrasenia(UserDetails accesoUserDetails) throws BadCreateTockenException {
		
		if(accesoUserDetails.getUsername() == null || accesoUserDetails.getUsername().trim().equals("") ) {
			throw new BadCreateTockenException(BadCreateTockenException.MSJ_USUARIO_BLANCO);
		}
		
		if(accesoUserDetails.getPassword() == null || accesoUserDetails.getPassword().trim().equals("") ) {
			throw new BadCreateTockenException(BadCreateTockenException.MSJ_USUARIO_BLANCO);
		}
	}

}
