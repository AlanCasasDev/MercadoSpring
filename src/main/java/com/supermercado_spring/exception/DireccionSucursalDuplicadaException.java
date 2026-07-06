package com.supermercado_spring.exception;

public class DireccionSucursalDuplicadaException extends RuntimeException {
    public DireccionSucursalDuplicadaException(String direccionSucursal) {
        super("Error! Ya existe en la base de datos una sucursal en la direccion:" + direccionSucursal );
    }
}
