package com.supermercado_spring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalleventa")
    private Long idDetalleVenta;

    //Producto
    //@NotNull(message = "El detalle debe tener una producto asociado. El producto no puede ser nulo.")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Producto producto;

    //@PositiveOrZero(message = "La cantidad del producto es estrictamente mayor o igual a 0")
    @Column(name = "dv_cant_product", nullable = false, precision = 9, scale = 0)
    private Integer cantidad;

    //@Positive(message = "El valor del producto debe ser positivo y mayor a 0")
    @Column(name = "dv_precio_product", nullable = false, precision = 11, scale = 2)
    private BigDecimal precioUnitario;

    //Venta
    //@NotNull(message = "El detalle debe tener una venta asociada. La venta no puede ser nula.")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Venta venta;

    //@Positive(message = "El subtotal no puede ser negativo ni 0")
    @Column(name = "subtotal",  nullable = false, precision = 19, scale = 2)
    private BigDecimal subtotal;

}
