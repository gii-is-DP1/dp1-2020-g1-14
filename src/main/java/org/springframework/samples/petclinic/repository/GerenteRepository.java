package org.springframework.samples.petclinic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Gerente;

public interface GerenteRepository extends CrudRepository<Gerente,Integer>{

	@Query("SELECT g FROM Gerente g WHERE g.user.username =:username") 
	public Optional<Gerente> findGerenteByUsuario(@Param("username") String username);
}
