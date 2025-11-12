package com.itca.inventario.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "areas_encargadas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaEncargada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;
}
