package org.springframework.samples.petclinic.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Reserva;

public interface ReservaRepository extends CrudRepository<Reserva, Integer>{
	
	/*@Query("SELECT r FROM Reserva r WHERE r.restaurante_id=:restaurante")
	public Iterable<Reserva> findByRestaurante(@Param("restaurante") int restaurante);*/

}
