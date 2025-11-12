package com.itca.inventario.controller;

import com.itca.inventario.entity.Inventario;
import com.itca.inventario.repository.CategoriaRepository;
import com.itca.inventario.service.InventarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/inventario")
public class InventarioController {

    private final InventarioService service;
    private final CategoriaRepository categoriaRepo;

    public InventarioController(InventarioService service, CategoriaRepository categoriaRepo) {
        this.service = service;
        this.categoriaRepo = categoriaRepo;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("listaInventario", service.listar());
        return "Inventario/listar";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("inventario", new Inventario());
        model.addAttribute("categorias", categoriaRepo.findAll());
        return "Inventario/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("inventario") Inventario inv,
                          BindingResult result,
                          Model model) {
        // Si hay errores de validación, volver al formulario
        if (result.hasErrors()) {
            model.addAttribute("categorias", categoriaRepo.findAll());
            return "Inventario/formulario";
        }

        // Ajustar cantidadActual si es null
        if (inv.getCantidadActual() == null) {
            inv.setCantidadActual(0);
        }

        service.guardar(inv);
        return "redirect:/inventario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("inventario", service.buscar(id).orElse(new Inventario()));
        model.addAttribute("categorias", categoriaRepo.findAll());
        return "Inventario/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return "redirect:/inventario";
    }
}
