package com.supermercado_spring.exception;

public class SucursalNoEncontradaException extends RuntimeException {
    public SucursalNoEncontradaException(Long id) {
        super("No existe una sucursal con el id " + id);
    }
}
