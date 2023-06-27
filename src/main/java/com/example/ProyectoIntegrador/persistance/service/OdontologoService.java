package com.example.ProyectoIntegrador.persistance.service;

import com.example.ProyectoIntegrador.entities.Odontologo;

import com.example.ProyectoIntegrador.exceptions.EntityNotFound;
import com.example.ProyectoIntegrador.exceptions.InvalidArguments;
import com.example.ProyectoIntegrador.persistance.repository.OdontologoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;


@Service
@Log4j
@RequiredArgsConstructor
public class OdontologoService {
        private final OdontologoRepository odontologoRepository;


        public Odontologo guardarOdontologo(Odontologo odontologo) throws InvalidArguments {
            try {

                if (odontologo.getNombre() == null || odontologo.getNombre().isEmpty()) {
                    throw new InvalidArguments("El campo 'Nombre' no puede estar vacío.");
                }
                if (odontologo.getApellido() == null || odontologo.getApellido().isEmpty()) {
                    throw new InvalidArguments("El campo 'Apellido' no puede estar vacío.");
                }
                if (odontologo.getMatricula() == null || odontologo.getMatricula().isEmpty()) {
                    throw new InvalidArguments("El campo 'Matricula' no puede estar vacío.");
                }
                Odontologo odontologoExistente = odontologoRepository.findByMatricula(odontologo.getMatricula());
                if (odontologoExistente != null) {
                    throw new InvalidArguments("La Matrícula ya se registró para el odontologo: " + odontologoExistente.getNombre() + " " + odontologoExistente.getApellido() + ".");
                }

                Odontologo odontologoGuardado = odontologoRepository.save(odontologo);
                log.info("Odontologo guardado con éxito: " + odontologoGuardado);
                return odontologoGuardado;

            } catch (Exception e) {
                log.warn("Error al guardar Odontologo: " + e.getMessage());
                throw e;
            }
        }


        public void eliminarOdontologo(Long id) throws Exception {
            try {
                Optional<Odontologo> optionalOdontologo = odontologoRepository.findById(id);
                if (optionalOdontologo.isPresent()) {
                    Odontologo odontologoEliminado = optionalOdontologo.get();
                    odontologoRepository.deleteById(id);
                    log.info("Odontologo ID " + id + " eliminado con éxito.");
                } else {
                    throw new EntityNotFound("El odontologo con ID " + id + " no existe.");
                }
            } catch (Exception e) {
                log.warn("Error al eliminar Odontologo: " + e.getMessage());
                throw e;
            }
        }


        public Odontologo actualizarOdontologo(Odontologo odontologo) throws InvalidArguments, EntityNotFound {
            if (odontologo.getId() == null) {
                throw new InvalidArguments("El ID del odontologo no puede ser nulo.");
            }

            if (odontologoRepository.findById(odontologo.getId()).isPresent()) {
                try {

                    if (odontologo.getNombre() == null || odontologo.getNombre().isEmpty()) {
                        throw new InvalidArguments("El campo 'Nombre' no puede estar vacío.");
                    }
                    if (odontologo.getApellido() == null || odontologo.getApellido().isEmpty()) {
                        throw new InvalidArguments("El campo 'Apellido' no puede estar vacío.");
                    }
                    if (odontologo.getMatricula() == null || odontologo.getMatricula().isEmpty()) {
                        throw new InvalidArguments("El campo 'Matricula' no puede estar vacío.");
                    }
                    Odontologo odontologoExistente = odontologoRepository.findByMatricula(odontologo.getMatricula());
                    if (odontologoExistente != null && !odontologoExistente.getId().equals(odontologo.getId())) {
                        throw new InvalidArguments("La Matricula ya se registró para el odontologo: " + odontologoExistente.getNombre() + " " + odontologoExistente.getApellido() + ".");
                    }

                    log.info("Odontologo " + "ID " + odontologo.getId() + " " + "actualizado con éxito");

                    return odontologoRepository.save(odontologo);
                } catch (Exception e) {
                    log.warn("Error al actualizar Odontologo: " + e.getMessage());
                    throw e;
                }
            } else {
                log.warn("Odontologo " + "ID " + odontologo.getId() + " " + "no existe. Ingrese un ID existente para actualizar");

                throw new EntityNotFound("El odontologo con ID " + odontologo.getId() + " no existe.");

            }

        }

        public Odontologo buscarOdontologo(Long id) throws Exception {
            try {
                Optional<Odontologo> odontologoOptional = odontologoRepository.findById(id);
                if (odontologoOptional.isPresent()) {
                    return odontologoOptional.get();
                } else {
                    log.warn("Odontologo " + "ID " + id + " " + "no existe.");
                    throw new EntityNotFound("El ID " + id + " no existe en la base de datos.");
                }
            } catch (Exception e) {
                throw new Exception("Error al buscar odontologo. " + e.getMessage());
            }
        }


        public List<Odontologo> listarOdontologo() throws Exception {
            try {
                return odontologoRepository.findAll();
            } catch (Exception e) {
                throw new Exception("Error al listar odontologos: " + e.getMessage());
            }
        }


        public Odontologo buscarOdontologoPorMatricula(String matricula) throws EntityNotFoundException, Exception {
            try {
                Odontologo odontologo = odontologoRepository.findByMatricula(matricula);
                if (odontologo == null) {
                    throw new EntityNotFoundException("No se encontró ningún odontologo con DNI " + matricula);
                }
                return odontologo;
            } catch (Exception e) {
                throw new Exception("Error al buscar odontologo por DNI: " + e.getMessage());
            }
        }







    }



