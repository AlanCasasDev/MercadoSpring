package com.supermercado_spring.repository;

import com.supermercado_spring.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SucursalRepositoryInterface extends JpaRepository<Sucursal, Long> {

    boolean existsByNombreSucursal(String nombreSucursal);
    boolean existsByDireccionSucursal(String direccionSucursal);
}
