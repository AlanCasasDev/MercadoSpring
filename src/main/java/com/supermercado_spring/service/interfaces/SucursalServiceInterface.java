package com.supermercado_spring.service.interfaces;

import com.supermercado_spring.dto.SucursalDTO;
import java.util.List;


public interface SucursalServiceInterface {

    List<SucursalDTO> traerSucursales();
    SucursalDTO buscarSucursal(Long id);
    Boolean crearSucursal(SucursalDTO sucursalDTO);
    Boolean actualizarSucursal(Long id, SucursalDTO sucursalDTO);
    Boolean eliminarSucursal(Long id);

    //Prueba
    SucursalDTO crearSucursalDTO(SucursalDTO sucursalDTO);
    SucursalDTO actualizarSucursalDTO(Long id, SucursalDTO sucursalDTO);

}
