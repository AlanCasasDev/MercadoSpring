package com.supermercado_spring.service;

import com.supermercado_spring.dto.SucursalDTO;
import com.supermercado_spring.service.interfaces.SucursalServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SucursalService implements SucursalServiceInterface {

    @Override
    public List<SucursalDTO> traerSucursales() {
        return List.of();
    }

    @Override
    public SucursalDTO buscarSucursal(Long id) {
        return null;
    }

    @Override
    public Boolean crearSucursal(SucursalDTO sucursalDTO) {
        return null;
    }

    @Override
    public Boolean actualizarSucursal(Long id, SucursalDTO sucursalDTO) {
        return null;
    }

    @Override
    public Boolean eliminarSucursal(Long id) {
        return null;
    }

    @Override
    public SucursalDTO crearSucursalDTO(SucursalDTO sucursalDTO) {
        return null;
    }

    @Override
    public SucursalDTO actualizarSucursalDTO(Long id, SucursalDTO sucursalDTO) {
        return null;
    }
}
