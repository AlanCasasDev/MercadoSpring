package com.supermercado_spring.service;

import com.supermercado_spring.dto.SucursalDTO;
import com.supermercado_spring.exception.*;
import com.supermercado_spring.mapper.Mapper;
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
    /*@Autowired
    private SucursalRepositoryInterface sucursalRepository;*/

    //Inyeccion por constructor (dicen que es mas recomendable)
    private final SucursalRepositoryInterface sucursalRepository;

    public SucursalService(SucursalRepositoryInterface sucursalRepository) {
        this.sucursalRepository = sucursalRepository;
    }



    @Override
    public List<SucursalDTO> traerSucursales() {

        return sucursalRepository.findAll(Sort.by(Sort.Direction.ASC,"idSucursal")) //idSucursal es el nombre del atributo de Sucursal
                .stream()
                .map(Mapper::toSucursalDTO)
                .toList();

    }

    @Override
    public SucursalDTO buscarSucursal(Long id) {
        return Mapper.toSucursalDTO(obtenerSucursal(id));
    }



    @Override
    public void crearSucursal(SucursalDTO dto) {
        verificarDireccionSucursal(dto.getDireccionSucursal());
        verificarNombreSucursal(dto.getNombreSucursal());

        sucursalRepository.save(Mapper.toSucursal(dto));
    }

    @Override
    public void actualizarSucursal(Long id, SucursalDTO dto) {
        Sucursal sucursal = obtenerSucursal(id);

        if (!sucursal.getNombreSucursal().equals(dto.getNombreSucursal())) {
            verificarNombreSucursal(dto.getNombreSucursal());
        }

        if (!sucursal.getDireccionSucursal().equals(dto.getDireccionSucursal())) {
            verificarDireccionSucursal(dto.getDireccionSucursal());
        }

        sucursal.setNombreSucursal(dto.getNombreSucursal());
        sucursal.setDireccionSucursal(dto.getDireccionSucursal());

        sucursalRepository.save(sucursal);
    }

    @Override
    public void eliminarSucursal(Long id) {
        sucursalRepository.delete(obtenerSucursal(id));
    }



    //---------------------------------------------------------------------
    private Sucursal obtenerSucursal(Long id) {

        return sucursalRepository.findById(id)
                .orElseThrow(() -> new SucursalNoEncontradaException(id));
    }

    private void verificarDireccionSucursal(String direccionSucursal) {

        if (sucursalRepository.existsByDireccionSucursal(direccionSucursal)) {
            throw new DireccionSucursalDuplicadaException(direccionSucursal);
        }
    }

    private void verificarNombreSucursal(String nombreSucursal) {

        if (sucursalRepository.existsByNombreSucursal(nombreSucursal)) {
            throw new NombreSucursalDuplicadaException(nombreSucursal);
        }
    }

}
