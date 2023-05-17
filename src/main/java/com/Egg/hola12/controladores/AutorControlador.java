package com.Egg.hola12.controladores;

import com.Egg.hola12.entidades.Autor;
import com.Egg.hola12.excepxiones.MiException;
import com.Egg.hola12.servicios.AutorServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/autor")
public class AutorControlador {

    @Autowired
    private AutorServicio AutorServicio;

    @GetMapping("/registrar")
    public String registrar() {
        return "autorForn.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo) throws MiException { //El atributo que llega a esta función tiene el mismo nombre que llega a nuestro input.

        try {
            AutorServicio.crearAutor(nombre);
            modelo.put("exito", "El autor fue cargado correctamente");
        } catch (MiException ex) {
            modelo.put("error", "El autor no ha sifo cargado correctamente");
            return "autorForm.html";
        }
        return "index.html";
    }

    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        List<Autor> autores = AutorServicio.listarAutores();
        modelo.addAttribute("autores", autores);

        return "autor_list.html";

    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("autor", AutorServicio.getOne(id));
        return "autor_modificar.html";
    }

    @PostMapping("modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo) {
        try {
            AutorServicio.modificarAutor(nombre, id);
            return "redirect../lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "autor_modificar.html";
        }
    }
}
