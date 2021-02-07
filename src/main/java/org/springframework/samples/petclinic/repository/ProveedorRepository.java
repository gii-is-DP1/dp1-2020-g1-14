package org.springframework.samples.petclinic.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Proveedor;

public interface ProveedorRepository extends CrudRepository<Proveedor, Integer>{

//	@Query("SELECT p FROM Proveedor p WHERE p.restaurante.id =:restauranteId") 
//	public Iterable<Proveedor> findProveedoresByRestauranteId(@Param("restauranteId") Integer restauranteId);
}
