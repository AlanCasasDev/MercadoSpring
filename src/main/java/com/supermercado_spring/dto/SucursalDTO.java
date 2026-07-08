package com.supermercado_spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SucursalDTO {

    //@Positive(message = "El id no puede ser negativo ni 0")
    private Long idSucursal;

    @NotBlank(message = "El nombre de la sucursal no puede estar vacio ni en blanco")
    //@Size(min = 2, max = 25)
    private String nombreSucursal;

    @NotBlank(message = "La direccion de la sucursal no puede estar vacio ni en blanco")
    //@Size(min = 5, max = 50)
    private String direccionSucursal;
}
