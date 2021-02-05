package org.springframework.samples.petclinic.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Reserva;

public interface ReservaRepository extends CrudRepository<Reserva, Integer>{
	@Query("SELECT r FROM Reserva r WHERE r.restaurante.id =:restauranteId AND r.cliente.user.username=:username") 
	public Iterable<Reserva> findReservasByRestauranteIdYCliente(@Param("restauranteId") Integer restauranteId, @Param("username") String username);
	
	@Query("SELECT r FROM Reserva r WHERE r.restaurante.id =:restauranteId") 
	public Iterable<Reserva> findReservasByRestauranteId(@Param("restauranteId") Integer restauranteId);
	
	@Query("SELECT r FROM Reserva r WHERE r.cliente.id =:clienteId") 
	public Iterable<Reserva> findReservasByClienteId(@Param("clienteId") Integer clienteId);
}
