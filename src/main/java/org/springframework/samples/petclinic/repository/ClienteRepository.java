package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente,Integer> {
	@Query("SELECT c FROM Cliente c WHERE c.esSocio = true") 
	public Collection<Cliente> findSocios();
	
	@Query("SELECT c FROM Cliente c WHERE c.user.username =:username") 
	public Optional<Cliente> findClienteByUsuario(@Param("username") String username);
}
