package com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.services;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.dominio.AccesoUserDetails;
import com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.exception.BadCreateTockenException;
import com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.exception.BadTockenException;
import com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.util.LoadProperties;

/**
 * Clase que genera un tocken autenticando, autorizando y haciendo una auditoria del usuario que se quiere logear
 * @author Franco Antonio Chamás
 */
public class AccesoCreateTokenServiceImp implements AccesoCreateTokenService, InitializingBean {
	
	private final String USER_NAME = "usuario";
	private final String USER_PASSWORD = "contrasenia";
	private int idAplicacion;
	
	@Autowired(required=false)
	private AccesoAutenticacionService accesoAutenticacion;
	@Autowired(required=false)
	private AccesoAutorizadorService  accesoAutorizador;
	@Autowired(required=false)
	private AccesoAuditoriaService accesoAuditoria;
	@Autowired(required=false)
	TokenService tokenHandlerJJwtImpl;
	
	private static Logger logger = LoggerFactory.getLogger(AccesoCreateTokenServiceImp.class);
	
	
	public void afterPropertiesSet() throws ServletException {
		logger.debug("invocación afterPropertiesSet");
		validateInstance();
	}
	
	public String createTokenForUser(final ServletRequest request) throws BadTockenException {
		logger.debug("Creando Tocken");
		validateInstance();
		UserDetails accesoUserDetails = getUserDetailsImp(request);
	    validarCredenciales(accesoUserDetails);
		
		accesoUserDetails = (AccesoUserDetails) autenticar(accesoUserDetails);
		accesoUserDetails = (AccesoUserDetails) autorizar(accesoUserDetails, idAplicacion);
		auditoria(accesoUserDetails, idAplicacion);
		
		return tokenHandlerJJwtImpl.createTokenForUser(accesoUserDetails);
	 }
	
	/**
	 * Obtiene el UserDetails
	 * @param request objeto que representa el request
	 * @return UserDetails impliementación de AccesoUserDetails
	 */
	private UserDetails getUserDetailsImp(final ServletRequest request) {
		logger.debug("Obteniendo la implementacion de UserDetailsImp ");
		HttpServletRequest httpRequest = (HttpServletRequest) request;
        //TODO ver de tomar el usuario y la contraseña de un form
        final String usuario = httpRequest.getParameter(USER_NAME);
        final String contrasenia = httpRequest.getParameter(USER_PASSWORD);
        
		AccesoUserDetails accesoUserDetails = new AccesoUserDetails();
		accesoUserDetails.setUsername(usuario);
		accesoUserDetails.setPassword(contrasenia);
		return accesoUserDetails;
	}
	
	/**
	 * Método que invoca a la implementacion del autenticador
	 * @param accesoUserDetails objeto que contiene las credenciales.
	 * @return UserDetails resultante de la autenticaci�n.
	 * @throws BadCreateTockenException exception lanzada en caso que el usuario no se autentique correctamente.
	 */
	private UserDetails autenticar(UserDetails accesoUserDetails) throws BadCreateTockenException {
		logger.debug("invocando autenticador");
		return accesoAutenticacion.autenticar(accesoUserDetails);
	}
	
	/**
	 * Método que invoca a la implementacion del autorizador
	 * @param accesoUserDetails objeto que contiene las credenciales.
	 * @param idAplicacion identificador del aplicativo.
	 * @return UserDetails resultante del autorizador, conteniendo la lista de roles.
	 * @throws BadCreateTockenException exception lanzada en caso que el usuario no se autorize correctamente.
	 */
	private UserDetails autorizar(UserDetails accesoUserDetails, int idAplicacion) throws BadCreateTockenException {
		logger.debug("invocando autorizar");
		return accesoAutorizador.autorizar(accesoUserDetails, idAplicacion);
	}
	
	/**
	 * Método que invoca a la implementacion de auditoria
	 * @param accesoUserDetails objeto que contiene las credenciales y roles.
	 * @param idAplicacion identificador del aplicativo.
	 * @throws BadCreateTockenException exception lanzada si existe un error al realizar la auditoria.
	 */
	private void auditoria(UserDetails accesoUserDetails, int idAplicacion) throws BadCreateTockenException {
		logger.debug("invocando auditar");
		accesoAuditoria.auditoria(accesoUserDetails, idAplicacion);
	}
	
	/**
	 * Método que valida que el usuario o la contrase�a tengan contenido
	 * @param accesoUserDetails objeto que contiene las credenciales a validar
	 */
	private void validarCredenciales(UserDetails accesoUserDetails) throws BadCreateTockenException {
		logger.debug("Validando las credenciales");
		if(accesoUserDetails.getUsername() == null || accesoUserDetails.getUsername().trim().equals("") ) {
			throw new BadCreateTockenException(BadCreateTockenException.MSJ_USUARIO_BLANCO);
		}
		
		if(accesoUserDetails.getPassword() == null || accesoUserDetails.getPassword().trim().equals("") ) {
			throw new BadCreateTockenException(BadCreateTockenException.MSJ_CONTRASENIA_BLANCO);
		}
	}

	/**
	 * Otienen el id del aplicativo
	 * @return
	 */
	public int getIdAplicacion() {
		return idAplicacion;
	}

	/**
	 * Setea el id del aplicativo
	 * @param idAplicacion
	 */
	public void setIdAplicacion(int idAplicacion) {
		logger.debug("asignando el idAplicativo:" + idAplicacion);
		this.idAplicacion = idAplicacion;
	}
	
	/**
	 * Obtiene una instancia de DefaultAccesoUserDetailsService.
	 * @return  DefaultAccesoAutenticacionServiceImpl
	 */
	private DefaultAccesoAutenticacionServiceImpl getDefaultAccesoAutenticacionServiceImpl() {
		logger.debug("Obteniendo AccesoAutenticacionServiceImpl por defecto");
		return new DefaultAccesoAutenticacionServiceImpl();
	}
	
	/**
	 * Obtiene una instancia de DefaultAccesoAutorizadorServiceImpl.
	 * @return  DefaultAccesoAutorizadorServiceImpl
	 */
	private DefaultAccesoAutorizadorServiceImpl getDefaultAccesoAutorizadorServiceImpl() {
		logger.debug("Obteniendo AccesoAutorizadorServiceImpl por defecto");
		return new DefaultAccesoAutorizadorServiceImpl();
	}
	
	/**
	 * Obtiene una instancia de DefaultAccesoAuditoriaServiceImpl.
	 * @return  DefaultAccesoAuditoriaServiceImpll
	 */
	private DefaultAccesoAuditoriaServiceImpl getDefaultAccesoAuditoriaServiceImpl() {
		logger.debug("Obteniendo AccesoAuditoriaServiceImpl por defecto");
		return new DefaultAccesoAuditoriaServiceImpl();
	}
	
	/**
	 * Obtiene una instancia de DefaultAccesoAuditoriaServiceImpl.
	 * @return  DefaultAccesoAuditoriaServiceImpll
	 */
	private TokenServiceJJwtImpl getTokenServiceJJwtImpl() {
		logger.debug("Obteniendo por defecto");
		String secret = LoadProperties.getPropertie("config", "secret");
		String minutosExpiracionTocken = LoadProperties.getPropertie("config", "minutosExpiracionTocken");
		return new TokenServiceJJwtImpl(secret,Integer.parseInt(minutosExpiracionTocken));
	}
	/**
	 * Valida que exista las instancias necesarios, si no existe asigna las implementaciones por defecto.
	 */
	private void validateInstance() {
		logger.debug("Validando instancias por default");
		accesoAutenticacion = (accesoAutenticacion == null) ? getDefaultAccesoAutenticacionServiceImpl() : accesoAutenticacion;
		accesoAutorizador = (accesoAutorizador == null) ?  getDefaultAccesoAutorizadorServiceImpl() : accesoAutorizador;
		accesoAuditoria = (accesoAuditoria == null) ? getDefaultAccesoAuditoriaServiceImpl() : accesoAuditoria;
		tokenHandlerJJwtImpl = (tokenHandlerJJwtImpl == null) ? getTokenServiceJJwtImpl() : tokenHandlerJJwtImpl;
	}

}
