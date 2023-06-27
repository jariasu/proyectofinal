package com.example.ProyectoIntegrador.persistance.service;

import com.example.ProyectoIntegrador.entities.Paciente;
import com.example.ProyectoIntegrador.exceptions.EntityNotFound;
import com.example.ProyectoIntegrador.exceptions.InvalidArguments;
import com.example.ProyectoIntegrador.persistance.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;


@Service
@Log4j
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository pacienteRepository;


    public Paciente guardarPaciente(Paciente paciente) throws InvalidArguments {
        try {

            if (paciente.getNombre() == null || paciente.getNombre().isEmpty()) {
                throw new InvalidArguments("El campo 'Nombre' no puede estar vacío.");
            }
            if (paciente.getApellido() == null || paciente.getApellido().isEmpty()) {
                throw new InvalidArguments("El campo 'Apellido' no puede estar vacío.");
            }
            if (paciente.getDni() == null || paciente.getDni().isEmpty()) {
                throw new InvalidArguments("El campo 'DNI' no puede estar vacío.");
            }
            Paciente pacienteExistente = pacienteRepository.findByDNI(paciente.getDni());
            if (pacienteExistente != null) {
                throw new InvalidArguments("El DNI ya se registró para el paciente: " + pacienteExistente.getNombre() + " " + pacienteExistente.getApellido() + ".");
            }
            if (paciente.getDomicilio().getCalle() == null || paciente.getDomicilio().getCalle().isEmpty()) {
                throw new InvalidArguments("El campo 'Calle' no puede estar vacío.");
            }
            if (paciente.getDomicilio().getNumero() == null) {
                throw new InvalidArguments("El campo 'Número' no puede estar vacío.");
            }
            if (paciente.getDomicilio().getLocalidad() == null || paciente.getDomicilio().getLocalidad().isEmpty()) {
                throw new InvalidArguments("El campo 'Localidad' no puede estar vacío.");
            }
            if (paciente.getDomicilio().getProvincia() == null || paciente.getDomicilio().getProvincia().isEmpty()) {
                throw new InvalidArguments("El campo 'Provincia' no puede estar vacío.");
            }

            Paciente pacienteGuardado = pacienteRepository.save(paciente);
            log.info("Paciente guardado con éxito: " + pacienteGuardado);
            return pacienteGuardado;

        } catch (Exception e) {
            log.warn("Error al guardar Paciente: " + e.getMessage());
            throw e;
        }
    }


    public void eliminarPaciente(Long id) throws Exception {
        try {
            Optional<Paciente> optionalPaciente = pacienteRepository.findById(id);
            if (optionalPaciente.isPresent()) {
                Paciente pacienteEliminado = optionalPaciente.get();
                pacienteRepository.deleteById(id);
                log.info("Paciente ID " + id + " eliminado con éxito.");
            } else {
                throw new EntityNotFound("El paciente con ID " + id + " no existe.");
            }
        } catch (Exception e) {
            log.warn("Error al eliminar Paciente: " + e.getMessage());
            throw e;
        }
    }

    public Paciente actualizarPaciente(Paciente paciente) throws InvalidArguments, EntityNotFound {
        if (paciente.getId() == null) {
            throw new InvalidArguments("El ID del paciente no puede ser nulo.");
        }

        if (pacienteRepository.findById(paciente.getId()).isPresent()) {
            try {

                if (paciente.getNombre() == null || paciente.getNombre().isEmpty()) {
                    throw new InvalidArguments("El campo 'Nombre' no puede estar vacío.");
                }
                if (paciente.getApellido() == null || paciente.getApellido().isEmpty()) {
                    throw new InvalidArguments("El campo 'Apellido' no puede estar vacío.");
                }
                if (paciente.getDni() == null || paciente.getDni().isEmpty()) {
                    throw new InvalidArguments("El campo 'DNI' no puede estar vacío.");
                }
                Paciente pacienteExistente = pacienteRepository.findByDNI(paciente.getDni());
                if (pacienteExistente != null && !pacienteExistente.getId().equals(paciente.getId())) {
                    throw new InvalidArguments("El DNI ya se registró para el paciente: " + pacienteExistente.getNombre() + " " + pacienteExistente.getApellido() + ".");
                }
                if (paciente.getDomicilio().getCalle() == null || paciente.getDomicilio().getCalle().isEmpty()) {
                    throw new InvalidArguments("El campo 'Calle' no puede estar vacío.");
                }
                if (paciente.getDomicilio().getNumero() == null) {
                    throw new InvalidArguments("El campo 'Número' no puede estar vacío.");
                }
                if (paciente.getDomicilio().getLocalidad() == null || paciente.getDomicilio().getLocalidad().isEmpty()) {
                    throw new InvalidArguments("El campo 'Localidad' no puede estar vacío.");
                }
                if (paciente.getDomicilio().getProvincia() == null || paciente.getDomicilio().getProvincia().isEmpty()) {
                    throw new InvalidArguments("El campo 'Provincia' no puede estar vacío.");
                }
                log.info("Paciente " + "ID " + paciente.getId() + " " + "actualizado con éxito");

                return pacienteRepository.save(paciente);
            } catch (Exception e) {
                log.warn("Error al actualizar Paciente: " + e.getMessage());
                throw e;
            }
        } else {
            log.warn("Paciente " + "ID " + paciente.getId() + " " + "no existe. Ingrese un ID existente para actualizar");

            throw new EntityNotFound("El paciente con ID " + paciente.getId() + " no existe.");

        }

    }

    public Paciente buscarPaciente(Long id) throws Exception {
        try {
            Optional<Paciente> pacienteOptional = pacienteRepository.findById(id);
            if (pacienteOptional.isPresent()) {
                return pacienteOptional.get();
            } else {
                log.warn("Paciente " + "ID " + id + " " + "no existe.");
                throw new EntityNotFound("El ID " + id + " no existe en la base de datos.");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar paciente. " + e.getMessage());
        }
    }


    public List<Paciente> listarPaciente() throws Exception {
        try {
            return pacienteRepository.findAll();
        } catch (Exception e) {
            throw new Exception("Error al listar pacientes: " + e.getMessage());
        }
    }


    public Paciente buscarPacientePorDNI(String dni) throws EntityNotFoundException, Exception {
        try {
            Paciente paciente = pacienteRepository.findByDNI(dni);
            if (paciente == null) {
                throw new EntityNotFoundException("No se encontró ningún paciente con DNI " + dni);
            }
            return paciente;
        } catch (Exception e) {
            throw new Exception("Error al buscar paciente por DNI: " + e.getMessage());
        }
    }

}



