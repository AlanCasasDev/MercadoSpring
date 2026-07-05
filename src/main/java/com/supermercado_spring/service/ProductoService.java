package com.supermercado_spring.service;

import com.supermercado_spring.dto.ProductoDTO;
import com.supermercado_spring.exception.ProductoDuplicadoException;
import com.supermercado_spring.exception.ProductoNoEncontradoException;
import com.supermercado_spring.mapper.Mapper;
import com.supermercado_spring.model.Producto;
import com.supermercado_spring.repository.ProductoRepositoryInterface;
import com.supermercado_spring.service.interfaces.ProductoServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ProductoService implements ProductoServiceInterface {

    @Autowired
    private ProductoRepositoryInterface productoRepository;

    @Override public List<ProductoDTO> traerProductos() {

        List<ProductoDTO> productos =
                productoRepository
                        .findAll()
                        .stream()
                        .sorted(Comparator.comparing(Producto::getIdProducto))
                        .map(Mapper::toProductoDTO)
                        .toList();

        return productos;
    }

    @Override
    public ProductoDTO buscarProducto(Long id) {

        ProductoDTO productoDTO =
                productoRepository
                .findById(id)
                .map(Mapper::toProductoDTO)
                .orElseThrow(() -> new ProductoNoEncontradoException(id));

        return productoDTO;
    }

    @Override
    public void crearProducto(ProductoDTO productoDTO) {

        if (productoRepository.existsByNombreProducto(productoDTO.getNombreProducto())) {
            throw new ProductoDuplicadoException(productoDTO.getNombreProducto());
        }

        Producto producto = Producto.builder()
                .nombreProducto(productoDTO.getNombreProducto())
                .enumCategoriaProducto(productoDTO.getEnumCategoriaProducto())
                .precioProducto(productoDTO.getPrecioProducto())
                .cantidadProducto(productoDTO.getCantidadProducto())
                .build();

        productoRepository.save(producto);
    }

    @Override
    public void actualizarProducto(Long id, ProductoDTO productoDTONuevo) {
        Producto productoViejo = productoRepository
                .findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException(id));

        //Con esto permites que el producto conserve su nombre, pero impides que adopte el nombre de otro producto existente.
        //Si los nombres entre los productos son diferentes y ademas eñ nombre nuevo existe en la base de datos. Entonces esta duplicado
        if (!productoViejo.getNombreProducto().equals(productoDTONuevo.getNombreProducto())
                && productoRepository.existsByNombreProducto(productoDTONuevo.getNombreProducto())) {

            throw new ProductoDuplicadoException(productoDTONuevo.getNombreProducto());
        }

        productoViejo.setNombreProducto(productoDTONuevo.getNombreProducto());
        productoViejo.setEnumCategoriaProducto(productoDTONuevo.getEnumCategoriaProducto());
        productoViejo.setPrecioProducto(productoDTONuevo.getPrecioProducto());
        productoViejo.setCantidadProducto(productoDTONuevo.getCantidadProducto());
        productoRepository.save(productoViejo);
    }

    @Override
    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ProductoNoEncontradoException(id);
        }

        productoRepository.deleteById(id);

        /*
        Producto producto = productoRepository.findById(id)
        .orElseThrow(() -> new ProductoNoEncontradoException(id));
        productoRepository.delete(producto);
        */ //Tambien valida

    }

    @Override
    public ProductoDTO crearProductoDTO(ProductoDTO productoDTO) {
        return null;
    }

    @Override
    public ProductoDTO actualizarProductoDTO(Long id, ProductoDTO productoDTO) {
        return null;
    }


}
