package com.example.ProyectoIntegrador.controller;

import com.example.ProyectoIntegrador.entities.Odontologo;

import com.example.ProyectoIntegrador.exceptions.EntityNotFound;
import com.example.ProyectoIntegrador.exceptions.InvalidArguments;
import com.example.ProyectoIntegrador.persistance.service.OdontologoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/odontologos")


public class OdontologoController {

    @Autowired
    private OdontologoService odontologoService;

    @PostMapping
    public ResponseEntity<?> guardarOdontologo(@RequestBody Odontologo odontologo) {
        try {
            Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);
            return new ResponseEntity<>(odontologoGuardado, HttpStatus.CREATED);
        } catch (InvalidArguments e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar Odontologo.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarOdontologoPorId(@PathVariable Long id) {
        try {
            Odontologo odontologoABuscar = odontologoService.buscarOdontologo(id);
            return ResponseEntity.ok(odontologoABuscar);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PutMapping
    public ResponseEntity<?> actualizarOdontologo(@RequestBody Odontologo odontologo) {
        try {
            Odontologo odontologoActualizado = odontologoService.actualizarOdontologo(odontologo);
            return ResponseEntity.ok(odontologoActualizado);
        } catch (InvalidArguments | EntityNotFound e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar Odontologo.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminarOdontologo(@PathVariable Long id) {
        try {
            odontologoService.eliminarOdontologo(id);
            return ResponseEntity.ok("Odontologo eliminado con ID " + id + " eliminado con éxito.");
        } catch (InvalidArguments e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (EntityNotFound e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar Odontologo.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> listarOdontologos() {
        try {
            List<Odontologo> odontologos = odontologoService.listarOdontologo();
            return ResponseEntity.ok(odontologos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<?> buscarOdontologoPorMatricula(@PathVariable String matricula) {
        try {
            Odontologo odontologo = odontologoService.buscarOdontologoPorMatricula(matricula);
            return ResponseEntity.ok(odontologo);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar odontologo por Matricula.");
        }
    }


}

