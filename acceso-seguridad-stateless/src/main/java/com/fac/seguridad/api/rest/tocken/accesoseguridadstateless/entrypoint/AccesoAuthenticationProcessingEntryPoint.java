package com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.entrypoint;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.util.ConfigConstant;

/**
 * Clase de utilidad que maneja las peticiones de usuario no autenticados,
 * se sobreescribe para que no redireccione y retorne un código de estado 401.
 * @author Franco Antonio Chamáss.
 *
 */
public class AccesoAuthenticationProcessingEntryPoint implements AuthenticationEntryPoint {
	private static final String AUTH_HEADER_NAME = ConfigConstant.AUTH_HEADER_NAME;
	public void commence(HttpServletRequest arg0, HttpServletResponse arg1,
	            AuthenticationException arg2) throws IOException, ServletException {
		HttpServletResponse resp = ((HttpServletResponse) arg1);
		resp.setHeader(AUTH_HEADER_NAME, "");
		resp.setStatus(HttpStatus.UNAUTHORIZED.value());
	}
}
