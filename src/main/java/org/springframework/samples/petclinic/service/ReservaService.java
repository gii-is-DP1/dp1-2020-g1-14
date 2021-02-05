package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Reserva;
import org.springframework.samples.petclinic.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReservaService {
	
	@Autowired
	private ReservaRepository ReservaRepo;
	
	@Transactional
	public int Reservascount() {
		return (int) ReservaRepo.count();
	}
	
	@Transactional
	public Iterable<Reserva> findAll() {
		log.info("obtención de todos las reservas");
		return ReservaRepo.findAll();
	}
	
	@Transactional(readOnly=true)
	public Optional<Reserva>findReservaById(int id){
		log.info("obtención de reserva en concreto");
		return ReservaRepo.findById(id);
	}
	
	@Transactional(readOnly = true)
	public Iterable<Reserva> findReservasByRestauranteIdYCliente(Integer restauranteId, String username) {

		log.info("Devolviendo reservas de un restaurante y usuario indicado");
		return ReservaRepo.findReservasByRestauranteIdYCliente(restauranteId, username);
	}
	
	@Transactional(readOnly = true)
	public Iterable<Reserva> findReservasByRestauranteId(Integer restauranteId) {

		log.info("Devolviendo reservas de un restaurante");
		return ReservaRepo.findReservasByRestauranteId(restauranteId);
	}
	
	@Transactional(readOnly = true)
	public Iterable<Reserva> findReservasByClienteId(Integer clienteId) {

		log.info("Devolviendo reservas de un cliente");
		return ReservaRepo.findReservasByClienteId(clienteId);
	}
	
	@Transactional
	public void save(Reserva reserva) {
		ReservaRepo.save(reserva);
		log.info("reserva guardada");
	}
	
	@Transactional
	public void delete(Reserva reserva) {
		ReservaRepo.delete(reserva);
		log.info("reserva eliminada");
	}
}
