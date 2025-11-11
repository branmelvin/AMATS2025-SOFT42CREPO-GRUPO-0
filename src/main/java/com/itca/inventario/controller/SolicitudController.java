package com.itca.inventario.controller;

import com.itca.inventario.model.Solicitud;
import com.itca.inventario.entity.Usuario;
import com.itca.inventario.repository.InventarioRepository;
import com.itca.inventario.repository.SolicitudRepository;
import com.itca.inventario.repository.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudController {

    private final SolicitudRepository solicitudRepo;
    private final InventarioRepository inventarioRepo;
    private final UsuarioRepository usuarioRepo;

    public SolicitudController(SolicitudRepository solicitudRepo, InventarioRepository inventarioRepo, UsuarioRepository usuarioRepo) {
        this.solicitudRepo = solicitudRepo;
        this.inventarioRepo = inventarioRepo;
        this.usuarioRepo = usuarioRepo;
    }

    @GetMapping
    public String listar(Model model, Authentication auth) {
        Usuario usuarioActual = usuarioRepo.findByUsuario(auth.getName()).orElse(null);
        boolean esAdmin = usuarioActual != null && usuarioActual.getRol().equalsIgnoreCase("Administrador");

        if (esAdmin) {
            model.addAttribute("solicitudes", solicitudRepo.findAll());
        } else {
            model.addAttribute("solicitudes", solicitudRepo.findByUsuario(usuarioActual));
        }

        model.addAttribute("esAdmin", esAdmin);
        return "solicitudes/listar";
    }

    @GetMapping("/nueva")
    public String nueva(Model model, Authentication auth) {
        Usuario usuarioActual = usuarioRepo.findByUsuario(auth.getName()).orElse(null);

        Solicitud solicitud = new Solicitud();
        solicitud.setUsuario(usuarioActual);

        model.addAttribute("solicitud", solicitud);
        model.addAttribute("inventarios", inventarioRepo.findAll());

        // Mapa de stock disponible por inventario
        Map<Integer, Integer> stockMap = new HashMap<>();
        inventarioRepo.findAll().forEach(i -> stockMap.put(i.getId(), i.getCantidadActual() != null ? i.getCantidadActual() : 0));
        model.addAttribute("stockMap", stockMap);

        return "solicitudes/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Solicitud solicitud, Authentication auth) {
        Usuario usuario = usuarioRepo.findByUsuario(auth.getName()).orElse(null);
        solicitud.setUsuario(usuario);
        solicitudRepo.save(solicitud);
        return "redirect:/solicitudes";
    }

    @GetMapping("/aprobar/{id}")
    public String aprobar(@PathVariable Integer id) {
        solicitudRepo.findById(id).ifPresent(s -> {
            s.setEstado(Solicitud.EstadoSolicitud.Aprobada);
            solicitudRepo.save(s);
        });
        return "redirect:/solicitudes";
    }

    @GetMapping("/rechazar/{id}")
    public String rechazar(@PathVariable Integer id) {
        solicitudRepo.findById(id).ifPresent(s -> {
            s.setEstado(Solicitud.EstadoSolicitud.Rechazada);
            solicitudRepo.save(s);
        });
        return "redirect:/solicitudes";
    }
}
