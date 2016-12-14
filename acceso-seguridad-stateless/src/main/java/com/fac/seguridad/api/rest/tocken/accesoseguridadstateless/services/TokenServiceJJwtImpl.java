package com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.services;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;

import com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.dominio.AccesoUserDetails;
import com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.exception.BadCredentialsTockenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

/**
 * Clase de utilidades de JWT.
 * 
 * @author Franco Antonio Chamás.
 *
 */
public final class TokenServiceJJwtImpl implements TokenService {
	private String secret;
	private int minutosExpiracionTocken;

	private static Logger logger = LoggerFactory.getLogger(TokenServiceJJwtImpl.class);

	/**
	 * Constructor de la clase de utilidad de JWT.
	 * 
	 * @param secret
	 *            clave con la que se cifra el hash del tocken.
	 * @param minutosExpiracionTocken
	 *            minutos para asignar el tiempo de vida del tocken.
	 */
	public TokenServiceJJwtImpl(String secret, Integer minutosExpiracionTocken) {
		logger.debug("Creando instancia de implementación de Service");
		this.secret = secret;
		this.minutosExpiracionTocken = minutosExpiracionTocken;
	}

	public UserDetails parseUserFromToken(String token) throws BadCredentialsTockenException {
		logger.debug("Parseando Usuerio desde el tocken");
		AccesoUserDetails accesoUserDetails = new AccesoUserDetails();

		try {
			Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

			accesoUserDetails.setId(Integer.parseInt(body.get("userId").toString()));
			accesoUserDetails.setUsername(body.getSubject());
			accesoUserDetails.setPassword(body.get("userPassword").toString());
			accesoUserDetails.setAuthoritiesJson(body.get("userRole").toString());
		} catch (ExpiredJwtException ex) {
			logger.error("El tocken expiró");
			throw new BadCredentialsTockenException(BadCredentialsTockenException.MSJ_ERROR_EXPIRO_TOCKEN, ex);
		} catch (ArrayIndexOutOfBoundsException ex) {
			logger.error("Error en el tocken, el tocken fue modificado, no coincide el hash con el contenido");
			throw new BadCredentialsTockenException(BadCredentialsTockenException.MSJ_ERROR);
		} catch (SignatureException ex) {
			logger.error("Error en el tocken, el tocken fue modificado, no coincide el hash con el contenido");
			throw new BadCredentialsTockenException(BadCredentialsTockenException.MSJ_ERROR);
		}

		return accesoUserDetails;
	}

	public String createTokenForUser(UserDetails user) {
		logger.debug("Creando tocken");
		AccesoUserDetails accesoUserDetails = (AccesoUserDetails) user;
		Claims claims = Jwts.claims().setExpiration(getDateExpired()).setSubject(accesoUserDetails.getUsername());

		claims.put("userId", accesoUserDetails.getId());
		claims.put("userPassword", "****");
		claims.put("userRole", accesoUserDetails.getAuthoritiesJson());
		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	/**
	 * M�tod que obtiene la fecha de expiraci�n del tocken, para lo cual se
	 * considera la fecha actual y se le suma una cantidad de mintos pasada por
	 * parametros.
	 * 
	 * @return retorna un objeto Date que representa la fecha de expiraci�n del
	 *         tocken.
	 */
	private Date getDateExpired() {
		logger.debug("Obteniendo fecha de expiración");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, minutosExpiracionTocken);
		return calendar.getTime();
	}
}
