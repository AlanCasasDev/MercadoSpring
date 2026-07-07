package com.supermercado_spring.service;

import com.supermercado_spring.dto.ProductoDTO;
import com.supermercado_spring.exception.ProductoDuplicadoException;
import com.supermercado_spring.exception.ProductoEnUsoException;
import com.supermercado_spring.exception.ProductoNoEncontradoException;
import com.supermercado_spring.exception.StockProductoInsuficienteException;
import com.supermercado_spring.exception.StockProductoNullException;
import com.supermercado_spring.mapper.Mapper;
import com.supermercado_spring.model.Producto;
import com.supermercado_spring.repository.ProductoRepositoryInterface;
import com.supermercado_spring.repository.VentaRepositoryInterface;
import com.supermercado_spring.service.interfaces.ProductoServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Objects;

@Service
public class ProductoService implements ProductoServiceInterface {

    //Inyeccion por Autowired
    /*@Autowired
    private ProductoRepositoryInterface productoRepository;*/

    //Inyeccion por constructor (dicen que es mas recomendable)
    private final ProductoRepositoryInterface productoRepository;
    private final VentaRepositoryInterface ventaRepository;

    public ProductoService(ProductoRepositoryInterface productoRepository, VentaRepositoryInterface ventaRepository) {
        this.productoRepository = productoRepository;
        this.ventaRepository = ventaRepository;
    }



    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> traerProductos() {

        return productoRepository.findAll(Sort.by(Sort.Direction.ASC,"idProducto")) //idProducto es el nombre del atributo de Producto
                .stream()
                .map(Mapper::toProductoDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoDTO buscarProducto(Long id) {

        return Mapper.toProductoDTO(obtenerProducto(id));
    }

    @Override
    @Transactional
    public void crearProducto(ProductoDTO dto) {

        validarProductoDuplicado(dto.getNombreProducto());

        productoRepository.save(Mapper.toProducto(dto));
    }

    @Override
    @Transactional
    public void actualizarProducto(Long id, ProductoDTO dto) {

        Producto producto = obtenerProducto(id);

        if (!Objects.equals(producto.getNombreProducto(), dto.getNombreProducto())) {
            validarProductoDuplicado(dto.getNombreProducto());
        }

        producto.setNombreProducto(dto.getNombreProducto());
        producto.setEnumCategoriaProducto(dto.getEnumCategoriaProducto());
        producto.setPrecioProducto(dto.getPrecioProducto());
        producto.setCantidadProducto(dto.getCantidadProducto());

        productoRepository.save(producto);
    }

    @Override
    @Transactional
    public void eliminarProducto(Long id) {
        Producto producto = obtenerProducto(id);

        if (ventaRepository.existsByProductoId(producto.getIdProducto())) {
            throw new ProductoEnUsoException(id);
        }

        productoRepository.delete(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public Long consultarCantidad(Long id) {

        Producto producto = obtenerProducto(id);

        validarCantidad(producto);

        return producto.getCantidadProducto();
    }

    @Override
    @Transactional
    public void aumentarStock(Long id, Long cantidad) {

        validarCantidadMovimiento(cantidad);

        Producto producto = obtenerProducto(id);

        validarCantidad(producto);  //si la cantidad es null arroja excpetion

        producto.setCantidadProducto(producto.getCantidadProducto() + cantidad);

        productoRepository.save(producto);
    }

    @Override
    @Transactional
    public void reducirStock(Long id, Long cantidad) {

        validarCantidadMovimiento(cantidad);

        Producto producto = obtenerProducto(id);

        validarCantidad(producto); //si la cantidad es null arroja excpetion

        if (producto.getCantidadProducto() < cantidad) {
            throw new StockProductoInsuficienteException(
                    producto.getNombreProducto(),
                    cantidad,
                    producto.getCantidadProducto()
            );
        }

        producto.setCantidadProducto(producto.getCantidadProducto() - cantidad);

        productoRepository.save(producto);
    }

    @Override
    @Transactional
    public void vaciarStock(Long id) {

        Producto producto = obtenerProducto(id);

        validarCantidad(producto);  //si la cantidad es null arroja excpetion

        producto.setCantidadProducto(0L);

        productoRepository.save(producto);
    }


    private Producto obtenerProducto(Long id) {

        return productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException(id));
    }

    private void validarCantidad(Producto producto) {

        if (producto.getCantidadProducto() == null) {
            throw new StockProductoNullException(producto.getIdProducto());
        }
    }

    private void validarProductoDuplicado(String nombreProducto) {

        if (productoRepository.existsByNombreProducto(nombreProducto)) {
            throw new ProductoDuplicadoException(nombreProducto);
        }
    }

    private void validarCantidadMovimiento(Long cantidad) {
        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
    }


}
