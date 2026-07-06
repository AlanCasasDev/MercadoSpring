package com.supermercado_spring.exception;

public class StockProductoInsuficienteException extends RuntimeException {

    public StockProductoInsuficienteException(String nombreProducto, Long cantidadDemandada, Long cantidadDisponible) {
        super("Stock insuficiente para el producto " + nombreProducto +
                ". Cantidad solicitada: " + cantidadDemandada +
                ". Cantidad disponible: " + cantidadDisponible);
    }
}
