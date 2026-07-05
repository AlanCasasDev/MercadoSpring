package com.supermercado_spring.model.enums;

public enum EnumEstadoVenta {

    PENDIENTE("Pendiente","La venta fue creada pero todavía no se confirmó."),
    CONFIRMADA("Confirmada", "La venta fue aceptada por el sistema."),
    PAGADA("Pagada","El pago fue aprobado."),
    PREPARANDO("Preparando","Se están preparando los productos."),
    LISTA_PARA_ENTREGA("Listo para la entrega","El pedido está listo para retirar o enviar."),
    ENTREGADA("Entregada", "El cliente ya recibió el pedido."),
    COMPLETADA("Completada","La venta terminó correctamente."),
    CANCELADA("Cancelada","Se canceló antes de finalizar."),
    RECHAZADA("Rechazada", "El pago o la operación fue rechazada."),
    REEMBOLSADA("Reembolsada", "Se devolvió el dinero al cliente.");

    private String descripcion;
    private String nombre;

    EnumEstadoVenta(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getNombre() {
        return nombre;
    }
}
