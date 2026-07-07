package com.supermercado_spring.exception;

public class VentaDuplicadaException extends RuntimeException{
    public  VentaDuplicadaException(Long id){
        super("Error!. Venta duplicada. El Id: " + id + " ya existe en la base de datos");
    }
}
