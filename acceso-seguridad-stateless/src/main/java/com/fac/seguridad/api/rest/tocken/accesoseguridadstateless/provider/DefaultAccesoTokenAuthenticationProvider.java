package com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.provider;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.dominio.AccesoUserAuthentication;
import com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.exception.BadCredentialsTockenException;
import com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.services.DefaultAccesoUserDetailsService;

/**
 * Clave proveedor de autenticacion para un JWT.
 * @author Franco Antonio Cham√°s.
 *
 */
public final class DefaultAccesoTokenAuthenticationProvider implements AuthenticationProvider, InitializingBean {
	@Autowired(required=false)
    private UserDetailsService userDetailsService;
	
	private static Logger logger = LoggerFactory.getLogger(DefaultAccesoTokenAuthenticationProvider.class);
    
	public void afterPropertiesSet() throws ServletException {
		logger.debug("invocando afterPropertiesSet");
		validateInstance();
	}
    
	/**
	 * MÈtodo que autentica un usuario basado en un JWT.
	 * @param authentication objeto que contienen el tocken.
	 * @return Authentication resultante de la autenticaciÛn.
	 */
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		logger.debug("Autenticando usuario");
		final String token = ((AccesoUserAuthentication) authentication).getAuthorization();
		Authentication retVal = null;

		if (token != null && !token.isEmpty()) {
			validateInstance();
			UserDetails userDetails = userDetailsService.loadUserByUsername(token);

			if (userDetails != null) {
				retVal =  new AccesoUserAuthentication(userDetails);
			}
		} else {
			logger.error("Error autenticando usuario: " + BadCredentialsTockenException.MSJ_ERROR_NO_TOCKEN);
			throw new BadCredentialsTockenException(BadCredentialsTockenException.MSJ_ERROR_NO_TOCKEN);
		}

		return retVal;
	}
	
	/**
	 * Obtiene una instancia de DefaultAccesoUserDetailsService.
	 * @return  DefaultAccesoUserDetailsService
	 */
	private DefaultAccesoUserDetailsService getDefaultAccesoUserDetailsService() {
		return new DefaultAccesoUserDetailsService();
	}
	
	/**
	 * Valida que exista una instancia de userDetailsService, si no existe asigna una implementaci√≥n por defecto
	 */
	private void validateInstance() {
		logger.debug("Validando instancias");
		userDetailsService = userDetailsService == null ? getDefaultAccesoUserDetailsService() : userDetailsService;
	}

	
	public boolean supports(Class<?> authentication) {
		return false;
	}
}
