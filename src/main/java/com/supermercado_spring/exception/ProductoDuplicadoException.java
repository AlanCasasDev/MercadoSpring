package com.supermercado_spring.exception;


public class ProductoDuplicadoException extends RuntimeException {
    public ProductoDuplicadoException(String nombreProducto) {
        super("Ya existe en la base de datos un producto con el nombre:" + nombreProducto );
    }
}
