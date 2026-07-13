package com.supermercado_spring.controller;

import com.supermercado_spring.dto.ProductoDTO;
import com.supermercado_spring.service.interfaces.ProductoServiceInterface;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    //http://localhost:8080/productos/{id}
    //Put modificar todo el recurso, patch solo modifica uno de sus campos
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarProducto(
            @PathVariable @Positive Long id,
            @Valid @RequestBody ProductoDTO productoDTO
    ) {
        productoService.actualizarProducto(id, productoDTO);
        return ResponseEntity.ok("Producto actualizado correctamente.");
    }

    //http://localhost:8080/productos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable @Positive Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.ok("Producto eliminado correctamente.");
    }

    //http://localhost:8080/productos/{id}/cantidad
    @GetMapping("/{id}/cantidad")
    public ResponseEntity<Long> consultarCantidad(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(productoService.consultarCantidad(id));
    }

    //http://localhost:8080/productos/5/stock/aumentar?cantidad=52
    @PatchMapping("/{id}/stock/aumentar")
    public ResponseEntity<String> aumentarStock(
            @PathVariable @Positive Long id,
            @RequestParam @Positive Long cantidad
    ) {
        productoService.aumentarStock(id, cantidad);
        return ResponseEntity.ok("Stock aumentado correctamente.");
    }

    //http://localhost:8080/productos/5/stock/reducir?cantidad=50
    @PatchMapping("/{id}/stock/reducir")
    public ResponseEntity<String> reducirStock(
            @PathVariable @Positive Long id,
            @RequestParam @Positive Long cantidad
    ) {
        productoService.reducirStock(id, cantidad);
        return ResponseEntity.ok("Stock reducido correctamente.");
    }

    //http://localhost:8080/productos/31/stock/vaciar
    @PatchMapping("/{id}/stock/vaciar")
    public ResponseEntity<String> vaciarStock(@PathVariable @Positive Long id) {
        productoService.vaciarStock(id);
        return ResponseEntity.ok("Stock vaciado correctamente.");
    }
}
