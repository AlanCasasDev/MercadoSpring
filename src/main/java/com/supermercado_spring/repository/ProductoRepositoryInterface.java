package com.supermercado_spring.repository;

import com.supermercado_spring.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProductoRepositoryInterface extends JpaRepository<Producto, Long> {

    boolean existsByNombreProducto(String nombreProducto);

}
