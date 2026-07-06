package com.supermercado_spring.mapper;

import com.supermercado_spring.dto.DetalleVentaDTO;
import com.supermercado_spring.dto.ProductoDTO;
import com.supermercado_spring.dto.SucursalDTO;
import com.supermercado_spring.dto.VentaDTO;
import com.supermercado_spring.model.DetalleVenta;
import com.supermercado_spring.model.Producto;
import com.supermercado_spring.model.Sucursal;
import com.supermercado_spring.model.Venta;


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

    //Map de Sucursal a SucursalDTO
    public static SucursalDTO wtoSucursalDTO(Sucursal s) {
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
}
