package com.example.ProyectoIntegrador.persistance.service;

import com.example.ProyectoIntegrador.entities.Domicilio;
import com.example.ProyectoIntegrador.persistance.repository.DomicilioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DomicilioService {
    private final DomicilioRepository domicilioRepository;

    public Domicilio guardarDomicilio(Domicilio domicilio) {

        return domicilioRepository.save(domicilio);
    }


}
