package com.itca.inventario.controller;

import com.itca.inventario.entity.Ubicacion;
import com.itca.inventario.repository.UbicacionRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ubicaciones")
public class UbicacionController {

    private final UbicacionRepository ubicacionRepository;

    public UbicacionController(UbicacionRepository ubicacionRepository) {
        this.ubicacionRepository = ubicacionRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("listaUbicaciones", ubicacionRepository.findAll());
        return "ubicaciones/listar";
    }

    @PreAuthorize("hasRole('Administrador')")
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("ubicacion", new Ubicacion());
        return "ubicaciones/formulario";
    }

    @PreAuthorize("hasRole('Administrador')")
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Ubicacion ubicacion) {
        ubicacionRepository.save(ubicacion);
        return "redirect:/ubicaciones";
    }

    @PreAuthorize("hasRole('Administrador')")
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("ubicacion", ubicacionRepository.findById(id).orElse(new Ubicacion()));
        return "ubicaciones/formulario";
    }

    @PreAuthorize("hasRole('Administrador')")
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        ubicacionRepository.deleteById(id);
        return "redirect:/ubicaciones";
    }
}
