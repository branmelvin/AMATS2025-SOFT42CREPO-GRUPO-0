package com.itca.inventario.controller;

import com.itca.inventario.entity.Tipo;
import com.itca.inventario.repository.TipoRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tipos")
public class TipoController {

    private final TipoRepository tipoRepository;

    public TipoController(TipoRepository tipoRepository) {
        this.tipoRepository = tipoRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("listaTipos", tipoRepository.findAll());
        return "tipos/listar";
    }

    @PreAuthorize("hasRole('Administrador')")
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("tipo", new Tipo());
        return "tipos/formulario";
    }

    @PreAuthorize("hasRole('Administrador')")
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Tipo tipo) {
        tipoRepository.save(tipo);
        return "redirect:/tipos";
    }

    @PreAuthorize("hasRole('Administrador')")
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("tipo", tipoRepository.findById(id).orElse(new Tipo()));
        return "tipos/formulario";
    }

    @PreAuthorize("hasRole('Administrador')")
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        tipoRepository.deleteById(id);
        return "redirect:/tipos";
    }
}
