package com.example.ProyectoIntegrador.persistance.repository;

import com.example.ProyectoIntegrador.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    @Query("SELECT o FROM Paciente o WHERE o.dni = :dni")
    Paciente findByDNI(@Param("dni") String dni);
}