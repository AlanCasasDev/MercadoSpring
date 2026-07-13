package com.supermercado_spring.service;

import com.supermercado_spring.dto.DetalleVentaDTO;
import com.supermercado_spring.dto.VentaDTO;
import com.supermercado_spring.dto.VentaResponseDTO;
import com.supermercado_spring.exception.ProductoNoEncontradoException;
import com.supermercado_spring.exception.VentaDuplicadaException;
import com.supermercado_spring.exception.VentaEstadoInvalidoException;
import com.supermercado_spring.exception.VentaNoEncontradaException;
import com.supermercado_spring.exception.StockProductoInsuficienteException;
import com.supermercado_spring.exception.SucursalNoEncontradaException;
import com.supermercado_spring.mapper.Mapper;
import com.supermercado_spring.model.DetalleVenta;
import com.supermercado_spring.model.Producto;
import com.supermercado_spring.model.Sucursal;
import com.supermercado_spring.model.Venta;
import com.supermercado_spring.model.enums.EnumEstadoVenta;
import com.supermercado_spring.repository.ProductoRepositoryInterface;
import com.supermercado_spring.repository.SucursalRepositoryInterface;
import com.supermercado_spring.repository.VentaRepositoryInterface;
import com.supermercado_spring.service.interfaces.VentaServiceInterface;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VentaService implements VentaServiceInterface {

    private final VentaRepositoryInterface ventaRepository;
    private final ProductoRepositoryInterface productoRepository;
    private final SucursalRepositoryInterface sucursalRepository;

    public VentaService(
            VentaRepositoryInterface ventaRepository,
            ProductoRepositoryInterface productoRepository,
            SucursalRepositoryInterface sucursalRepository
    ) {
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
        this.sucursalRepository = sucursalRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<VentaResponseDTO> traerVentas() {
        return ventaRepository.findAll(Sort.by(Sort.Direction.ASC, "idVenta"))
                .stream()
                .map(Mapper::toVentaResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public VentaResponseDTO buscarVenta(Long id) {
        return Mapper.toVentaResponseDTO(obtenerVenta(id));
    }

    @Override
    @Transactional
    public void crearVenta(VentaDTO dto) {
        validarVentaDuplicada(dto.getIdVenta());
        validarSucursalRequerida(dto.getIdSucursal());

        Venta venta = new Venta();
        aplicarDatosVenta(venta, dto);
        aplicarStockInicial(venta);
        ventaRepository.save(venta);
    }

    @Override
    @Transactional
    public void actualizarVenta(Long id, VentaDTO dto) {
        Venta venta = obtenerVenta(id);
        EnumEstadoVenta estadoAnterior = venta.getEstadoVenta();
        EnumEstadoVenta estadoNuevo = dto.getEstadoVenta() != null ? dto.getEstadoVenta() : estadoAnterior;
        List<DetalleVenta> detallesAnteriores = copiarDetalles(venta.getDetalle());

        validarTransicionEstado(estadoAnterior, estadoNuevo);
        aplicarDatosVenta(venta, dto);

        aplicarStockPorCambioDeEstado(estadoAnterior, detallesAnteriores, venta.getEstadoVenta(), venta.getDetalle());
        ventaRepository.save(venta);
    }

    @Override
    @Transactional
    public void eliminarVenta(Long id) {
        Venta venta = obtenerVenta(id);

        if (estadoReservaStock(venta.getEstadoVenta())) {
            reintegrarStock(venta.getDetalle());
        }

        ventaRepository.delete(venta);
    }


    private void aplicarDatosVenta(Venta venta, VentaDTO dto) {
        if (venta.getFechaVenta() == null) {
            venta.setFechaVenta(LocalDateTime.now());
        }
        venta.setEstadoVenta(dto.getEstadoVenta() != null ? dto.getEstadoVenta() : venta.getEstadoVenta());
        venta.setSucursal(dto.getIdSucursal() != null
                ? obtenerSucursal(dto.getIdSucursal())
                : venta.getSucursal());

        List<DetalleVenta> detalles = mapearDetalles(dto.getDetalles(), venta);
        venta.setDetalle(detalles);
        venta.setTotal(sumarSubtotales(detalles));
    }

    private List<DetalleVenta> copiarDetalles(List<DetalleVenta> detalles) {
        List<DetalleVenta> copia = new ArrayList<>();

        if (detalles == null) {
            return copia;
        }

        for (DetalleVenta detalle : detalles) {
            copia.add(DetalleVenta.builder()
                    .idDetalleVenta(detalle.getIdDetalleVenta())
                    .producto(detalle.getProducto())
                    .cantidad(detalle.getCantidad())
                    .precioUnitario(detalle.getPrecioUnitario())
                    .subtotal(detalle.getSubtotal())
                    .build());
        }

        return copia;
    }

    private void aplicarStockInicial(Venta venta) {
        if (estadoReservaStock(venta.getEstadoVenta())) {
            descontarStock(venta.getDetalle());
        }
    }

    private void aplicarStockPorCambioDeEstado(
            EnumEstadoVenta estadoAnterior,
            List<DetalleVenta> detallesAnteriores,
            EnumEstadoVenta estadoNuevo,
            List<DetalleVenta> detallesNuevos
    ) {
        boolean anteriorReserva = estadoReservaStock(estadoAnterior);
        boolean nuevoReserva = estadoReservaStock(estadoNuevo);

        if (anteriorReserva && nuevoReserva) {
            ajustarStockPorDiferencia(detallesAnteriores, detallesNuevos);
            return;
        }

        if (anteriorReserva) {
            reintegrarStock(detallesAnteriores);
            return;
        }

        if (nuevoReserva) {
            descontarStock(detallesNuevos);
        }
    }

    private void descontarStock(List<DetalleVenta> detalles) {
        ajustarStock(detalles, false);
    }

    private void reintegrarStock(List<DetalleVenta> detalles) {
        ajustarStock(detalles, true);
    }

    private void ajustarStock(List<DetalleVenta> detalles, boolean reintegrar) {
        if (detalles == null) {
            return;
        }

        for (DetalleVenta detalle : detalles) {
            Long idProducto = obtenerIdProducto(detalle);
            if (idProducto == null) {
                continue;
            }

            long cantidad = obtenerCantidadDetalle(detalle);
            ajustarStockProducto(idProducto, cantidad, reintegrar);
        }
    }

    private void ajustarStockPorDiferencia(List<DetalleVenta> detallesAnteriores, List<DetalleVenta> detallesNuevos) {
        Map<Long, Long> cantidadesAnteriores = construirMapaCantidades(detallesAnteriores);

        if (detallesNuevos != null) {
            for (DetalleVenta detalleNuevo : detallesNuevos) {
                Long idProducto = obtenerIdProducto(detalleNuevo);
                if (idProducto == null) {
                    continue;
                }

                long cantidadNueva = obtenerCantidadDetalle(detalleNuevo);
                long cantidadAnterior = cantidadesAnteriores.containsKey(idProducto)
                        ? cantidadesAnteriores.remove(idProducto)
                        : 0L;

                long diferencia = cantidadNueva - cantidadAnterior;

                if (diferencia > 0) {
                    ajustarStockProducto(idProducto, diferencia, false);
                } else if (diferencia < 0) {
                    ajustarStockProducto(idProducto, -diferencia, true);
                }
            }
        }

        for (Map.Entry<Long, Long> detalleAnterior : cantidadesAnteriores.entrySet()) {
            ajustarStockProducto(detalleAnterior.getKey(), detalleAnterior.getValue(), true);
        }
    }

    /*
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
    }*/

    private List<DetalleVenta> mapearDetalles(
            List<DetalleVentaDTO> detallesDTO,
            Venta venta) {

        List<DetalleVenta> detalles = new ArrayList<>();

        Map<Long, Integer> cantidades = agruparProductos(detallesDTO);

        for (Map.Entry<Long, Integer> entry : cantidades.entrySet()) {

            Producto producto = obtenerProducto(entry.getKey());

            Integer cantidad = entry.getValue();

            BigDecimal precio = producto.getPrecioProducto();

            BigDecimal subtotal =
                    precio.multiply(BigDecimal.valueOf(cantidad));

            DetalleVenta detalle = DetalleVenta.builder()
                    .producto(producto)
                    .venta(venta)
                    .cantidad(cantidad)
                    .precioUnitario(precio)
                    .subtotal(subtotal)
                    .build();

            detalles.add(detalle);
        }

        return detalles;
    }

    private Map<Long, Integer> agruparProductos(List<DetalleVentaDTO> detallesDTO) {
        return detallesDTO.stream()
                .collect(Collectors.toMap(
                        DetalleVentaDTO::getIdProducto,
                        DetalleVentaDTO::getCantidad,
                        Integer::sum
                ));
    }

    private Map<Long, Long> construirMapaCantidades(List<DetalleVenta> detalles) {
        Map<Long, Long> cantidades = new HashMap<>();

        if (detalles == null) {
            return cantidades;
        }

        for (DetalleVenta detalle : detalles) {
            Long idProducto = obtenerIdProducto(detalle);
            if (idProducto == null) {
                continue;
            }

            cantidades.merge(idProducto, obtenerCantidadDetalle(detalle), Long::sum);
        }

        return cantidades;
    }

    private Long obtenerIdProducto(DetalleVenta detalle) {
        if (detalle == null || detalle.getProducto() == null) {
            return null;
        }
        return detalle.getProducto().getIdProducto();
    }

    private long obtenerCantidadDetalle(DetalleVenta detalle) {
        return detalle.getCantidad() == null ? 0L : detalle.getCantidad().longValue();
    }

    private void ajustarStockProducto(Long idProducto, long cantidad, boolean reintegrar) {
        Producto producto = obtenerProducto(idProducto);
        Long stockActual = producto.getCantidadProducto();

        if (stockActual == null) {
            throw new com.supermercado_spring.exception.StockProductoNullException(producto.getIdProducto());
        }

        if (reintegrar) {
            producto.setCantidadProducto(stockActual + cantidad);
        } else {
            if (stockActual < cantidad) {
                throw new StockProductoInsuficienteException(
                        producto.getNombreProducto(),
                        cantidad,
                        stockActual
                );
            }
            producto.setCantidadProducto(stockActual - cantidad);
        }

        productoRepository.save(producto);
    }

    private void validarSucursalRequerida(Long idSucursal) {
        if (idSucursal == null) {
            throw new IllegalArgumentException("La sucursal es obligatoria para crear una venta");
        }

        obtenerSucursal(idSucursal);
    }

    private Sucursal obtenerSucursal(Long idSucursal) {
        return sucursalRepository.findById(idSucursal)
                .orElseThrow(() -> new SucursalNoEncontradaException(idSucursal));
    }

    private boolean estadoReservaStock(EnumEstadoVenta estadoVenta) {
        if (estadoVenta == null) {
            return false;
        }

        return switch (estadoVenta) {
            case PENDIENTE,
                 CONFIRMADA,
                 PAGADA,
                 PREPARANDO,
                 LISTA_PARA_ENTREGA,
                 ENTREGADA,
                 COMPLETADA -> true;
            case CANCELADA,
                 RECHAZADA,
                 REEMBOLSADA -> false;
        };
    }

    private void validarTransicionEstado(EnumEstadoVenta estadoAnterior, EnumEstadoVenta estadoNuevo) {
        if (estadoAnterior == estadoNuevo) {
            return;
        }

        if (!transicionPermitida(estadoAnterior, estadoNuevo)) {
            throw new VentaEstadoInvalidoException(estadoAnterior, estadoNuevo);
        }
    }

    private boolean transicionPermitida(EnumEstadoVenta estadoAnterior, EnumEstadoVenta estadoNuevo) {
        if (estadoAnterior == null || estadoNuevo == null) {
            return true;
        }

        return switch (estadoAnterior) {
            case PENDIENTE -> Set.of(
                    EnumEstadoVenta.CONFIRMADA,
                    EnumEstadoVenta.CANCELADA,
                    EnumEstadoVenta.RECHAZADA
            ).contains(estadoNuevo);
            case CONFIRMADA -> Set.of(
                    EnumEstadoVenta.PAGADA,
                    EnumEstadoVenta.PREPARANDO,
                    EnumEstadoVenta.CANCELADA,
                    EnumEstadoVenta.RECHAZADA,
                    EnumEstadoVenta.REEMBOLSADA
            ).contains(estadoNuevo);
            case PAGADA -> Set.of(
                    EnumEstadoVenta.PREPARANDO,
                    EnumEstadoVenta.CANCELADA,
                    EnumEstadoVenta.REEMBOLSADA
            ).contains(estadoNuevo);
            case PREPARANDO -> Set.of(
                    EnumEstadoVenta.LISTA_PARA_ENTREGA,
                    EnumEstadoVenta.CANCELADA,
                    EnumEstadoVenta.REEMBOLSADA
            ).contains(estadoNuevo);
            case LISTA_PARA_ENTREGA -> Set.of(
                    EnumEstadoVenta.ENTREGADA,
                    EnumEstadoVenta.CANCELADA,
                    EnumEstadoVenta.REEMBOLSADA
            ).contains(estadoNuevo);
            case ENTREGADA -> Set.of(
                    EnumEstadoVenta.COMPLETADA,
                    EnumEstadoVenta.REEMBOLSADA
            ).contains(estadoNuevo);
            case COMPLETADA -> Set.of(EnumEstadoVenta.REEMBOLSADA).contains(estadoNuevo);
            case CANCELADA -> Set.of(EnumEstadoVenta.REEMBOLSADA).contains(estadoNuevo);
            case RECHAZADA -> Set.of(EnumEstadoVenta.REEMBOLSADA).contains(estadoNuevo);
            case REEMBOLSADA -> Set.of(EnumEstadoVenta.REEMBOLSADA).contains(estadoNuevo);
        };
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
        if (id == null) {
            throw new ProductoNoEncontradoException(null);
        }

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
