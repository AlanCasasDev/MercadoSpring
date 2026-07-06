package com.supermercado_spring.service.interfaces;

import com.supermercado_spring.dto.ProductoDTO;
import com.supermercado_spring.model.Producto;

import java.util.List;


public interface ProductoServiceInterface {

    List<ProductoDTO> traerProductos();
    ProductoDTO buscarProducto(Long id);
    void crearProducto(ProductoDTO productoDTO);
    void actualizarProducto(Long id, ProductoDTO productoDTO);
    void eliminarProducto(Long id);
    Long consultarCantidad(Long id);
    void aumentarStock(Long id, Long cant);
    void reducirStock(Long id, Long cant);
    void vaciarStock(Long id);
    //Producto obtenerProducto(Long id)

}
