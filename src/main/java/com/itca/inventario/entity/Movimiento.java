package com.itca.inventario.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @NotNull(message = "El inventario asociado es obligatorio.")
    private Inventario inventario;

    @Column(name = "tipo", nullable = false, length = 20)
    @NotBlank(message = "El tipo de movimiento es obligatorio.")
    @Pattern(
        regexp = "^(Entrada|Salida|Ajuste)$",
        message = "El tipo debe ser 'Entrada', 'Salida' o 'Ajuste'."
    )
    private String tipo;

    @NotNull(message = "La cantidad es obligatoria.")
    @Positive(message = "La cantidad debe ser mayor que cero.")
    private Integer cantidad;

    @Column(nullable = false)
    @PastOrPresent(message = "La fecha no puede ser futura.")
    private LocalDateTime fecha = LocalDateTime.now();

    @NotBlank(message = "El responsable es obligatorio.")
    @Size(min = 3, max = 100, message = "El nombre del responsable debe tener entre 3 y 100 caracteres.")
    private String responsable;

    @Column(columnDefinition = "TEXT")
    @Size(max = 500, message = "La observación no puede superar los 500 caracteres.")
    private String observacion;
}
