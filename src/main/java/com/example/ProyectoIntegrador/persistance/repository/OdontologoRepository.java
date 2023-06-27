package com.example.ProyectoIntegrador.persistance.repository;

import com.example.ProyectoIntegrador.entities.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OdontologoRepository extends JpaRepository<Odontologo, Long> {
    @Query("SELECT o FROM Odontologo o WHERE o.matricula = :matricula")
    Odontologo findByMatricula(@Param("matricula") String matricula);
}
