package com.itca.inventario.repository;

import com.itca.inventario.entity.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InventarioRepository extends JpaRepository<Inventario, Integer> {
    List<Inventario> findByNombreContainingIgnoreCase(String nombre);
}
