package com.supermercado_spring.service;

import com.supermercado_spring.dto.SucursalDTO;
import com.supermercado_spring.exception.*;
import com.supermercado_spring.mapper.Mapper;
import com.supermercado_spring.model.Sucursal;
import com.supermercado_spring.repository.VentaRepositoryInterface;
import com.supermercado_spring.repository.SucursalRepositoryInterface;
import com.supermercado_spring.service.interfaces.SucursalServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
@Service
public class SucursalService implements SucursalServiceInterface {

    //Inyeccion por Autowired
    /*@Autowired
    private SucursalRepositoryInterface sucursalRepository;*/

    //Inyeccion por constructor (dicen que es mas recomendable)
    private final SucursalRepositoryInterface sucursalRepository;
    private final VentaRepositoryInterface ventaRepository;

    public SucursalService(SucursalRepositoryInterface sucursalRepository, VentaRepositoryInterface ventaRepository) {
        this.sucursalRepository = sucursalRepository;
        this.ventaRepository = ventaRepository;
    }



    @Override
    @Transactional(readOnly = true)
    public List<SucursalDTO> traerSucursales() {

        return sucursalRepository.findAll(Sort.by(Sort.Direction.ASC,"idSucursal")) //idSucursal es el nombre del atributo de Sucursal
                .stream()
                .map(Mapper::toSucursalDTO)
                .toList();

    }

    @Override
    @Transactional(readOnly = true)
    public SucursalDTO buscarSucursal(Long id) {
        return Mapper.toSucursalDTO(obtenerSucursal(id));
    }



    @Override
    @Transactional
    public void crearSucursal(SucursalDTO dto) {
        verificarDireccionSucursal(dto.getDireccionSucursal());
        verificarNombreSucursal(dto.getNombreSucursal());

        sucursalRepository.save(Mapper.toSucursal(dto));
    }

    @Override
    @Transactional
    public void actualizarSucursal(Long id, SucursalDTO dto) {
        Sucursal sucursal = obtenerSucursal(id);

        if (!Objects.equals(sucursal.getNombreSucursal(), dto.getNombreSucursal())) {
            verificarNombreSucursal(dto.getNombreSucursal());
        }

        if (!Objects.equals(sucursal.getDireccionSucursal(), dto.getDireccionSucursal())) {
            verificarDireccionSucursal(dto.getDireccionSucursal());
        }

        sucursal.setNombreSucursal(dto.getNombreSucursal());
        sucursal.setDireccionSucursal(dto.getDireccionSucursal());

        sucursalRepository.save(sucursal);
    }

    @Override
    @Transactional
    public void eliminarSucursal(Long id) {
        Sucursal sucursal = obtenerSucursal(id);

        if (ventaRepository.existsBySucursalId(id)) {
            throw new SucursalEnUsoException(id);
        }

        sucursalRepository.delete(sucursal);
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
