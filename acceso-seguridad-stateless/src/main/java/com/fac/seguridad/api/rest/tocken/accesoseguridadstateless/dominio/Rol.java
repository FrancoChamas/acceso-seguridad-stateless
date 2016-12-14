package com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.dominio;

import java.io.Serializable;

import org.springframework.security.core.GrantedAuthority;

public class Rol implements GrantedAuthority, Serializable {

	private static final long serialVersionUID = 1L;
	
	private String codigo;
	private String nombre;
	private String descripcion;
	private String authority;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

}
