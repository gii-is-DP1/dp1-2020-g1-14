package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Ingrediente;

public interface IngredienteRepository extends CrudRepository<Ingrediente, Integer>{

	/*@Query("SELECT ingrediente FROM Ingrediente ingrediente WHERE id=:restaurante_id")
	public Collection<Ingrediente> findByRestaurante(@Param("id") int id);*/
}
