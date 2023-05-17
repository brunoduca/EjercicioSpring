package com.Egg.hola12.controladores;

import com.Egg.hola12.entidades.Autor;
import com.Egg.hola12.entidades.Editorial;
import com.Egg.hola12.entidades.Libro;
import com.Egg.hola12.excepxiones.MiException;
import com.Egg.hola12.servicios.AutorServicio;
import com.Egg.hola12.servicios.EditorialServicio;
import com.Egg.hola12.servicios.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/libro")

public class LibroControlador {

    @Autowired
    private LibroServicio libroServicio;
    @Autowired
    private AutorServicio AutorServicio;
    @Autowired
    private EditorialServicio EditorialServicio;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {
        List<Autor> autores = AutorServicio.listarAutores();
        List<Editorial> editoriales = EditorialServicio.listarEditoriales();

        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
        return "librosForm.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) Long isbn, @RequestParam String titulo,
            @RequestParam(required = false) Integer ejemplares, @RequestParam String idAutor,
            @RequestParam String idEditorial, ModelMap modelo) throws MiException {

        try {
            libroServicio.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
            modelo.put("exito", "El libro fue cargado correctamente");
        } catch (MiException ex) {
            List<Autor> autores = AutorServicio.listarAutores();
            List<Editorial> editoriales = EditorialServicio.listarEditoriales();

            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);
            modelo.put("error", ex.getMessage());

            return "librosForm.html";
        }
        return "index.html";
    }

    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        List<Libro> libros = libroServicio.listarLibros();
        modelo.addAttribute("libros", libros);
        return "libro_list.html";
    }

    @GetMapping("/modificar/ {id}")
    public String modificar(Long ISBN, ModelMap modelo){
        modelo.put("libro", libroServicio.getOne(ISBN));
        return "libro_modificar.html";
    }
   

}
