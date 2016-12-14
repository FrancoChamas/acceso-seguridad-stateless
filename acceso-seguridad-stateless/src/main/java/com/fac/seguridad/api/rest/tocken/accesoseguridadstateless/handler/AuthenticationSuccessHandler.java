package com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.handler;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;

/**
 * @author Franco Antonio Chamás
 * Interfaz que representa la acción de una autenticación correcta.
 */
public interface AuthenticationSuccessHandler {
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain, Authentication authentication) throws IOException, ServletException;
}
