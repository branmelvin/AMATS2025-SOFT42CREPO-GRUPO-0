package com.itca.inventario.service;

import com.itca.inventario.entity.Movimiento;
import com.itca.inventario.repository.MovimientoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MovimientoService {
    private final MovimientoRepository repo;
    public MovimientoService(MovimientoRepository repo){ this.repo = repo; }
    public Movimiento guardar(Movimiento m){ return repo.save(m); }
    public List<Movimiento> listar(){ return repo.findAll(); }
    public void eliminar(Integer id){ repo.deleteById(id); }
}
