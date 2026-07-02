package com.supermercado_spring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Venta {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta") //unique = false y nullable = false son redundante
    private Long idVenta;

    @Column(name = "fecha_venta", nullable = false, updatable = false)
    @PastOrPresent(message = "La fecha solo puede ser actual o del pasado (No se aceptan fechas futuras)")
    private LocalDateTime fechaVenta;


    private String estadoVenta; //Esto deberia ser un enum

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Sucursal sucursal;
}
