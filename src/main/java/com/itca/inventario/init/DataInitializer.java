package com.itca.inventario.init;

import com.itca.inventario.entity.Usuario;
import com.itca.inventario.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
        return args -> {
            if (usuarioRepository.findByUsuario("admin").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setNombre("Administrador");
                admin.setUsuario("admin");
                admin.setContrasena(encoder.encode("admin")); // contraseña inicial: admin
                admin.setRol("Administrador");
                usuarioRepository.save(admin);
                System.out.println("Usuario admin creado: usuario=admin , contraseña=admin");
            }
        };
    }
}
