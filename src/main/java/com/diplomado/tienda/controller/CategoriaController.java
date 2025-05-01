package com.diplomado.tienda.controller;

import com.diplomado.tienda.model.Categoria;
import com.diplomado.tienda.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/categorias")
public class CategoriaController {


    private final CategoriaService categoriaService;

    //mostrar formulario
    @GetMapping("/nueva")
    public String mostrarFormularioNuevaCategoria(Model model) {
        model.addAttribute("categorias", new Categoria());
        return "admin/form-categoria";
    }

    //guardar categoria
    @PostMapping("/guardar")
    public String guardarCategoria(Categoria categoria) {
        categoriaService.crearCategoria(categoria);
        return "redirect:/categorias/lista";
    }

    // Mostrar la lista de categorías
    @GetMapping("/lista")
    public String listarCategorias(Model model) {
        model.addAttribute("categorias", categoriaService.obtenerTodasLasCategorias());
        return "admin/listaCategorias";
    }

    // Mostrar formulario de edición
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarCategoria(@PathVariable("id") Integer id, Model model) {
        Categoria categoria = categoriaService.obtenerPorId(id);
        model.addAttribute("categorias", categoria);
        return "admin/form-categoria";
    }

    // Actualizar categoría existente
    @PostMapping("/actualizar/{id}")
    public String actualizarCategoria(@PathVariable("id") Integer id, @ModelAttribute Categoria categoria) {
        categoriaService.actualizarCategoria(id, categoria);
        return "redirect:/categorias/lista";
    }

    // Eliminar categoría
    @GetMapping("/eliminar/{id}")
    public String eliminarCategoria(@PathVariable("id") Integer id) {
        categoriaService.eliminarCategoria(id);
        return "redirect:/categorias/lista";
    }

}
