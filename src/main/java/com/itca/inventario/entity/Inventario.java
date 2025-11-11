package com.itca.inventario.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "inventario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "numero_registro")
    private String numeroRegistro;

    @Column(nullable = false)
    private String nombre;

    private String codigo;

    @Column(name = "cantidad_total")
    private Integer cantidadTotal = 0;

    @Column(name = "cantidad_utilizada")
    private Integer cantidadUtilizada = 0;

    @Column(name = "cantidad_actual")
    private Integer cantidadActual = 0;

    private String tipo;
    @Column(name = "area_encargada")
    private String areaEncargada;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @Column(columnDefinition = "TEXT")
    private String observacion;

    @Column(name = "ubicacion")
    private String ubicacion;
}
