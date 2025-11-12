package com.itca.inventario.controller;

import com.itca.inventario.entity.Categoria;
import com.itca.inventario.repository.CategoriaRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categorias/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Categoria categoria, BindingResult result, Model model) {
        // Si hay errores de validación, volver al formulario
        if (result.hasErrors()) {
            return "categorias/formulario";
        }
        
        try {
            categoriaRepo.save(categoria);
            return "redirect:/categorias";
        } catch (Exception e) {
            // Manejar errores de base de datos (ej: nombre duplicado)
            model.addAttribute("error", "Error al guardar la categoría: " + e.getMessage());
            return "categorias/formulario";
        }
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("categoria", categoriaRepo.findById(id).orElse(new Categoria()));
        return "categorias/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        categoriaRepo.deleteById(id);
        return "redirect:/categorias";
    }
}
