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

            if (usuarioService.listarTodos().isEmpty()) {
                Usuario admin = new Usuario();
                admin.setNombreUsuario("admin");
                admin.setContraseña("admin123");
                admin.setNombreCompleto("Administrador del Sistema");
                admin.setRol(Usuario.Rol.administrador); 
                admin.setEmail("admin@ingecar.com");
                admin.setTelefono("999888777");
                
                usuarioService.guardar(admin);
                System.out.println("Usuario administrador creado: admin / admin123");
                

                Usuario tecnico = new Usuario();
                tecnico.setNombreUsuario("tecnico1");
                tecnico.setContraseña("tecnico123");
                tecnico.setNombreCompleto("Carlos Rodríguez");
                tecnico.setRol(Usuario.Rol.tecnico); 
                tecnico.setEmail("tecnico@ingecar.com");
                
                usuarioService.guardar(tecnico);
                System.out.println("Usuario técnico creado: tecnico1 / tecnico123");
                

                Usuario recepcionista = new Usuario();
                recepcionista.setNombreUsuario("recepcion");
                recepcionista.setContraseña("recepcion123");
                recepcionista.setNombreCompleto("Ana García");
                recepcionista.setRol(Usuario.Rol.recepcionista); 
                recepcionista.setEmail("recepcion@ingecar.com");
                
                usuarioService.guardar(recepcionista);
                System.out.println("Usuario recepcionista creado: recepcion / recepcion123");
            }
        };
    }
}