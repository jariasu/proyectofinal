package com.example.ProyectoIntegrador.controller;

import com.example.ProyectoIntegrador.entities.Paciente;
import com.example.ProyectoIntegrador.exceptions.EntityNotFound;
import com.example.ProyectoIntegrador.exceptions.InvalidArguments;
import com.example.ProyectoIntegrador.persistance.service.PacienteService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@RestController
@RequestMapping("/pacientes")
@RequiredArgsConstructor

public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<?> guardarPaciente(@RequestBody Paciente paciente) {
        try {
            Paciente pacienteGuardado = pacienteService.guardarPaciente(paciente);
            return new ResponseEntity<>(pacienteGuardado, HttpStatus.CREATED);
        } catch (InvalidArguments e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar Paciente.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPacientePorId(@PathVariable Long id) {
        try {
            Paciente pacienteABuscar = pacienteService.buscarPaciente(id);
            return ResponseEntity.ok(pacienteABuscar);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PutMapping
    public ResponseEntity<?> actualizarPaciente(@RequestBody Paciente paciente) {
        try {
            Paciente pacienteActualizado = pacienteService.actualizarPaciente(paciente);
            return ResponseEntity.ok(pacienteActualizado);
        } catch (InvalidArguments | EntityNotFound e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar Paciente.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminarPaciente(@PathVariable Long id) {
        try {
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.ok("Paciente eliminado con ID " + id + " eliminado con éxito.");
        } catch (InvalidArguments | EntityNotFound e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar Paciente.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes() {
        try {
            List<Paciente> pacientes = pacienteService.listarPaciente();
            return ResponseEntity.ok(pacientes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<?> buscarPacientePorDNI(@PathVariable String dni) {
        try {
            Paciente paciente = pacienteService.buscarPacientePorDNI(dni);
            return ResponseEntity.ok(paciente);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar paciente por DNI.");
        }
    }

}

