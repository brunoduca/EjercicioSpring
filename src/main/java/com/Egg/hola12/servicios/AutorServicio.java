package com.Egg.hola12.servicios;

import com.Egg.hola12.entidades.Autor;
import com.Egg.hola12.excepxiones.MiException;
import com.Egg.hola12.repositorios.AutorRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServicio {
    
    @Autowired
    AutorRepositorio autorRepositorio;
    
    @Transactional
    public void crearAutor(String nombre) throws MiException{
        verificar(nombre, nombre);
        Autor autor = new Autor();
        
        autor.setNombre(nombre);
        
        autorRepositorio.save(autor);
        
    }
    
    public List<Autor> listarAutores() {
        List<Autor> autores = new ArrayList();
        autores = autorRepositorio.findAll();
        return autores;
    }
    
    @Transactional
    public void modificarAutor(String nombre, String id) throws MiException {
        verificar(nombre, id);
        Autor autor = new Autor();
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            autor.setNombre(nombre);
            autorRepositorio.save(autor);
        }
        
    }
    
    public Autor getOne(String id){
        return autorRepositorio.getOne(id);
    }
    
    private void verificar(String nombre, String id) throws MiException {
        if(nombre.isEmpty() || nombre == null){
            throw new MiException("El nombre no puede estar vacío ni ser nulo");
        }
        if(id.isEmpty() || id == null){
            throw new MiException("El ID no puede estar vacío ni ser nulo");
        }
    }
}
