package com.example.ProyectoIntegrador;

import com.example.ProyectoIntegrador.entities.Domicilio;
import com.example.ProyectoIntegrador.entities.Paciente;
import com.example.ProyectoIntegrador.persistance.service.PacienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class PacienteServiceTest {


    @Autowired
    private PacienteService pacienteService;
    private Paciente pacienteAGuardar;
    private Domicilio domicilioAGuardar;

    Random random = new Random();
    String dni = "0123" + random.nextInt(90000) + 10000;

    String guardarDNI = dni;

    @BeforeEach
    public void setup() {
        pacienteAGuardar = new Paciente();
        domicilioAGuardar = new Domicilio();


        pacienteAGuardar.setId(1L);
        pacienteAGuardar.setNombre("Ana");
        pacienteAGuardar.setApellido("Pérez");
        pacienteAGuardar.setDni(guardarDNI);

        domicilioAGuardar.setCalle("Av. Siempre Viva");
        domicilioAGuardar.setNumero(123);
        domicilioAGuardar.setLocalidad("Springfield");
        domicilioAGuardar.setProvincia("Homerolandia");
        pacienteAGuardar.setDomicilio(domicilioAGuardar);
    }

    @Test
    @Order(1)
    public void guardarPacienteTestDatos() throws Exception {
        // Uso de pacientes creados en el setup()
        Paciente pacienteGuardado = pacienteService.guardarPaciente(pacienteAGuardar);

        // Assert
        assertThat(pacienteGuardado).isNotNull();
        assertThat(pacienteGuardado.getId()).isNotNull();
        assertThat(pacienteGuardado.getNombre()).isEqualTo(pacienteAGuardar.getNombre());
        assertThat(pacienteGuardado.getApellido()).isEqualTo(pacienteAGuardar.getApellido());
        assertThat(pacienteGuardado.getDni()).isEqualTo(pacienteAGuardar.getDni());
        assertThat(pacienteGuardado.getDomicilio().getCalle()).isEqualTo(pacienteAGuardar.getDomicilio().getCalle());
        assertThat(pacienteGuardado.getDomicilio().getNumero()).isEqualTo(pacienteAGuardar.getDomicilio().getNumero());
        assertThat(pacienteGuardado.getDomicilio().getLocalidad()).isEqualTo(pacienteAGuardar.getDomicilio().getLocalidad());
        assertThat(pacienteGuardado.getDomicilio().getProvincia()).isEqualTo(pacienteAGuardar.getDomicilio().getProvincia());

    }


    @Test
    @Order(2)
    public void actualizarPacienteTestDatosDistintos() throws Exception {


        // Modificación de pacienteExistente ...

        pacienteAGuardar.setId(1L);
        pacienteAGuardar.setNombre("Ana Maria");
        pacienteAGuardar.setApellido("Lopez Perez");
        pacienteAGuardar.setDni("123");

        domicilioAGuardar.setCalle("Av. Siempre Vivía");
        domicilioAGuardar.setNumero(123456);
        domicilioAGuardar.setLocalidad("Springfield");
        domicilioAGuardar.setProvincia("Springfield");
        pacienteAGuardar.setDomicilio(domicilioAGuardar);

        Paciente pacienteActualizado = pacienteService.actualizarPaciente(pacienteAGuardar);

        // Assert
        assertThat(pacienteActualizado).isNotNull();
        assertThat(pacienteActualizado.getId()).isEqualTo(pacienteAGuardar.getId());
        assertThat(pacienteActualizado.getNombre().equals("Ana María"));
    }

    @Test
    @Order(3)
    public void buscarPacientePorIdTest() throws Exception {
        // Uso de paciente creado en el setup()
        Paciente pacienteGuardado = pacienteService.guardarPaciente(pacienteAGuardar);
        Long idPacienteABuscar = pacienteGuardado.getId();

        Paciente pacienteEncontrado = pacienteService.buscarPaciente(idPacienteABuscar);

        // Assert
        assertThat(pacienteEncontrado).isNotNull();
        assertThat(pacienteEncontrado.getId()).isEqualTo(idPacienteABuscar);
        assertThat(pacienteEncontrado.getDni()).isEqualTo(pacienteAGuardar.getDni());
    }

    @Test
    public void buscarPacientePorDNITest() throws Exception {

        Paciente pacienteActulizadoAEncontrar = pacienteService.buscarPaciente(1L);


        // Buscar el paciente por DNI
        Paciente pacienteEncontrado = pacienteService.buscarPacientePorDNI("123");

        // Verificar que el paciente encontrado sea igual al paciente guardado
        assertThat(pacienteEncontrado.getApellido()).isEqualTo(pacienteActulizadoAEncontrar.getApellido());
        assertThat(pacienteEncontrado.getNombre()).isEqualTo(pacienteActulizadoAEncontrar.getNombre());
        assertThat(pacienteEncontrado.getDni()).isEqualTo(pacienteActulizadoAEncontrar.getDni());
    }


}





