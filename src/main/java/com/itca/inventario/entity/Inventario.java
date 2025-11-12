package com.itca.inventario.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    /* ====== Validación avanzada: formato y longitud ====== */
    @Column(name = "numero_registro")
    @NotBlank(message = "El número de registro es obligatorio")
    @Pattern(regexp = "^[A-Z0-9-]+$", message = "El número de registro solo puede contener letras mayúsculas, números y guiones")
    @Size(min = 3, max = 20, message = "El número de registro debe tener entre 3 y 20 caracteres")
    private String numeroRegistro;

    /* ====== Validación común: campo obligatorio ====== */
    @Column(nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @Size(max = 30, message = "El código no debe superar los 30 caracteres")
    private String codigo;

    /* ====== Validaciones numéricas ====== */
    @Column(name = "cantidad_total")
    @NotNull(message = "Debe ingresar la cantidad total")
    @Min(value = 0, message = "La cantidad total no puede ser negativa")
    @Max(value = 10000, message = "La cantidad total no debe exceder los 10,000")
    private Integer cantidadTotal = 0;

    @Column(name = "cantidad_utilizada")
    @Min(value = 0, message = "La cantidad utilizada no puede ser negativa")
    private Integer cantidadUtilizada = 0;

    @Column(name = "cantidad_actual")
    @Min(value = 0, message = "La cantidad actual no puede ser negativa")
    private Integer cantidadActual = 0;

    @AssertTrue(message = "La cantidad actual no puede ser mayor que la cantidad total")
    private boolean isCantidadValida() {
        if (cantidadTotal == null || cantidadActual == null) return true;
        return cantidadActual <= cantidadTotal;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_id")
    private Tipo tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_encargada_id")
    private AreaEncargada areaEncargada;

    /* ====== Validación de fecha ====== */
    @Column(name = "fecha_vencimiento")
    @FutureOrPresent(message = "La fecha de vencimiento no puede ser anterior a la fecha actual")
    private LocalDate fechaVencimiento;

    /* ====== Validación avanzada: tamaño máximo y formato de texto ====== */
    @Column(columnDefinition = "TEXT")
    @Size(max = 300, message = "La observación no debe superar los 300 caracteres")
    private String observacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ubicacion_id")
    private Ubicacion ubicacion;

    /* ====== Relación con categoría ====== */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    @NotNull(message = "Debe seleccionar una categoría válida")
    private Categoria categoria;
}
