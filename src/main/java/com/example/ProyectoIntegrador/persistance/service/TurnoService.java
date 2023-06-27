package com.example.ProyectoIntegrador.persistance.service;

import com.example.ProyectoIntegrador.DTO.TurnoDTO;
import com.example.ProyectoIntegrador.entities.Odontologo;
import com.example.ProyectoIntegrador.entities.Paciente;
import com.example.ProyectoIntegrador.entities.Turno;
import com.example.ProyectoIntegrador.exceptions.InvalidArguments;
import com.example.ProyectoIntegrador.persistance.repository.OdontologoRepository;
import com.example.ProyectoIntegrador.persistance.repository.PacienteRepository;
import com.example.ProyectoIntegrador.persistance.repository.TurnoRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service

@Log4j

public class TurnoService {
    @Autowired
    private PacienteRepository pacienteRepository;


    @Autowired
    private TurnoRepository turnoRepository;

    @Autowired
    private OdontologoRepository odontologoRepository;


    public TurnoDTO guardarTurno(Turno turno) throws InvalidArguments {
        Turno turnoGuardado;
        try {
            if (turno.getOdontologo().getId() == null) {
                throw new InvalidArguments("Por favor designe un Odontologo.");
            }
            if (turno.getPaciente().getId() == null) {
                throw new InvalidArguments("Por favor designe un Paciente.");
            }
            turnoGuardado = turnoRepository.save(turno);
            log.info("Turno guardado con éxito " + turnoGuardado);
        } catch (Exception e) {
            log.error("Se produjo un error");
            throw new InvalidArguments("Error al guardar Turno.");
        }
        return toDTO(turnoGuardado);
    }

    @Transactional
    public void eliminarTurnoPorId(Long id) {
        turnoRepository.eliminarPorId(id);
    }


    public Turno actualizarTurno(Turno turno) throws Exception {
        if (turnoRepository.findById(turno.getId()).isPresent()) {
            log.info("Turno ID " + turno.getId() + " actualizado con éxito.");
            return (turnoRepository.save(turno));
        }
        throw new Exception("Turno no encontrado");
    }

    public TurnoDTO buscarTurno(Long id) throws Exception {
        if (turnoRepository.findById(id).isPresent()) {
            return toDTO(turnoRepository.findById(id).get());
        }
        throw new Exception("Turno no encontrado");
    }

    public List<TurnoDTO> listarTurnos() {
        return toDTOList(turnoRepository.findAll());
    }

    private Turno toEntity(TurnoDTO turnoDTO) {
        Turno turno = new Turno();
        turno.setId(turnoDTO.getId());
        turno.setFechaAltaTurno(LocalDate.parse(turnoDTO.getFechaAltaTurno()));
        turno.setFechaDeTurno(LocalDate.parse(turnoDTO.getFechaDeTurno()));
        return turno;
    }

    private TurnoDTO toDTO(Turno turno) {
        TurnoDTO turnoDTO = new TurnoDTO();
        turnoDTO.setId(turno.getId());
        turnoDTO.setFechaAltaTurno(turno.getFechaAltaTurno().toString());
        turnoDTO.setFechaDeTurno(turno.getFechaDeTurno().toString());
        turnoDTO.setPacienteId(turno.getPaciente().getId());
        turnoDTO.setPacienteNombre(turno.getPaciente().getNombre());
        turnoDTO.setPacienteApellido(turno.getPaciente().getApellido());
        turnoDTO.setOdontologoId(turno.getOdontologo().getId());
        turnoDTO.setOdontologoNombre(turno.getOdontologo().getNombre());
        turnoDTO.setOdontologoApellido(turno.getOdontologo().getApellido());
        return turnoDTO;
    }

    private List<TurnoDTO> toDTOList(List<Turno> turnos) {
        List<TurnoDTO> turnosDTO = new ArrayList<>();
        for (Turno turno : turnos) {
            turnosDTO.add(toDTO(turno));
        }
        return turnosDTO;
    }

    public List<TurnoDTO> turnosPorIDOdontologo(Long id) {
        Odontologo odontologo = odontologoRepository.findById(id).orElse(null);
        List<TurnoDTO> turnosOdontologoDTO = new ArrayList<>();

        if (odontologo != null) {
            List<Turno> turnosOdontologo = turnoRepository.findByOdontologo(odontologo);
            for (Turno turno : turnosOdontologo) {
                TurnoDTO turnoDTO = new TurnoDTO(turno.getId(),
                        turno.getFechaAltaTurno().toString(),
                        turno.getFechaDeTurno().toString(),
                        turno.getPaciente().getNombre(),
                        turno.getPaciente().getApellido(),
                        turno.getPaciente().getId(),
                        turno.getOdontologo().getNombre(),
                        turno.getOdontologo().getApellido(),
                        turno.getOdontologo().getId());
                turnosOdontologoDTO.add(turnoDTO);
            }
        }

        return turnosOdontologoDTO;
    }

    public List<TurnoDTO> turnosPorIDPaciente(Long id) {
        Paciente paciente = pacienteRepository.findById(id).orElse(null);
        List<TurnoDTO> turnosPacienteDTO = new ArrayList<>();

        if (paciente != null) {
            List<Turno> turnosPaciente = turnoRepository.findByPaciente(paciente);
            for (Turno turno : turnosPaciente) {
                TurnoDTO turnoDTO = new TurnoDTO(turno.getId(),
                        turno.getFechaAltaTurno().toString(),
                        turno.getFechaDeTurno().toString(),
                        turno.getPaciente().getNombre(),
                        turno.getPaciente().getApellido(),
                        turno.getPaciente().getId(),
                        turno.getOdontologo().getNombre(),
                        turno.getOdontologo().getApellido(),
                        turno.getOdontologo().getId());
                turnosPacienteDTO.add(turnoDTO);
            }
        }

        return turnosPacienteDTO;
    }


}