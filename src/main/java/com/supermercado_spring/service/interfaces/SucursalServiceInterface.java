package com.supermercado_spring.service.interfaces;

import com.supermercado_spring.dto.SucursalDTO;
import java.util.List;


public interface SucursalServiceInterface {

    List<SucursalDTO> traerSucursales();
    SucursalDTO buscarSucursal(Long id);
    void crearSucursal(SucursalDTO sucursalDTO);
    void actualizarSucursal(Long id, SucursalDTO sucursalDTO);
    void eliminarSucursal(Long id);



}
