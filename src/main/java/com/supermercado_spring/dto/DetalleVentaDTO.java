package com.supermercado_spring.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DetalleVentaDTO {


    //Producto
    @NotNull(message = "El id del producto es obligatorio.")
    private Long idProducto;

    @Positive(message = "La cantidad del producto debe ser mayor a 0")
    private Integer cantidad;


}
