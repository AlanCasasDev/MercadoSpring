package com.supermercado_spring.exception;

public class StockProductoInsuficienteException extends RuntimeException {

    public StockProductoInsuficienteException(String nombreProducto, Long stockDisponible) {
        super("Stock insuficiente para el producto " + nombreProducto + ". Stock disponible: " + stockDisponible);
    }
}
