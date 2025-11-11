package com.itca.inventario.controller;

import com.itca.inventario.entity.Usuario;
import com.itca.inventario.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) { this.usuarioService = usuarioService; }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioService.listar());
        return "Usuarios/listar";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", java.util.List.of("Administrador","Encargado","Docente"));
        return "Usuarios/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam("nombre") String nombre,
                         @RequestParam("usuario") String usuarioLogin,
                         @RequestParam("contrasena") String contrasena,
                         @RequestParam("confirmarContrasena") String confirmarContrasena,
                         @RequestParam("rol") String rol,
                         Model model) {
        try {
            if (!contrasena.equals(confirmarContrasena)) {
                throw new RuntimeException("Las contraseñas no coinciden");
            }

            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setUsuario(usuarioLogin);
            usuario.setContrasena(contrasena);
            usuario.setRol(rol);

            usuarioService.register(usuario);
            return "redirect:/usuarios";
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar el usuario: " + e.getMessage());
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setUsuario(usuarioLogin);
            usuario.setRol(rol);
            model.addAttribute("usuario", usuario);
            model.addAttribute("roles", java.util.List.of("Administrador","Encargado","Docente"));
            return "Usuarios/formulario";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        usuarioService.eliminar(id);
        return "redirect:/usuarios";
    }
}
