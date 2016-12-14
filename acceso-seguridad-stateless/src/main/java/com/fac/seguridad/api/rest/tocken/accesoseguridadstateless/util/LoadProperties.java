/*
 * Desarrollado por Ayi Asociados
 */
package com.fac.seguridad.api.rest.tocken.accesoseguridadstateless.util;

import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase utilitario para acceder a los archivos .properties.
 * @author Franco Antonio chamas
 *
 */
public final class LoadProperties {

    /** El logger. */
    private static Logger logger = LoggerFactory.getLogger(LoadProperties.class);

    /**
     * Método que obtiene al valor de un .properites en funcion de su key
     * @param pathFile path donde esta el fila ej: \\nolca
     * @param nameFile nombre del archivo ej: servicios
     * @param key clave a buscar ej: url.validar.ca
     * @return string resultado de la busqueda
     */
    public static String getPropertie(String nameFile, String key) {
        logger.debug("obteniendo valor de properties, nombre del archivo: " + nameFile
                        + " clave de busqueda: " + key);
        String retVal = null;

        try {
            ResourceBundle rb = ResourceBundle.getBundle(nameFile);
            retVal = rb.getString(key);

        } catch (Exception ex) {
            logger.error("Error al recuperar la clave del .properties", ex);
        }

        return retVal;
    }

    /**
     * Constructor privado para ocultar el por defecto.
     */
    private LoadProperties() {

    }
}
