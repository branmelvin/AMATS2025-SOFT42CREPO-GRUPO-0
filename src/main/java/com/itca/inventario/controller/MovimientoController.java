package com.itca.inventario.controller;

import com.itca.inventario.entity.Inventario;
import com.itca.inventario.entity.Movimiento;
import com.itca.inventario.service.InventarioService;
import com.itca.inventario.service.MovimientoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        model.addAttribute("tipos", List.of("Entrada","Salida","Ajuste"));
        return "movimientos/formulario";
    }

    @PreAuthorize("hasRole('Administrador')")
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Movimiento movimiento, @RequestParam Integer inventarioId) {
        Inventario inv = inventarioService.buscar(inventarioId).orElse(null);
        movimiento.setInventario(inv);

        // ajustar cantidades del inventario según tipo
        if (inv != null) {
            int actual = inv.getCantidadActual() == null ? 0 : inv.getCantidadActual();
            if ("Entrada".equalsIgnoreCase(movimiento.getTipo())) {
                inv.setCantidadActual(actual + movimiento.getCantidad());
            } else if ("Salida".equalsIgnoreCase(movimiento.getTipo())) {
                int nuevaCantidad = actual - movimiento.getCantidad();
                if (nuevaCantidad < 0) {
                    // Evitar stock negativo - ajustar a 0
                    nuevaCantidad = 0;
                }
                inv.setCantidadActual(nuevaCantidad);
            } else if ("Ajuste".equalsIgnoreCase(movimiento.getTipo())) {
                inv.setCantidadActual(movimiento.getCantidad());
            }
            inventarioService.guardar(inv);
        }

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
