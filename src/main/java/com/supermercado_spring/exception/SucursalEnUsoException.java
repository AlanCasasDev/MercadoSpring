package com.supermercado_spring.exception;

public class SucursalEnUsoException extends RuntimeException {
    public SucursalEnUsoException(Long idSucursal) {
        super("No se puede eliminar la sucursal. Tiene ventas asociadas. Id: " + idSucursal);
    }
}
