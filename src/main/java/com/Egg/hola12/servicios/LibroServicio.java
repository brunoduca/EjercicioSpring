package com.Egg.hola12.servicios;

import com.Egg.hola12.entidades.Autor;
import com.Egg.hola12.entidades.Editorial;
import com.Egg.hola12.entidades.Libro;
import com.Egg.hola12.excepxiones.MiException;
import com.Egg.hola12.repositorios.AutorRepositorio;
import com.Egg.hola12.repositorios.EditorialRepositorio;
import com.Egg.hola12.repositorios.LibroRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer Ejemplares, String idEditorial, String idAutor) throws MiException {
        validar(isbn, titulo, Ejemplares, idAutor, idEditorial);

        Optional<Libro> respuesta = libroRepositorio.findById(isbn);
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);
        Autor autor = new Autor();
        Editorial editorial = new Editorial();
        Libro libro = new Libro();

        if (respuestaAutor.isPresent()) {
            autor = respuestaAutor.get();
            libro.setAutor(autor);
        }

        if (respuestaEditorial.isPresent()) {
            editorial = respuestaEditorial.get();
            libro.setEditorial(editorial);
        }
        

        libro.setISBN(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(Ejemplares);
        libro.setAlta(new Date());

        libroRepositorio.save(libro);
    }

    public List<Libro> listarLibros() {
        List<Libro> libros = new ArrayList();
        libros = libroRepositorio.findAll();
        return libros;
    }

    @Transactional
    public void modificarLibro(Long ISBN, String titulo, String idAutor, String idEditorial, Integer ejemplares) {
        Optional<Libro> respuesta = libroRepositorio.findById(ISBN);
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);

        Autor autor = new Autor();
        Editorial editorial = new Editorial();

        if (respuestaAutor.isPresent()) {
            autor = respuestaAutor.get();
        }
        if (respuestaEditorial.isPresent()) {
            editorial = respuestaEditorial.get();
        }
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libro.setEjemplares(ejemplares);

            libroRepositorio.save(libro);
        }
    }
    public Libro getOne(Long ISBN){
        return libroRepositorio.getOne(ISBN);
    }

    private void validar(Long ISBN, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException {
        if (ISBN == null) {
            throw new MiException("El ISBN no puede ser nulo");
        }
        if (titulo.isEmpty()) {
            throw new MiException("Título no puede estar vacío ni ser nulo.");
        }
        if (ejemplares == null) {
            throw new MiException("Los ejemplares no pueden ser nulos");
        }
        if (idEditorial.isEmpty()) {
            throw new MiException("El ID de la editorial no puede ser nula ni estar vacía");
        }
        if (idAutor.isEmpty()) {
            throw new MiException("El ID de Autor no puede estar vacío o ser nulo");
        }
    }
}
