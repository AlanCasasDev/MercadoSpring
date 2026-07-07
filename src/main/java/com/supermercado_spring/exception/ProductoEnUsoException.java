package com.supermercado_spring.exception;

public class ProductoEnUsoException extends RuntimeException {
    public ProductoEnUsoException(Long idProducto) {
        super("No se puede eliminar el producto. Tiene ventas asociadas. Id: " + idProducto);
    }
}
