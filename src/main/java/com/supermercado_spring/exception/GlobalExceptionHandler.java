package com.supermercado_spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Cuando @Valid falla, Spring lanza MethodArgumentNotValidException.
    // Usamos Map porque pueden fallar varios campos a la vez:
    // { "nombre": "El nombre no puede estar vacío", "precio": "Debe ser mayor que cero" }
    //
    // Versión simplificada (solo devuelve el primer error):
    // String mensaje = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidacion(
            MethodArgumentNotValidException ex) {

        Map<String, String> errores = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<String> manejarProductoNoEncontrado(ProductoNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ProductoDuplicadoException.class)
    public ResponseEntity<String> manejarProductoDuplicado(ProductoDuplicadoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(StockProductoInsuficienteException.class)
    public ResponseEntity<String> manejarStockInsuficiente(StockProductoInsuficienteException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(StockProductoNullException.class)
    public ResponseEntity<String> manejarStockNulo(StockProductoNullException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(SucursalNoEncontradaException.class)
    public ResponseEntity<String> manejarSucursalNoEncontrada(SucursalNoEncontradaException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(NombreSucursalDuplicadaException.class)
    public ResponseEntity<String> manejarNombreSucursalDuplicada(NombreSucursalDuplicadaException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(DireccionSucursalDuplicadaException.class)
    public ResponseEntity<String> manejarDireccionSucursalDuplicada(DireccionSucursalDuplicadaException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(VentaNoEncontradaException.class)
    public ResponseEntity<String> manejarVentaNoEncontrada(VentaNoEncontradaException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(VentaDuplicadaException.class)
    public ResponseEntity<String> manejarVentaDuplicada(VentaDuplicadaException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(VentaEstadoInvalidoException.class)
    public ResponseEntity<String> manejarVentaEstadoInvalido(VentaEstadoInvalidoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(SucursalEnUsoException.class)
    public ResponseEntity<String> manejarSucursalEnUso(SucursalEnUsoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(ProductoEnUsoException.class)
    public ResponseEntity<String> manejarProductoEnUso(ProductoEnUsoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> manejarArgumentoInvalido(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> manejarIntegridadDatos(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Violacion de integridad de datos: " + ex.getMostSpecificCause().getMessage());
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<String> manejarBloqueoOptimista(OptimisticLockingFailureException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Conflicto de actualizacion concurrente: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> manejarExcepcionGenerica(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ocurrio un error inesperado: " + ex.getMessage());
    }
}
