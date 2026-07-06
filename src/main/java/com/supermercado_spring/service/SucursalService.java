package com.supermercado_spring.service;

import com.supermercado_spring.dto.ProductoDTO;
import com.supermercado_spring.dto.SucursalDTO;
import com.supermercado_spring.exception.*;
import com.supermercado_spring.mapper.Mapper;
import com.supermercado_spring.model.Producto;
import com.supermercado_spring.model.Sucursal;
import com.supermercado_spring.repository.SucursalRepositoryInterface;
import com.supermercado_spring.service.interfaces.SucursalServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SucursalService implements SucursalServiceInterface {

    //Inyeccion por Autowired
    @Autowired
    private SucursalRepositoryInterface sucursalRepository;

    //Inyeccion por constructor (dicen que es mas recomendable)
    /*private final ProductoRepositoryInterface productoRepository;

    public ProductoService(ProductoRepositoryInterface productoRepository) {
        this.productoRepository = productoRepository;
    }*/



    @Override
    public List<SucursalDTO> traerSucursales() {

        return sucursalRepository.findAll(Sort.by(Sort.Direction.ASC,"idSucursal")) //idProducto es el nombre del atributo de Producto
                .stream()
                .map(Mapper::toSucursalDTO)
                .toList();

    }

    @Override
    public SucursalDTO buscarSucursal(Long id) {
        return Mapper.toSucursalDTO(obtenerSucursal(id));
    }

    //TODO DEJE POR ACA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

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


    //---------------------------------------------------------------------
    private Sucursal obtenerSucursal(Long id) {

        return sucursalRepository.findById(id)
                .orElseThrow(() -> new SucursalNoEncontradaException(id));
    }

    private void validarDireccionSucursal(String direccionSucursal) {

        if (sucursalRepository.existsByDireccionSucursal(direccionSucursal)) {
            throw new DireccionSucursalDuplicadaException(direccionSucursal);
        }
    }

    private void validarNombreSucursal(String nombreSucursal) {

        if (sucursalRepository.existsByDireccionSucursal(nombreSucursal)) {
            throw new NombreSucursalDuplicadaException(nombreSucursal);
        }
    }

    private Sucursal crearEntidad(SucursalDTO dto) {

        return Sucursal.builder()
                .nombreSucursal(dto.getNombreSucursal())
                .direccionSucursal(dto.getDireccionSucursal())
                .build();
    }
}
