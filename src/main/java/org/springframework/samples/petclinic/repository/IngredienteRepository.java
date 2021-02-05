package org.springframework.samples.petclinic.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Ingrediente;

public interface IngredienteRepository extends CrudRepository<Ingrediente, Integer>{
	
	@Query("SELECT i FROM Ingrediente i WHERE i.restaurante.id =:restauranteId") 
	public Iterable<Ingrediente> findIngredientesByRestauranteId(@Param("restauranteId") Integer restauranteId);
}
