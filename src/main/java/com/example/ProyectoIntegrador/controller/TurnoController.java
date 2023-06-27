package com.example.ProyectoIntegrador.controller;

import com.example.ProyectoIntegrador.DTO.TurnoDTO;
import com.example.ProyectoIntegrador.entities.Turno;
import com.example.ProyectoIntegrador.persistance.repository.TurnoRepository;
import com.example.ProyectoIntegrador.persistance.service.OdontologoService;
import com.example.ProyectoIntegrador.persistance.service.TurnoService;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
@NoArgsConstructor
public class TurnoController {
    @Autowired
    private  TurnoService turnoService;
    @Autowired
    private  OdontologoService odontologoService;

    private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(TurnoController.class);
    private  TurnoRepository turnoRepository;
    private TurnoDTO Turno;


    @PostMapping
    public ResponseEntity<TurnoDTO> guardarTurno(@RequestBody Turno turno) throws Exception {
        TurnoDTO turnoGuardado = turnoService.guardarTurno(turno);
        return ResponseEntity.ok(turnoGuardado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarTurnoPorId(@PathVariable Long id) {
        try {
            TurnoDTO turnoABuscar = turnoService.buscarTurno(id);
            return ResponseEntity.ok(turnoABuscar);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<Turno> actualizarTurno(@RequestBody Turno turno) throws Exception {
        return ResponseEntity.ok(turnoService.actualizarTurno(turno));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTurnoPorId(@PathVariable Long id) {
        turnoService.eliminarTurnoPorId(id);
        return ResponseEntity.ok("Turno eliminado exitosamente");
    }

    @GetMapping
    public ResponseEntity<List<TurnoDTO>> listaTurnos(){
        return ResponseEntity.ok(turnoService.listarTurnos());
    }

    @GetMapping("/od/{id}")
    public ResponseEntity<List<TurnoDTO>> obtenerTurnosPorOdontologo(@PathVariable Long id) {
        List<TurnoDTO> turnosDTO = turnoService.turnosPorIDOdontologo(id);
        return ResponseEntity.ok(turnosDTO);
    }

    @GetMapping("/pa/{id}")
    public ResponseEntity<List<TurnoDTO>> obtenerTurnosPorPaciente(@PathVariable Long id) {
        List<TurnoDTO> turnosDTO = turnoService.turnosPorIDPaciente(id);
        return ResponseEntity.ok(turnosDTO);
    }
}