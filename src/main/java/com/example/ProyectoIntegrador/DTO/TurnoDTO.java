package com.example.ProyectoIntegrador.DTO;

import com.example.ProyectoIntegrador.entities.Turno;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data

@AllArgsConstructor
@NoArgsConstructor
public class TurnoDTO {


    private Long id;
    private String fechaAltaTurno;
    private String fechaDeTurno;
    private String pacienteNombre;
    private String pacienteApellido;
    private Long pacienteId;
    private String odontologoNombre;
    private String odontologoApellido;
    private Long odontologoId;

    public TurnoDTO(Turno turno) {

    }
}


