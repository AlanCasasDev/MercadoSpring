package com.supermercado_spring.model;

import com.supermercado_spring.model.enums.EnumCategoria;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class Producto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_producto")
    private Long idProducto;

    //@NotBlank(message = "El nombre del producto no puede estar vacio ni en blanco") @Size(min = 3, max = 50)
    @Column(name ="nombre_producto", nullable = false, unique = true, length = 50)
    private String nombreProducto;

    //@NotNull(message = "La categoria no puede ser null") //Se usa en los enums y no @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(name ="categoria_producto", nullable = false, length = 25)
    private EnumCategoria enumCategoriaProducto;

    //@Positive(message = "El precio solo puede ser de valor positivo mayor a 0")
    @Column(name ="precio_producto", nullable = false, precision = 20, scale = 2) //Hasta 20 numeros teniendo en cuenta los decimales que son 2
    private BigDecimal precioProducto;

    //@PositiveOrZero(message = "La cantidad de productos solo puede ser 0 o mayor a 0")
    @Column(name = "cant_producto", nullable = false)
    private Long cantidadProducto; //Stock

}
