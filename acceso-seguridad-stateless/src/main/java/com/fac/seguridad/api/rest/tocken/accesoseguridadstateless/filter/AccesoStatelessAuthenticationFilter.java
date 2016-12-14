package com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.filter.GenericFilterBean;

import com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.dominio.AccesoUserAuthentication;
import com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.handler.AuthenticationSuccessHandler;
import com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.handler.DefaultAuthenticationAccessDeniedHandler;
import com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.handler.DefaultAuthenticationAccessSuccessHandler;
import com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.provider.DefaultAccesoTokenAuthenticationProvider;
import com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.util.ConfigConstant;

/**
 * @author Franco Antonio Chamas.
 * Filtro que intercepta toda las peticiones y valida el tocken.
 */
public final class AccesoStatelessAuthenticationFilter extends GenericFilterBean {
	// TODO ver porque si le ponemos Autowired da conflicto porque toma dos
	// beans
	private AuthenticationProvider authenticationProvider;
	@Autowired(required = false)
	private AccessDeniedHandler accessDeniedHandler;
	@Autowired(required = false)
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	
	private static final String AUTH_HEADER_NAME = ConfigConstant.AUTH_HEADER_NAME;

	private static Logger logger = LoggerFactory.getLogger(AccesoStatelessAuthenticationFilter.class);

	// TODO revisar que siempre tome AnonymousAuthenticationProvider por default
	// cuando no se asigna bean desde el contexto
	public void afterPropertiesSet() throws ServletException {
		logger.debug("invocando afterPropertiesSet");
		initFilterBean();
		validateInstance();
	}

	/**
	 * Método que intercepta todas las peticiones y valida el tocken. en caso
	 * que el tocken sea correcto, se pasa a los otros filtros, interceptores y
	 * luego de evaluar los roles se limpia el contecto de spring security.
	 */

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.debug("Inicio de filtro de seguridad");

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		Authentication authentication = null;
		try {
			validateInstance();
			authentication = authenticationProvider.authenticate(
					getAccesoUserAuthentication(httpRequest));
			authenticationSuccessHandler.onAuthenticationSuccess(
					httpRequest, (HttpServletResponse) response, chain, authentication);
		} catch (AuthenticationException ex) {
			logger.error("invocando afterPropertiesSet");
			accessDeniedHandler.handle((HttpServletRequest) request, (HttpServletResponse) response,
					new AccessDeniedException("Unauthorized"));
		}

	}

	/**
	 * Método que obtiene un Authentication con el tocken en la propiedad
	 * Authorization.
	 * 
	 * @param httpRequest
	 *            objeto request que contiene el tocken.
	 * @return objeto Authentication.
	 */
	private Authentication getAccesoUserAuthentication(HttpServletRequest httpRequest) {
		logger.debug("obteniendo AccesoUserAuthentication");
		AccesoUserAuthentication AccesoUserAuthentication = new AccesoUserAuthentication(null);

		final String token = httpRequest.getHeader(AUTH_HEADER_NAME);

		logger.debug("tocken obtenido: " + token);
		AccesoUserAuthentication.setAuthorization(token);

		return AccesoUserAuthentication;
	}

	/**
	 * Obteniendo instancia por defecto de
	 * DefaultAccesoTokenAuthenticationProvider
	 * 
	 * @return DefaultAccesoTokenAuthenticationProvider
	 */
	private DefaultAccesoTokenAuthenticationProvider getDefaultAccesoTokenAuthenticationProvider() {
		return new DefaultAccesoTokenAuthenticationProvider();
	}

	/**
	 * Obteniendo instancia por defecto de
	 * DefaultAuthenticationAccessDeniedHandler
	 * 
	 * @return DefaultAuthenticationAccessDeniedHandler
	 */
	private DefaultAuthenticationAccessDeniedHandler getDefaultAuthenticationAccessDeniedHandler() {
		return new DefaultAuthenticationAccessDeniedHandler();
	}
	
	/**
	 * Obteniendo instancia por defecto de
	 * DefaultAuthenticationAccessDeniedHandler
	 * 
	 * @return DefaultAuthenticationAccessDeniedHandler
	 */
	private DefaultAuthenticationAccessSuccessHandler getDefaultAuthenticationAccessSuccessHandler() {
		return new DefaultAuthenticationAccessSuccessHandler();
	}
	
	/**
	 * Validando Instancias.
	 */
	private void validateInstance() {
		authenticationProvider = (authenticationProvider == null) ? getDefaultAccesoTokenAuthenticationProvider()
				: authenticationProvider;
		accessDeniedHandler = (accessDeniedHandler == null) ? getDefaultAuthenticationAccessDeniedHandler()
				: accessDeniedHandler;
		authenticationSuccessHandler = (authenticationSuccessHandler == null) ? getDefaultAuthenticationAccessSuccessHandler() 
				: authenticationSuccessHandler;
	}
}
