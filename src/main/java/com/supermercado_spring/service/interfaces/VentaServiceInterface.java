package com.supermercado_spring.service.interfaces;

import com.supermercado_spring.dto.ProductoDTO;
import com.supermercado_spring.dto.VentaDTO;

import java.util.List;

public interface VentaServiceInterface {

    List<VentaDTO> traerVentas();
    ProductoDTO buscarVenta(Long id);
    Boolean crearVenta(VentaDTO ventaDTO);
    Boolean actualizarVenta(Long id, VentaDTO ventaDTO);
    Boolean eliminarVenta(Long id);

    //Prueba
    VentaDTO crearVentaDTO(VentaDTO ventaDTO);
    VentaDTO actualizarVentaDTO(Long id, VentaDTO ventaDTO);
}
