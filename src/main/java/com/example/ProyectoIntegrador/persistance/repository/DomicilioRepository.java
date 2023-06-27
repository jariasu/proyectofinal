package com.example.ProyectoIntegrador.persistance.repository;

import com.example.ProyectoIntegrador.entities.Domicilio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomicilioRepository extends JpaRepository<Domicilio, Long> {
}