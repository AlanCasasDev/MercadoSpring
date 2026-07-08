package com.supermercado_spring.controller;

import com.supermercado_spring.dto.ProductoDTO;
import com.supermercado_spring.service.interfaces.ProductoServiceInterface;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoServiceInterface productoService;

    public ProductoController(ProductoServiceInterface productoService) {
        this.productoService = productoService;
    }

    //http://localhost:8080/productos
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> traerProductos() {
        return ResponseEntity.ok(productoService.traerProductos());
    }

    //http://localhost:8080/productos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> buscarProducto(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(productoService.buscarProducto(id));
    }

    //http://localhost:8080/productos/crear_producto
    @PostMapping("/crear_producto")
    public ResponseEntity<String> crearProducto(@Valid @RequestBody ProductoDTO productoDTO) {
        productoService.crearProducto(productoDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Producto creado correctamente.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarProducto(
            @PathVariable @Positive Long id,
            @Valid @RequestBody ProductoDTO productoDTO
    ) {
        productoService.actualizarProducto(id, productoDTO);
        return ResponseEntity.ok("Producto actualizado correctamente.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable @Positive Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.ok("Producto eliminado correctamente.");
    }

    @GetMapping("/{id}/cantidad")
    public ResponseEntity<Long> consultarCantidad(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(productoService.consultarCantidad(id));
    }

    @PutMapping("/{id}/stock/aumentar")
    public ResponseEntity<String> aumentarStock(
            @PathVariable @Positive Long id,
            @RequestParam @Positive Long cantidad
    ) {
        productoService.aumentarStock(id, cantidad);
        return ResponseEntity.ok("Stock aumentado correctamente.");
    }

    @PutMapping("/{id}/stock/reducir")
    public ResponseEntity<String> reducirStock(
            @PathVariable @Positive Long id,
            @RequestParam @Positive Long cantidad
    ) {
        productoService.reducirStock(id, cantidad);
        return ResponseEntity.ok("Stock reducido correctamente.");
    }

    @PutMapping("/{id}/stock/vaciar")
    public ResponseEntity<String> vaciarStock(@PathVariable @Positive Long id) {
        productoService.vaciarStock(id);
        return ResponseEntity.ok("Stock vaciado correctamente.");
    }
}
