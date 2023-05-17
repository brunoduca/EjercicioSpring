package com.Egg.hola12.servicios;

import com.Egg.hola12.entidades.Editorial;
import com.Egg.hola12.excepxiones.MiException;
import com.Egg.hola12.repositorios.EditorialRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialServicio {

    @Autowired
    EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearEditorial(String nombre) throws MiException {
        verificar(nombre, nombre);
        Editorial editorial = new Editorial();

        editorial.setNombre(nombre);
        editorialRepositorio.save(editorial);
    }

    public List<Editorial> listarEditoriales() {
        List<Editorial> editoriales = new ArrayList();
        editoriales = editorialRepositorio.findAll();
        return editoriales;
    }

    @Transactional
    public void modificarEditorial(String idEditorial, String nombre) throws MiException {
        verificar(idEditorial, nombre);
        Editorial editorial = new Editorial();
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);

        if (respuestaEditorial.isPresent()) {
            editorial.setNombre(nombre);
            editorialRepositorio.save(editorial);
        }

    }
    
    public Editorial getOne(String id){
        return editorialRepositorio.getOne(id);
    }
    
    private void verificar(String idEditorial, String nombre) throws MiException{
        if(idEditorial.isEmpty() || idEditorial== null){
            throw new MiException("El id de la Editorial no puede ser nulo ni estar vacío.");
        }
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede estar vacío ni ser nulo");
            
        }
    }

}
