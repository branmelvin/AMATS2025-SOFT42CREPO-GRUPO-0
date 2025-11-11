package com.itca.inventario.service;

import com.itca.inventario.entity.Usuario;
import com.itca.inventario.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository repo;
    private final PasswordEncoder encoder;

    public UsuarioService(UsuarioRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public Usuario register(Usuario u) {
        if (repo.findByUsuario(u.getUsuario()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }
        u.setContrasena(encoder.encode(u.getContrasena()));
        return repo.save(u);
    }

    public Optional<Usuario> findByUsuario(String usuario) { return repo.findByUsuario(usuario); }

    public java.util.List<Usuario> listar() { return repo.findAll(); }

    public void eliminar(Integer id) { repo.deleteById(id); }
}
