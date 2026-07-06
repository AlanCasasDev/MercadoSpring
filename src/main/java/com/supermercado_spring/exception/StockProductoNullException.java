package com.supermercado_spring.exception;

public class StockProductoNullException extends RuntimeException {
    public StockProductoNullException(Long id) {
        super("Error! El stock del producto con id: "+ id + " es null, no fue inicializadoo no existe.");
    }
}
