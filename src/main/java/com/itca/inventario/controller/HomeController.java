package com.itca.inventario.controller;

import com.itca.inventario.repository.InventarioRepository;
import com.itca.inventario.repository.MovimientoRepository;
import com.itca.inventario.repository.SolicitudRepository;
import com.itca.inventario.service.InventarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final InventarioRepository inventarioRepo;
    private final MovimientoRepository movimientoRepo;
    private final SolicitudRepository solicitudRepo;
    private final InventarioService inventarioService;

    public HomeController(InventarioRepository inventarioRepo, MovimientoRepository movimientoRepo,
                         SolicitudRepository solicitudRepo, InventarioService inventarioService) {
        this.inventarioRepo = inventarioRepo;
        this.movimientoRepo = movimientoRepo;
        this.solicitudRepo = solicitudRepo;
        this.inventarioService = inventarioService;
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("appName", "Inventario ITCA");

        // Estadísticas para el dashboard
        model.addAttribute("totalItems", inventarioRepo.count());
        model.addAttribute("lowStockAlerts", inventarioService.obtenerBajoStock().size());
        model.addAttribute("pendingSolicitudes", solicitudRepo.findAll().stream()
                .filter(s -> s.getEstado().name().equals("Pendiente")).count());
        model.addAttribute("recentMovements", movimientoRepo.count());

        return "dashboard";
    }

    @GetMapping("/login")
    public String login() { return "login"; }
}
