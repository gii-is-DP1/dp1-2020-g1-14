package org.springframework.samples.petclinic.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Oferta;

public interface OfertaRepository extends CrudRepository<Oferta, Integer> {

	@Query("SELECT o FROM Oferta o WHERE o.restaurante.id =:restauranteId") 
	public Iterable<Oferta> findOfertasByRestauranteId(@Param("restauranteId") Integer restauranteId);
}
