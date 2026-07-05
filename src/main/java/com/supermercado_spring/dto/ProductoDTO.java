package com.supermercado_spring.dto;

import com.supermercado_spring.model.enums.EnumCategoria;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductoDTO {

    @Positive(message = "El id no puede ser negativo ni 0")
    private Long idProducto;

    @NotBlank(message = "El nombre del producto no puede estar vacio ni en blanco") @Size(min = 3, max = 50)
    private String nombreProducto;

    @NotNull(message = "La categoria no puede ser null") //Se usa en los enums y no @NotBlank
    private EnumCategoria enumCategoriaProducto;

    @Positive(message = "El precio solo puede ser de valor positivo mayor a 0")
    private BigDecimal precioProducto;

    @PositiveOrZero(message = "La cantidad de productos solo puede ser 0 o mayor a 0")
    private Long cantidadProducto;
}
