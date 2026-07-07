package com.supermercado_spring.exception;

import com.supermercado_spring.model.enums.EnumEstadoVenta;

public class VentaEstadoInvalidoException extends RuntimeException {
    public VentaEstadoInvalidoException(EnumEstadoVenta estadoAnterior, EnumEstadoVenta estadoNuevo) {
        super("Transicion de estado de venta invalida. Estado actual: " + estadoAnterior +
                ". Estado solicitado: " + estadoNuevo);
    }
}
