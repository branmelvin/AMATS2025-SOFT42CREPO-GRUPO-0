package com.itca.inventario.service;

import com.itca.inventario.entity.Inventario;
import com.itca.inventario.entity.Movimiento;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReportService {

    public ByteArrayInputStream inventarioToExcel(List<Inventario> inventarios) throws Exception {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Inventario");
            Row header = sheet.createRow(0);
            String[] cols = {"ID","Nombre","Código","Cantidad Actual","Cantidad Total","Área","Tipo","Ubicación","Observación"};
            for (int i=0;i<cols.length;i++) header.createCell(i).setCellValue(cols[i]);

            int rowIdx = 1;
            for (Inventario inv: inventarios) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(inv.getId());
                row.createCell(1).setCellValue(inv.getNombre());
                row.createCell(2).setCellValue(inv.getCodigo() == null ? "" : inv.getCodigo());
                row.createCell(3).setCellValue(inv.getCantidadActual() == null ? 0 : inv.getCantidadActual());
                row.createCell(4).setCellValue(inv.getCantidadTotal() == null ? 0 : inv.getCantidadTotal());
                row.createCell(5).setCellValue(inv.getAreaEncargada() == null ? "" : inv.getAreaEncargada().getNombre());
                row.createCell(6).setCellValue(inv.getTipo() == null ? "" : inv.getTipo().getNombre());
                row.createCell(7).setCellValue(inv.getUbicacion() == null ? "" : inv.getUbicacion().getNombre());
                row.createCell(8).setCellValue(inv.getObservacion() == null ? "" : inv.getObservacion());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    public ByteArrayInputStream movimientosToExcel(List<Movimiento> movimientos) throws Exception {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Movimientos");
            Row header = sheet.createRow(0);
            String[] cols = {"ID","Inventario","Tipo","Cantidad","Fecha","Responsable","Observación"};
            for (int i=0;i<cols.length;i++) header.createCell(i).setCellValue(cols[i]);

            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            int rowIdx = 1;
            for (Movimiento m: movimientos) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(m.getId());
                row.createCell(1).setCellValue(m.getInventario() != null ? m.getInventario().getNombre() : "");
                row.createCell(2).setCellValue(m.getTipo());
                row.createCell(3).setCellValue(m.getCantidad());
                row.createCell(4).setCellValue(m.getFecha() != null ? m.getFecha().format(fmt) : "");
                row.createCell(5).setCellValue(m.getResponsable());
                row.createCell(6).setCellValue(m.getObservacion());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
