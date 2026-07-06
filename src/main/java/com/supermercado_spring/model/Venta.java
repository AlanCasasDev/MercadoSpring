package com.supermercado_spring.model;

import com.supermercado_spring.model.enums.EnumEstadoVenta;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class Venta {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta") //unique = true y nullable = false son redundante
    private Long idVenta;

    //@PastOrPresent(message = "La fecha solo puede ser actual o del pasado (No se aceptan fechas futuras)")
    @Column(name = "fecha_venta", nullable = false, updatable = false)
    private LocalDateTime fechaVenta;

    //@PositiveOrZero(message = "El total del a venta no puede ser negativo")
    @Column(name = "total_venta", nullable = false, precision = 19, scale = 2)
    private BigDecimal total;

    //@NotNull(message = "La venta tiene que tener un estado asociado")
    //@Enumerated(EnumType.STRING)
    @Column(name ="estado_venta", nullable = false, length =25)
    private EnumEstadoVenta estadoVenta;

    //@NotNull(message = "La venta tiene que tener una sucursal asociada. No puede ser nula.")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Sucursal sucursal;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVenta> detalle = new ArrayList<>();
}
