package com.diplomado.tienda.service.impl;

import com.diplomado.tienda.exception.CategoriaNoEncontradaException;
import com.diplomado.tienda.model.Categoria;
import com.diplomado.tienda.repository.CategoriaRepository;
import com.diplomado.tienda.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> obtenerTodasLasCategorias() {
        log.info("\n 📂 Buscando todas las categorías... \n");

        List<Categoria> categorias = categoriaRepository.findAll();

        if (categorias.isEmpty()) {
            log.warn("\n ⚠️ No se encontraron categorías en la base de datos. \n");
            throw new CategoriaNoEncontradaException("No se encontraron categorías.");
        }

        log.info("\n ✅ Se encontraron {} categorías. \n", categorias.size());
        return categorias;
    }

    @Override
    public Categoria crearCategoria(Categoria categoria) {
        log.info("\n 📂 Creando categoría... \n");

        Categoria nuevaCategoria = categoriaRepository.save(categoria);

        log.info("\n ✅ Categoría creada con éxito: {} \n", nuevaCategoria);
        return nuevaCategoria;
    }

    @Override
    public Categoria obtenerPorId(Integer id) {
        log.info("\n 📂 Buscando categoría por ID: {}... \n", id);

        return categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNoEncontradaException("Categoría no encontrada con ID: " + id));
    }

    @Override
    public Categoria actualizarCategoria(Integer id, Categoria nuevaCategoria) {
        log.info("\n 📂 Actualizando categoría con ID: {}... \n", id);

        Categoria categoriaExistente = obtenerPorId(id);
        categoriaExistente.setNombre(nuevaCategoria.getNombre());
        categoriaExistente.setDescripcion(nuevaCategoria.getDescripcion());

        Categoria categoriaActualizada = categoriaRepository.save(categoriaExistente);

        log.info("\n ✅ Categoría actualizada con éxito: {} \n", categoriaActualizada);

        return categoriaActualizada;
    }

    @Override
    public void eliminarCategoria(Integer id) {
        log.info("\n 📂 Eliminando categoría con ID: {}... \n", id);

        Categoria categoriaExistente = obtenerPorId(id);
        categoriaRepository.delete(categoriaExistente);

        log.info("\n ✅ Categoría eliminada con éxito: {} \n", categoriaExistente);
    }
}
