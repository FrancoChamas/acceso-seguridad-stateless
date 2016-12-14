package com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.dominio;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Clase que representa la implementacion de Authentication.
 * @author Franco Antonio Chamás.
 *
 */
public class AccesoUserAuthentication implements Authentication {

    private static final long serialVersionUID = 1L;
    private final UserDetails        user;
    private boolean           authenticated    = true;
    private String 	authorization;

    
    public AccesoUserAuthentication(UserDetails user) {
        this.user = user;
    }
    
    public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

    public String getName() {
        return user.getUsername();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    public Object getCredentials() {
        return user.getPassword();
    }

    public UserDetails getDetails() {
        return user;
    }

    public Object getPrincipal() {
        return user.getUsername();
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}
