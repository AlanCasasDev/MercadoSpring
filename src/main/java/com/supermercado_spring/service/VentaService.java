package com.supermercado_spring.service;

import com.supermercado_spring.dto.ProductoDTO;
import com.supermercado_spring.dto.VentaDTO;
import com.supermercado_spring.service.interfaces.VentaServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaService implements VentaServiceInterface {


    @Override
    public List<VentaDTO> traerVentas() {
        return List.of();
    }

    @Override
    public ProductoDTO buscarVenta(Long id) {
        return null;
    }

    @Override
    public Boolean crearVenta(VentaDTO ventaDTO) {
        return null;
    }

    @Override
    public Boolean actualizarVenta(Long id, VentaDTO ventaDTO) {
        return null;
    }

    @Override
    public Boolean eliminarVenta(Long id) {
        return null;
    }

    @Override
    public VentaDTO crearVentaDTO(VentaDTO ventaDTO) {
        return null;
    }

    @Override
    public VentaDTO actualizarVentaDTO(Long id, VentaDTO ventaDTO) {
        return null;
    }
}
