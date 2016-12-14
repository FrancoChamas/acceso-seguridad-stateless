package com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.handler;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * @author Franco Antonio Chamás.
 *  clase que representa la acción de una autenticación correcta.
 *
 */
public class DefaultAuthenticationAccessSuccessHandler implements AuthenticationSuccessHandler {
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
		SecurityContextHolder.clearContext();
	}
}
