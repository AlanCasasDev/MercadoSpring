package com.supermercado_spring.exception;

public class NombreSucursalDuplicadaException extends RuntimeException{
    public NombreSucursalDuplicadaException(String nombreSucursal) {
        super("Error! Ya existe en la base de datos una sucursal con el nombre:" + nombreSucursal );
    }
}
