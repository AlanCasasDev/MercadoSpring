package com.supermercado_spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DetalleVentaDTO {

    //@Positive(message = "El id no puede ser negativo ni 0")
    private Long idDetalleVenta;

    //Producto
    private Long idProducto;

    @NotBlank(message = "El nombre del producto no puede estar en blanco ni nulo.")
    private String nombreProducto;

    @PositiveOrZero(message = "La cantidad del producto es estrictamente mayor o igual a 0")
    private Integer cantidad;

    @Positive(message = "El valor del producto debe ser positivo y mayor a 0")
    private BigDecimal precioUnitario;

    //Venta
    @Positive(message = "El subtotal de la venta no puede ser negativo ni 0")
    private BigDecimal subtotal;


}
