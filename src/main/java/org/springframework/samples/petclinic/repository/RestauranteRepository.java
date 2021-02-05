package org.springframework.samples.petclinic.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Restaurante;

public interface RestauranteRepository extends CrudRepository<Restaurante, Integer>{
	
	@Query("SELECT r FROM Restaurante r WHERE r.gerente.user.username=:gerenteUser") 
	public Optional<Restaurante> findRestauranteByGerenteUser(@Param("gerenteUser") String gerenteUser);
	
}
