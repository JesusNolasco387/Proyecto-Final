package com.diplomado.tienda.service;

import com.diplomado.tienda.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {

    List<Categoria> obtenerTodasLasCategorias();
    Categoria crearCategoria(Categoria categoria);
    Categoria obtenerPorId(Integer id);
    Categoria actualizarCategoria(Integer id, Categoria nuevaCategoria);
    void eliminarCategoria(Integer id);

}
