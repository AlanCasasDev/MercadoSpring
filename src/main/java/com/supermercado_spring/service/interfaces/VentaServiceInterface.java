package com.supermercado_spring.service.interfaces;

import com.supermercado_spring.dto.ProductoDTO;
import com.supermercado_spring.dto.VentaDTO;

import java.util.List;

public interface VentaServiceInterface {

    List<VentaDTO> traerVentas();
    VentaDTO buscarVenta(Long id);
    void crearVenta(VentaDTO ventaDTO);
    void actualizarVenta(Long id, VentaDTO ventaDTO);
    void eliminarVenta(Long id);


}
