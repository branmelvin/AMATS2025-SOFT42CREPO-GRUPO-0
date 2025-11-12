package com.itca.inventario.controller;

import com.itca.inventario.entity.AreaEncargada;
import com.itca.inventario.repository.AreaEncargadaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/areas-encargadas")
public class AreaEncargadaController {

    private final AreaEncargadaRepository areaEncargadaRepository;

    public AreaEncargadaController(AreaEncargadaRepository areaEncargadaRepository) {
        this.areaEncargadaRepository = areaEncargadaRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("listaAreasEncargadas", areaEncargadaRepository.findAll());
        return "areas-encargadas/listar";
    }

    @PreAuthorize("hasRole('Administrador')")
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("areaEncargada", new AreaEncargada());
        return "areas-encargadas/formulario";
    }

    @PreAuthorize("hasRole('Administrador')")
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute AreaEncargada areaEncargada) {
        areaEncargadaRepository.save(areaEncargada);
        return "redirect:/areas-encargadas";
    }

    @PreAuthorize("hasRole('Administrador')")
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("areaEncargada", areaEncargadaRepository.findById(id).orElse(new AreaEncargada()));
        return "areas-encargadas/formulario";
    }

    @PreAuthorize("hasRole('Administrador')")
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        areaEncargadaRepository.deleteById(id);
        return "redirect:/areas-encargadas";
    }
}
