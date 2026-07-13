package com.supermercado_spring.dto;

import com.supermercado_spring.model.enums.EnumEstadoVenta;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class VentaDTO {


    //Datos de Venta
    //@Positive(message = "El id debe ser estrictamente positivo")
    private Long idVenta;

    @NotNull(message = "La venta tiene que tener un estado asociado")
    private EnumEstadoVenta estadoVenta; //TODO capaz aca deberia ser String


    //Datos de DetalleVenta
    @NotNull(message = "La venta tiene que tener detalles asociados.")
    @NotEmpty(message = "La lista de detalles no puede estar vacia.")
    //@Size(min = 1)
    private List<@Valid DetalleVentaDTO> detalles;

    //Datos de Sucursal
    @Positive(message = "El id de la sucursal debe ser estrictamente positivo.")
    private Long idSucursal;
}
