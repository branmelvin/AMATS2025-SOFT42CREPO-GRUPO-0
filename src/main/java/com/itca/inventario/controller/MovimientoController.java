package com.itca.inventario.controller;

import com.itca.inventario.entity.Inventario;
import com.itca.inventario.entity.Movimiento;
import com.itca.inventario.service.InventarioService;
import com.itca.inventario.service.MovimientoService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/movimientos")
public class MovimientoController {

    private final MovimientoService movimientoService;
    private final InventarioService inventarioService;

    public MovimientoController(MovimientoService movimientoService, InventarioService inventarioService) {
        this.movimientoService = movimientoService;
        this.inventarioService = inventarioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("lista", movimientoService.listar());
        return "movimientos/listar";
    }

    @PreAuthorize("hasRole('Administrador')")
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("movimiento", new Movimiento());
        model.addAttribute("inventarios", inventarioService.listar());
        model.addAttribute("tipos", List.of("Entrada", "Salida", "Ajuste"));
        return "movimientos/formulario";
    }

    @PreAuthorize("hasRole('Administrador')")
    @PostMapping("/guardar")
    public String guardar(
            @Valid @ModelAttribute Movimiento movimiento,
            BindingResult result,
            @RequestParam Integer inventarioId,
            Model model
    ) {
        // Verifica errores de validación
        if (result.hasErrors()) {
            model.addAttribute("inventarios", inventarioService.listar());
            model.addAttribute("tipos", List.of("Entrada", "Salida", "Ajuste"));
            return "movimientos/formulario";
        }

        // Busca el inventario seleccionado
        Inventario inv = inventarioService.buscar(inventarioId).orElse(null);
        if (inv == null) {
            result.rejectValue("inventario", "error.movimiento", "Debe seleccionar un inventario válido.");
            model.addAttribute("inventarios", inventarioService.listar());
            model.addAttribute("tipos", List.of("Entrada", "Salida", "Ajuste"));
            return "movimientos/formulario";
        }

        movimiento.setInventario(inv);

        // Ajusta las cantidades del inventario según el tipo
        int actual = inv.getCantidadActual() == null ? 0 : inv.getCantidadActual();
        switch (movimiento.getTipo()) {
            case "Entrada" -> inv.setCantidadActual(actual + movimiento.getCantidad());
            case "Salida" -> {
                int nuevaCantidad = actual - movimiento.getCantidad();
                inv.setCantidadActual(Math.max(nuevaCantidad, 0)); // Evitar stock negativo
            }
            case "Ajuste" -> inv.setCantidadActual(movimiento.getCantidad());
        }

        inventarioService.guardar(inv);
        movimientoService.guardar(movimiento);

        return "redirect:/movimientos";
    }

    @PreAuthorize("hasRole('Administrador')")
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        movimientoService.eliminar(id);
        return "redirect:/movimientos";
    }
}
