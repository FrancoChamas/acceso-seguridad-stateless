package com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.services;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.util.LoadProperties;

/**
 * Clase servicio que provee un IserDetails.
 * @author Franco Antonio Chamás.
 *
 */
public class DefaultAccesoUserDetailsService implements UserDetailsService , InitializingBean {
	@Autowired(required=false)
    private TokenService tokenService;
	
	private static Logger logger = LoggerFactory.getLogger(DefaultAccesoUserDetailsService.class);
	
	public void afterPropertiesSet() throws ServletException {
		logger.debug("invocando afterPropertiesSet");
		validateInstance();
	}
    
	/**
	 * Método que retorna un UserDetails desde un tocken.
	 * @author Franco Antonio Chamás.
	 *
	 */
    public final UserDetails loadUserByUsername(String token) throws UsernameNotFoundException, 
    		BadCredentialsException {
    	logger.debug("Cargando usuario por nombre de usuario");
    	validateInstance();
    	final UserDetails accesoUserDetails =  tokenService.parseUserFromToken(token);
    	
        if (accesoUserDetails == null) {
        	logger.error("Error obteniendo detalle de usuario, Usuario no válido");
            throw new UsernameNotFoundException("Usuario no válido");
        }
        return accesoUserDetails;
    }

	/**
	 * Obtiene una instancia de DefaultAccesoUserDetailsService.
	 * @return  DefaultAccesoUserDetailsService
	 */
	private TokenService getTokenService() {
		logger.debug("Obteniendo una instancia de TokenService");
		String secret = LoadProperties.getPropertie("config", "secret");
		String minutosExpiracionTocken = LoadProperties.getPropertie("config", "minutosExpiracionTocken");
		return new TokenServiceJJwtImpl(secret,Integer.parseInt(minutosExpiracionTocken));
	}
	
	/**
	 * Valida que exista una instancia de userDetailsService, si no existe asigna una implementación por defecto
	 */
	private void validateInstance() {
		logger.debug("Validando instancias");
		tokenService = tokenService == null ? getTokenService() : tokenService;
	}

}
