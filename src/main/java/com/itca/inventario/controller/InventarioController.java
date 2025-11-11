package com.itca.inventario.controller;

import com.itca.inventario.entity.Inventario;
import com.itca.inventario.service.InventarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/inventario")
public class InventarioController {

    private final InventarioService service;

    public InventarioController(InventarioService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("listaInventario", service.listar());
        return "inventario/listar";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("inventario", new Inventario());
        return "inventario/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Inventario inv) {
        service.guardar(inv);
        return "redirect:/inventario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("inventario", service.buscarPorId(id).orElse(new Inventario()));
        return "inventario/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return "redirect:/inventario";
    }
}
