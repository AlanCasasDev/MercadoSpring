package com.supermercado_spring.mapper;

import com.supermercado_spring.dto.*;
import com.supermercado_spring.model.DetalleVenta;
import com.supermercado_spring.model.Producto;
import com.supermercado_spring.model.Sucursal;
import com.supermercado_spring.model.Venta;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Mapper {

    //Map de Producto a ProductoDTO
    public static ProductoDTO toProductoDTO(Producto p){

        if(p==null) {
            return null;
        }

        ProductoDTO productoDTO = ProductoDTO.builder()
                .idProducto(p.getIdProducto())
                .nombreProducto(p.getNombreProducto())
                .precioProducto(p.getPrecioProducto())
                .cantidadProducto(p.getCantidadProducto())
                .enumCategoriaProducto(p.getEnumCategoriaProducto())
                .build();

        return productoDTO;
    }

    //Map de ProductoDTO a Producto
    public static Producto toProducto(ProductoDTO dto) {

        if (dto == null) {
            return null;
        }

        Producto producto = Producto.builder()
                .nombreProducto(dto.getNombreProducto())
                .enumCategoriaProducto(dto.getEnumCategoriaProducto())
                .precioProducto(dto.getPrecioProducto())
                .cantidadProducto(dto.getCantidadProducto())
                .build();

        return producto;
    }


    //Map de Sucursal a SucursalDTO
    public static SucursalDTO toSucursalDTO(Sucursal s) {
        if (s == null) {
            return null;
        }

        SucursalDTO sucursalDTO = SucursalDTO.builder()
                .idSucursal(s.getIdSucursal())
                .nombreSucursal(s.getNombreSucursal())
                .direccionSucursal(s.getDireccionSucursal())
                .build();

        return sucursalDTO;
    }

    //Map de SucursalDTO a Sucursal
    public static Sucursal toSucursal(SucursalDTO dto) {
        if (dto == null) {
            return null;
        }

        Sucursal sucursal = Sucursal.builder()
                .nombreSucursal(dto.getNombreSucursal())
                .direccionSucursal(dto.getDireccionSucursal())
                .build();

        return sucursal;
    }


    //Map de Venta a VentaResponseDTO
    public static VentaResponseDTO toVentaResponseDTO(Venta v) {
        if (v == null) {
            return null;
        }

        List<DetalleVentaResponseDTO> detallesDTO = v.getDetalle() == null
                ? new ArrayList<>()
                : v.getDetalle().stream()
                .map(Mapper::toDetalleVentaResponseDTO)
                .toList();

        BigDecimal sumatoriaSubtotalDetallesDTO = detallesDTO.stream()
                .map(DetalleVentaResponseDTO::getSubtotal)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        VentaResponseDTO ventaresponseDTO = VentaResponseDTO.builder()
                .idVenta(v.getIdVenta())
                .fechaVenta(v.getFechaVenta())
                .estadoVenta(v.getEstadoVenta())
                .idSucursal(v.getSucursal() != null ? v.getSucursal().getIdSucursal() : null)
                .detalles(detallesDTO)
                .total(sumatoriaSubtotalDetallesDTO)
                .build();

        return ventaresponseDTO;
    }

    private static BigDecimal calcularSubtotal(BigDecimal precioUnitario, Integer cantidad) {
        if (precioUnitario == null || cantidad == null) {
            return null;
        }
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }



    //MAp de VentaDTO a Venta
    public static Venta toVenta(VentaDTO dto) {
        if (dto == null) {
            return null;
        }

        List<DetalleVenta> detalles = dto.getDetalles() == null
                ? new ArrayList<>()
                : dto.getDetalles().stream()
                .map(Mapper::toDetalleVenta)
                .toList();

        BigDecimal sumatoriaSubtotalDetalles = detalles.stream()
                .map(DetalleVenta::getSubtotal)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Venta venta = Venta.builder()
                .idVenta(dto.getIdVenta())
                //.fechaVenta(dto.getFechaVenta())  //En donde se use toVenta hacer esto:
                    /* Venta venta = Mapper.toVenta(dto);
                       venta.setFechaVenta(LocalDateTime.now());*/
                .estadoVenta(dto.getEstadoVenta())
                .sucursal(dto.getIdSucursal() != null
                        ? Sucursal.builder().idSucursal(dto.getIdSucursal()).build()
                        : null)
                .detalle(detalles)
                .total(sumatoriaSubtotalDetalles)
                .build();

        return venta;
    }



    //Map de DetalleVenta a DetalleVentaDTO
    public static DetalleVentaDTO toDetalleVentaDTO(DetalleVenta dv) {
        if (dv == null) {
            return null;
        }

        DetalleVentaDTO detalleVentaDTO = DetalleVentaDTO.builder()
                .idProducto(dv.getProducto() != null ? dv.getProducto().getIdProducto() : null)
                .cantidad(dv.getCantidad())
                .build();

        return detalleVentaDTO;
    }

    public static DetalleVentaResponseDTO toDetalleVentaResponseDTO(DetalleVenta dv) {
        if (dv == null) {
            return null;
        }

        DetalleVentaResponseDTO detalleVentaResponseDTO = DetalleVentaResponseDTO.builder()
                .idProducto(dv.getProducto() != null ? dv.getProducto().getIdProducto() : null)
                .cantidad(dv.getCantidad())
                .nombreProducto(dv.getProducto().getNombreProducto())
                .idDetalleVenta(dv.getIdDetalleVenta())
                .precioUnitario(dv.getPrecioUnitario())
                .subtotal(dv.getSubtotal())
                .build();

        return detalleVentaResponseDTO;
    }

    public static DetalleVenta toDetalleVenta(DetalleVentaDTO dto) {
        return toDetalleVenta(dto, null);
    }

    //Map de DetalleVentaDTO a DetalleVenta con producto resuelto
    public static DetalleVenta toDetalleVenta(DetalleVentaDTO dto, Producto producto) {
        if (dto == null) {
            return null;
        }

        return DetalleVenta.builder()
                .producto(producto)
                .cantidad(dto.getCantidad())
                .build();
    }
}
