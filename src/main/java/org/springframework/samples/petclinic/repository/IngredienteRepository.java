package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Ingrediente;

public interface IngredienteRepository extends CrudRepository<Ingrediente, Integer>{

}
