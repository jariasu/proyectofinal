package com.example.ProyectoIntegrador.persistance.repository;

import com.example.ProyectoIntegrador.entities.Odontologo;
import com.example.ProyectoIntegrador.entities.Paciente;
import com.example.ProyectoIntegrador.entities.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {
    List<Turno> findByOdontologo(Odontologo odontologo);
    List<Turno> findByPaciente(Paciente paciente);

    //Se creo esto xq no se eliminaba el turno
    @Modifying
    @Query("DELETE FROM Turno t WHERE t.id = :id")
    void eliminarPorId(@Param("id") Long id);


}