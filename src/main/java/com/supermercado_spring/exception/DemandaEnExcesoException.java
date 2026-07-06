package com.supermercado_spring.exception;

public class DemandaEnExcesoException extends RuntimeException {
    public DemandaEnExcesoException(String nombreProducto, Long cantidadDemandada, Long cantidadDisponible) {
        super("Error. La cantidad solicitada ("+cantidadDemandada+") " +
                "es superior a la cantidad disponible ("+cantidadDisponible+") " +
                "del producto:"+nombreProducto);
    }
}
