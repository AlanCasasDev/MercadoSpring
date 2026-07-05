package com.supermercado_spring.repository;

import com.supermercado_spring.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepositoryInterface extends JpaRepository<Venta,Long> {
}
