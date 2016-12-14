package com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.util.ConfigConstant;

/**
 * Clase de utilidad para manejar el acceso Denegado
 * @author usuario
 *
 */
public class DefaultAuthenticationAccessDeniedHandler  implements AccessDeniedHandler {
	private static final String AUTH_HEADER_NAME = ConfigConstant.AUTH_HEADER_NAME;
	
	/**
	 * Metodo que retorna un objeto Response con un codigo 401.
	 *
	 */
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException)
			throws IOException, ServletException {
		SecurityContextHolder.clearContext();
		HttpServletResponse resp = ((HttpServletResponse) response);
		resp.setHeader(AUTH_HEADER_NAME, "");
		resp.setStatus(HttpStatus.UNAUTHORIZED.value());
	}
}
