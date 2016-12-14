package com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.dominio;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * Clase que representa la implementacion de un UserDetails
 * @author Franco Antonio chamas
 *
 */
public class AccesoUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;
    private int id;
	private String            Username;
    private String password;
    private Set<Rol> authorities;
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

    public void setPassword(String password) {
		this.password = password;
	}
    
    public String getPassword() {
        return password;
    }

	public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
    	return new LinkedHashSet<GrantedAuthority>(authorities);
    }
    
    public String getAuthoritiesJson() {
    	String retVal = null;
    	
    	ObjectMapper mapper = new ObjectMapper();
    	
    	try {
			retVal =  mapper.writeValueAsString(getAuthorities());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	
    	return retVal;
    }
    
    
    public void setAuthorities(Collection<? extends GrantedAuthority> authorities)
    {
      this.authorities = new LinkedHashSet<Rol>();
      
      for (GrantedAuthority authority : authorities) {
        this.authorities.add((Rol)authority);
      }
    }
    
	public void setAuthoritiesJson(String authoritiesJson) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			this.authorities = mapper.readValue(authoritiesJson,
					TypeFactory.defaultInstance().constructCollectionType(LinkedHashSet.class,  
							Rol.class));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return false;
    }
}
