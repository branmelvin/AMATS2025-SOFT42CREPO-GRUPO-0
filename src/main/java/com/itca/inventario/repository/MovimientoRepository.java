package com.itca.inventario.repository;

import com.itca.inventario.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento,Integer> {
    List<Movimiento> findByInventarioIdOrderByFechaDesc(Integer inventarioId);
}
