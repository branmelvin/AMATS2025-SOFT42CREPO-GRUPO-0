package com.itca.inventario.security;

import com.itca.inventario.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Configuration
public class WebSecurityConfig {

    private final UsuarioRepository usuarioRepository;

    public WebSecurityConfig(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> usuarioRepository.findByUsuario(username)
                .map(u -> {
                    // Agrega el prefijo ROLE_ requerido por Spring Security
                    String role = "ROLE_" + (u.getRol() == null ? "Encargado" : u.getRol());
                    return new User(
                            u.getUsuario(),
                            u.getContrasena(),
                            List.of(new SimpleGrantedAuthority(role))
                    );
                })
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        .requestMatchers("/usuarios/**").hasRole("Administrador")
                        .requestMatchers("/inventario/**", "/tipos/**", "/areas-encargadas/**", "/ubicaciones/**", "/categorias/**", "/movimientos/**", "/reportes/**").hasAnyRole("Administrador", "Encargado")
                        .requestMatchers("/solicitudes/**").hasAnyRole("Administrador", "Encargado", "Docente")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
