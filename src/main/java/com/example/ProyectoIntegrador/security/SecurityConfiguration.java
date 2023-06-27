package com.example.ProyectoIntegrador.security;

import com.example.ProyectoIntegrador.persistance.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired private RestAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/odontologo.html", "/turnos.html", "/pacientes.html", "/alta_turnos.html", "/alta_pacientes.html", "/alta_odontologos.html", "/index.html")
                .hasRole("ADMIN") // Solo los usuarios con el rol "ADMIN" pueden acceder a estas URLs
                .antMatchers("/index.html")
                .hasAnyRole("USER", "ADMIN") // Los usuarios con los roles "USER" o "ADMIN" pueden acceder a esta URL
                .anyRequest()
                .hasRole("ADMIN") // Los usuarios con el rol "ADMIN" tienen permiso para acceder a todas las demás URLs
                .and()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .formLogin();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(usuarioService);
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        return provider;
    }


}
