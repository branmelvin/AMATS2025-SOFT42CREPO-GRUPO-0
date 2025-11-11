package com.itca.inventario.service;

import com.itca.inventario.entity.Inventario;
import com.itca.inventario.repository.InventarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {
    private final InventarioRepository repo;
    public InventarioService(InventarioRepository repo){ this.repo = repo; }
    public List<Inventario> listar(){ return repo.findAll(); }
    public Optional<Inventario> buscar(Integer id){ return repo.findById(id); }
    public Inventario guardar(Inventario i){ return repo.save(i); }
    public void eliminar(Integer id){ repo.deleteById(id); }
    public List<Inventario> buscarPorNombre(String q){ return repo.findByNombreContainingIgnoreCase(q); }
}
