package com.supermercado_spring.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DetalleVentaResponseDTO {


    //Producto
    @NotNull(message = "El id del producto es obligatorio.")
    private Long idProducto;

    @Positive(message = "La cantidad del producto debe ser mayor a 0")
    private Integer cantidad;


    //@Positive(message = "El id no puede ser negativo ni 0")
    private Long idDetalleVenta;


    private String nombreProducto;

    @Positive(message = "El valor del producto debe ser positivo y mayor a 0")
    private BigDecimal precioUnitario;

    //Venta
    @Positive(message = "El subtotal de la venta no puede ser negativo ni 0")
    private BigDecimal subtotal;


}