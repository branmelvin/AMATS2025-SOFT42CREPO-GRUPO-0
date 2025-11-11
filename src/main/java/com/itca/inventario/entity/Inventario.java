package com.itca.inventario.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String numeroRegistro;
    private String nombre;
    private String codigo;
    private int cantidadTotal;
    private int cantidadUtilizada;
    private int cantidadActual;
    private String tipo;
    private String areaEncargada;
    private LocalDate fechaVencimiento;

    @Column(columnDefinition = "TEXT")
    private String observacion;

    @Enumerated(EnumType.STRING)
    private Ubicacion ubicacion;

    public enum Ubicacion {
        Recursos_oficina, Consumibles_oficina, Taller_cocina, Bodega_1, Bodega_2
    }
}

