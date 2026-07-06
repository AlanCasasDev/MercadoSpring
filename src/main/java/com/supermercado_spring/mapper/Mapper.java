package com.supermercado_spring.mapper;

import com.supermercado_spring.dto.DetalleVentaDTO;
import com.supermercado_spring.dto.ProductoDTO;
import com.supermercado_spring.dto.SucursalDTO;
import com.supermercado_spring.dto.VentaDTO;
import com.supermercado_spring.model.DetalleVenta;
import com.supermercado_spring.model.Producto;
import com.supermercado_spring.model.Sucursal;
import com.supermercado_spring.model.Venta;
import java.util.ArrayList;
import java.util.List;


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


    //Map de Venta a VentaDTO
    public static VentaDTO toVentaDTO(Venta v) {
        if (v == null) {
            return null;
        }

        VentaDTO ventaDTO = VentaDTO.builder()
                .idVenta(v.getIdVenta())
                .fechaVenta(v.getFechaVenta())
                .total(v.getTotal())
                .estadoVenta(v.getEstadoVenta())
                .idSucursal(v.getSucursal() != null ? v.getSucursal().getIdSucursal() : null)
                .detalles(
                        v.getDetalle() == null
                                ? null
                                : v.getDetalle().stream()
                                .map(Mapper::toDetalleVentaDTO)
                                .toList()
                )
                .build();

        return ventaDTO;
    }

    //MAp de VentaDTO a Venta
    public static Venta toVenta(VentaDTO dto) {
        if (dto == null) {
            return null;
        }

        Venta venta = Venta.builder()
                .idVenta(dto.getIdVenta())
                .fechaVenta(dto.getFechaVenta())
                .total(dto.getTotal())
                .estadoVenta(dto.getEstadoVenta())
                .sucursal(dto.getIdSucursal() != null
                        ? Sucursal.builder().idSucursal(dto.getIdSucursal()).build()
                        : null)
                .detalle(dto.getDetalles() == null
                        ? new ArrayList<>()
                        : dto.getDetalles().stream()
                        .map(Mapper::toDetalleVenta)
                        .toList())
                .build();

        return venta;
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


    //Map de DetalleVenta a DetalleVentaDTO
    public static DetalleVentaDTO toDetalleVentaDTO(DetalleVenta dv) {
        if (dv == null) {
            return null;
        }

        DetalleVentaDTO detalleVentaDTO = DetalleVentaDTO.builder()
                .idDetalleVenta(dv.getIdDetalleVenta())
                .total(dv.getTotal())
                .cantidad(dv.getCantidad())
                .precioUnitario(dv.getPrecioUnitario())
                .nombreProducto(dv.getProducto() != null ? dv.getProducto().getNombreProducto() : null)
                .build();

        return detalleVentaDTO;
    }

    //Map de DetalleVentaDTO a DetalleVenta
    public static DetalleVenta toDetalleVenta(DetalleVentaDTO dto) {
        if (dto == null) {
            return null;
        }

        DetalleVenta detalleVenta = DetalleVenta.builder()
                .idDetalleVenta(dto.getIdDetalleVenta())
                .total(dto.getTotal())
                .cantidad(dto.getCantidad())
                .precioUnitario(dto.getPrecioUnitario())
                .producto(dto.getNombreProducto() != null
                        ? Producto.builder().nombreProducto(dto.getNombreProducto()).build()
                        : null)
                .build();

        return detalleVenta;
    }
}
