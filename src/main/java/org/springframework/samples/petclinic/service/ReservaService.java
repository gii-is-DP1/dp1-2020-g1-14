package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Reserva;
import org.springframework.samples.petclinic.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		return ReservaRepo.findAll();
	}
	
	@Transactional(readOnly=true)
	public Optional<Reserva>findRestauranteById(int id){
		return ReservaRepo.findById(id);
	}
	
	@Transactional
	public void save(Reserva reserva) {
		ReservaRepo.save(reserva);
	}
	
	@Transactional
	public void delete(Reserva reserva) {
		ReservaRepo.delete(reserva);
		
	}
}
