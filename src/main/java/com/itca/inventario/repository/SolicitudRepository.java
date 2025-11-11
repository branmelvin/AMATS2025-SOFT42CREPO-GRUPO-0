package com.itca.inventario.repository;

import com.itca.inventario.model.Solicitud;
import com.itca.inventario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SolicitudRepository extends JpaRepository<Solicitud, Integer> {
    List<Solicitud> findByUsuario(Usuario usuario);
}
