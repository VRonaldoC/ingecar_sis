package com.ingecar;

import com.ingecar.entity.Usuario;
import com.ingecar.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class IngecarApplication {

    public static void main(String[] args) {
        SpringApplication.run(IngecarApplication.class, args);
    }
    
    @Bean
    CommandLineRunner initData(UsuarioService usuarioService) {
        return args -> {
            if (usuarioService.obtenerPorNombreUsuario("admin") == null) {
                Usuario admin = new Usuario();
                admin.setNombreUsuario("admin");
                admin.setPassword("admin123");
                admin.setNombreCompleto("Administrador");
                admin.setRol(Usuario.Rol.administrador);
                usuarioService.guardar(admin);
            }
        };
    }
}