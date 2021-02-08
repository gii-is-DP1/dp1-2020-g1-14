package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Repository;

public interface UserRepository extends  CrudRepository<User, String>{
	
}
