package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente,Integer> {
	@Query("SELECT c FROM Cliente c WHERE c.esSocio = true") 
	public Collection<Cliente> findSocios();
	
}
