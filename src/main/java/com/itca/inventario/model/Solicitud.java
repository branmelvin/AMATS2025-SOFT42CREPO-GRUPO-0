package com.itca.inventario.model;

import com.itca.inventario.entity.Inventario;
import com.itca.inventario.entity.Usuario;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "solicitudes")
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_inventario", nullable = false)
    private Inventario inventario;

    private int cantidad;

    private String motivo;

    private LocalDateTime fechaSolicitud = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private EstadoSolicitud estado = EstadoSolicitud.Pendiente;

    public enum EstadoSolicitud {
        Pendiente, Aprobada, Rechazada
    }
}
