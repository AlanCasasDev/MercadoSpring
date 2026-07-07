package com.supermercado_spring.controller;

import com.supermercado_spring.dto.VentaDTO;
import com.supermercado_spring.service.interfaces.VentaServiceInterface;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/ventas")
public class VentaController {

    private final VentaServiceInterface ventaService;

    public VentaController(VentaServiceInterface ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping
    public ResponseEntity<List<VentaDTO>> traerVentas() {
        return ResponseEntity.ok(ventaService.traerVentas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaDTO> buscarVenta(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(ventaService.buscarVenta(id));
    }

    @PostMapping("/crear_venta")
    public ResponseEntity<String> crearVenta(@Valid @RequestBody VentaDTO ventaDTO) {
        ventaService.crearVenta(ventaDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Venta creada correctamente.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarVenta(
            @PathVariable @Positive Long id,
            @Valid @RequestBody VentaDTO ventaDTO
    ) {
        ventaService.actualizarVenta(id, ventaDTO);
        return ResponseEntity.ok("Venta actualizada correctamente.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarVenta(@PathVariable @Positive Long id) {
        ventaService.eliminarVenta(id);
        return ResponseEntity.ok("Venta eliminada correctamente.");
    }
}
