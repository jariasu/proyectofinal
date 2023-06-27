package com.example.ProyectoIntegrador;

import com.example.ProyectoIntegrador.entities.Usuario;
import com.example.ProyectoIntegrador.entities.UsuarioRole;
import com.example.ProyectoIntegrador.persistance.repository.UsuarioRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CargarUsuariosRoles implements ApplicationRunner {
    private UsuarioRepository usuarioRepository;

    public CargarUsuariosRoles(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(ApplicationArguments args){
        BCryptPasswordEncoder cifrador = new BCryptPasswordEncoder();
        String passCifrada = cifrador.encode("usuario");
        Usuario usuario =  new Usuario("Lionel Andres", "Messi", "usuario@usuario", passCifrada, UsuarioRole.ROLE_USER);
        usuarioRepository.save(usuario);
        passCifrada = cifrador.encode("admin");
        usuario =  new Usuario("Diego Armando", "Maradona", "admin@admin", passCifrada, UsuarioRole.ROLE_ADMIN);
        usuarioRepository.save(usuario);

    }

}