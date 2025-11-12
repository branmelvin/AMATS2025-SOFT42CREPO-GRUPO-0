package com.itca.inventario.controller;

import com.itca.inventario.entity.Categoria;
import com.itca.inventario.repository.CategoriaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaRepository categoriaRepo;

    public CategoriaController(CategoriaRepository categoriaRepo) {
        this.categoriaRepo = categoriaRepo;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaRepo.findAll());
        return "categorias/listar";
    }

    @PreAuthorize("hasRole('Administrador')")
    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categorias/formulario";
    }

    @PreAuthorize("hasRole('Administrador')")
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Categoria categoria) {
        categoriaRepo.save(categoria);
        return "redirect:/categorias";
    }

    @PreAuthorize("hasRole('Administrador')")
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("categoria", categoriaRepo.findById(id).orElse(new Categoria()));
        return "categorias/formulario";
    }

    @PreAuthorize("hasRole('Administrador')")
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        categoriaRepo.deleteById(id);
        return "redirect:/categorias";
    }
}
