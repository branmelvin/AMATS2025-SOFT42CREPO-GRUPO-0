package com.itca.inventario.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tipos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;
}
