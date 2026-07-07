package com.supermercado_spring.exception;

public class VentaNoEncontradaException extends RuntimeException {
    public VentaNoEncontradaException(Long id) {
        super("Venta no encontrada. Id: " + id);
    }
}
