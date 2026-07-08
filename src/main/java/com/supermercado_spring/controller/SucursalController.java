package com.supermercado_spring.controller;

import com.supermercado_spring.dto.SucursalDTO;
import com.supermercado_spring.service.interfaces.SucursalServiceInterface;
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
@RequestMapping("/sucursales")
public class SucursalController {

    private final SucursalServiceInterface sucursalService;

    public SucursalController(SucursalServiceInterface sucursalService) {
        this.sucursalService = sucursalService;
    }

    //http://localhost:8080/sucursales
    @GetMapping
    public ResponseEntity<List<SucursalDTO>> traerSucursales() {
        return ResponseEntity.ok(sucursalService.traerSucursales());
    }

    //http://localhost:8080/sucursales/{id}
    @GetMapping("/{id}")
    public ResponseEntity<SucursalDTO> buscarSucursal(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(sucursalService.buscarSucursal(id));
    }

    //http://localhost:8080/sucursales/crear_sucursal
    @PostMapping("/crear_sucursal")
    public ResponseEntity<String> crearSucursal(@Valid @RequestBody SucursalDTO sucursalDTO) {
        sucursalService.crearSucursal(sucursalDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Sucursal creada correctamente.");
    }

    //http://localhost:8080/sucursales/{id}
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarSucursal(
            @PathVariable @Positive Long id,
            @Valid @RequestBody SucursalDTO sucursalDTO
    ) {
        sucursalService.actualizarSucursal(id, sucursalDTO);
        return ResponseEntity.ok("Sucursal actualizada correctamente.");
    }

    //http://localhost:8080/sucursales/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarSucursal(@PathVariable @Positive Long id) {
        sucursalService.eliminarSucursal(id);
        return ResponseEntity.ok("Sucursal eliminada correctamente.");
    }
}
