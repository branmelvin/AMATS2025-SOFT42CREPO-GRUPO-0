package com.itca.inventario.controller;

import com.itca.inventario.service.InventarioService;
import com.itca.inventario.service.MovimientoService;
import com.itca.inventario.service.ReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportController {

    private final InventarioService inventarioService;
    private final MovimientoService movimientoService;
    private final ReportService reportService;

    public ReportController(InventarioService inventarioService, MovimientoService movimientoService, ReportService reportService) {
        this.inventarioService = inventarioService;
        this.movimientoService = movimientoService;
        this.reportService = reportService;
    }

    @GetMapping("/reports/inventario")
    public ResponseEntity<byte[]> exportInventario() throws Exception {
        var data = reportService.inventarioToExcel(inventarioService.listar());
        byte[] bytes = data.readAllBytes();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=inventario.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(bytes);
    }

    @GetMapping("/reports/movimientos")
    public ResponseEntity<byte[]> exportMovimientos() throws Exception {
        var data = reportService.movimientosToExcel(movimientoService.listar());
        byte[] bytes = data.readAllBytes();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=movimientos.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(bytes);
    }
}
