
package com.Egg.hola12.repositorios;

import com.Egg.hola12.entidades.Autor;
import com.Egg.hola12.entidades.Editorial;
import com.Egg.hola12.entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro,Long> {
    
    /*@Query("SELECT l FROM Libro l WHERE l.titulo = :titulo")*/
    public Libro findByTitulo(String titulo);
    
   /* @Query("SELECT l FROM Libro l WHERE l.autor.nombre = :autor")*/
    public List<Libro>   findByAutor(Autor autor);
    
    public List<Libro>   findByEditorial(Editorial editorial);

  
}

