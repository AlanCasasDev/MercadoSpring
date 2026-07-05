package com.supermercado_spring.exception;

import com.supermercado_spring.dto.ProductoDTO;

public class ProductoDuplicadoException extends RuntimeException {
    public ProductoDuplicadoException(String nombreProducto) {
        super("Ya existe en la base de datos un producto con el nombre:" + nombreProducto );
    }
}
