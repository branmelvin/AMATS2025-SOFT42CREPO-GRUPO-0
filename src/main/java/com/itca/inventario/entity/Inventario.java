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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_id")
    private Tipo tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_encargada_id")
    private AreaEncargada areaEncargada;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @Column(columnDefinition = "TEXT")
    private String observacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ubicacion_id")
    private Ubicacion ubicacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}
