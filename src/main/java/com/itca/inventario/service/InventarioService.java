package com.itca.inventario.service;

import com.itca.inventario.entity.Inventario;
import com.itca.inventario.repository.InventarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventarioService {
    private final InventarioRepository repo;
    public InventarioService(InventarioRepository repo){ this.repo = repo; }
    public List<Inventario> listar(){ return repo.findAll(); }
    public Optional<Inventario> buscar(Integer id){ return repo.findById(id); }
    public Inventario guardar(Inventario i){ return repo.save(i); }
    public void eliminar(Integer id){ repo.deleteById(id); }
    public List<Inventario> buscarPorNombre(String q){ return repo.findByNombreContainingIgnoreCase(q); }

    // Método para obtener items con bajo stock (menos de 5 unidades)
    public List<Inventario> obtenerBajoStock() {
        return repo.findAll().stream()
                .filter(i -> i.getCantidadActual() != null && i.getCantidadActual() < 5)
                .collect(Collectors.toList());
    }
}
