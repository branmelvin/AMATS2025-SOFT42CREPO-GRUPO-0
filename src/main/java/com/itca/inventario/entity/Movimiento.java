package com.itca.inventario.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimientos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_inventario", nullable = false)
    private Inventario inventario;

    @Column(name = "tipo")
    private String tipo; // Entrada | Salida | Ajuste

    private Integer cantidad;

    private LocalDateTime fecha = LocalDateTime.now();

    private String responsable;

    @Column(columnDefinition = "TEXT")
    private String observacion;
}
