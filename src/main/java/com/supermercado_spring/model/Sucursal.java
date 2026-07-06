package com.supermercado_spring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Sucursal {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_sucursal")
    private Long idSucursal;

    //@NotBlank(message = "El nombre de la sucursal no puede estar vacio ni en blanco")
    @Column(name ="nombre_sucursal", nullable = false, unique = true, length = 25)
    private String nombreSucursal;

    //@NotBlank(message = "La direccion de la sucursal no puede estar vacio ni en blanco")
    @Column(name ="direccion_sucursal", unique = true, nullable = false, length = 50)
    private String direccionSucursal;

}
