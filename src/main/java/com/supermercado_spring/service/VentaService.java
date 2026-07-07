package com.supermercado_spring.service;

import com.supermercado_spring.dto.DetalleVentaDTO;
import com.supermercado_spring.dto.VentaDTO;
import com.supermercado_spring.exception.ProductoNoEncontradoException;
import com.supermercado_spring.exception.VentaDuplicadaException;
import com.supermercado_spring.exception.VentaNoEncontradaException;
import com.supermercado_spring.mapper.Mapper;
import com.supermercado_spring.model.DetalleVenta;
import com.supermercado_spring.model.Producto;
import com.supermercado_spring.model.Sucursal;
import com.supermercado_spring.model.Venta;
import com.supermercado_spring.repository.ProductoRepositoryInterface;
import com.supermercado_spring.repository.VentaRepositoryInterface;
import com.supermercado_spring.service.interfaces.VentaServiceInterface;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class VentaService implements VentaServiceInterface {

    private final VentaRepositoryInterface ventaRepository;
    private final ProductoRepositoryInterface productoRepository;

    public VentaService(VentaRepositoryInterface ventaRepository, ProductoRepositoryInterface productoRepository) {
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public List<VentaDTO> traerVentas() {
        return ventaRepository.findAll(Sort.by(Sort.Direction.ASC, "idVenta"))
                .stream()
                .map(Mapper::toVentaDTO)
                .toList();
    }

    @Override
    public VentaDTO buscarVenta(Long id) {
        return Mapper.toVentaDTO(obtenerVenta(id));
    }

    @Override
    public void crearVenta(VentaDTO dto) {
        validarVentaDuplicada(dto.getIdVenta());

        Venta venta = new Venta();
        aplicarDatosVenta(venta, dto);
        ventaRepository.save(venta);
    }

    @Override
    public void actualizarVenta(Long id, VentaDTO dto) {
        Venta venta = obtenerVenta(id);
        aplicarDatosVenta(venta, dto);
        ventaRepository.save(venta);
    }

    @Override
    public void eliminarVenta(Long id) {
        ventaRepository.delete(obtenerVenta(id));
    }

    private void aplicarDatosVenta(Venta venta, VentaDTO dto) {
        venta.setFechaVenta(dto.getFechaVenta() != null
                ? dto.getFechaVenta()
                : (venta.getFechaVenta() != null ? venta.getFechaVenta() : LocalDateTime.now()));
        venta.setEstadoVenta(dto.getEstadoVenta());
        venta.setSucursal(dto.getIdSucursal() != null
                ? Sucursal.builder().idSucursal(dto.getIdSucursal()).build()
                : null);

        List<DetalleVenta> detalles = mapearDetalles(dto.getDetalles(), venta);
        venta.setDetalle(detalles);
        venta.setTotal(sumarSubtotales(detalles));
    }

    private List<DetalleVenta> mapearDetalles(List<DetalleVentaDTO> detallesDTO, Venta venta) {
        if (detallesDTO == null) {
            return new ArrayList<>();
        }

        List<DetalleVenta> detalles = new ArrayList<>();
        for (DetalleVentaDTO detalleDTO : detallesDTO) {
            Producto producto = obtenerProducto(detalleDTO.getIdProducto());
            DetalleVenta detalle = Mapper.toDetalleVenta(detalleDTO, producto);
            detalle.setVenta(venta);
            detalles.add(detalle);
        }
        return detalles;
    }

    private BigDecimal sumarSubtotales(List<DetalleVenta> detalles) {
        return detalles.stream()
                .map(DetalleVenta::getSubtotal)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Venta obtenerVenta(Long id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new VentaNoEncontradaException(id));
    }

    private Producto obtenerProducto(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException(id));
    }

    private void validarVentaDuplicada(Long id) {
        if (id == null) {
            return;
        }

        if (ventaRepository.existsById(id)) {
            throw new VentaDuplicadaException(id);
        }
    }
}
