package com.supermercado_spring.repository;

import com.supermercado_spring.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepositoryInterface extends JpaRepository<Venta,Long> {

    @Query("select count(v) > 0 from Venta v where v.sucursal.idSucursal = :idSucursal")
    boolean existsBySucursalId(@Param("idSucursal") Long idSucursal);

    @Query("select count(v) > 0 from Venta v join v.detalle d where d.producto.idProducto = :idProducto")
    boolean existsByProductoId(@Param("idProducto") Long idProducto);

}
