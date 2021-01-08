package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Estado;
import org.springframework.samples.petclinic.model.Pedido;
import org.springframework.samples.petclinic.model.Reserva;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.service.exceptions.CantCancelOrderException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ReservaServiceTest {
	
	@Autowired
	private ReservaService reservaService;
	@Autowired
	private RestauranteService restauranteService;
	
	@Test
	public void testCountWithInitialData() {
		int count = reservaService.Reservascount();
		assertEquals(count, 3);
	}
	
	@Test
	void shouldFindReservaById() {
		Optional<Reserva> reserva = reservaService.findReservaById(2);
		assertThat(reserva.get().getId()).isEqualTo(2);
	}
	
	@Test
	@Transactional
	public void shouldInsertReserva() {
		Collection<Reserva> reservas = (Collection<Reserva>) this.reservaService.findAll();
		int found = reservas.size();
		Optional<Reserva> reserva=reservaService.findReservaById(1);
		Reserva nuevaReserva = new Reserva();
		nuevaReserva.setFecha(LocalDate.now());
		nuevaReserva.setHoraInicio(LocalTime.now());
		nuevaReserva.setHoraFin(LocalTime.of(22, 00));
		nuevaReserva.setnPersonas(12);
		nuevaReserva.setEvento(true);      
		nuevaReserva.setRestaurante(restauranteService.findRestauranteById(1).get());
        this.reservaService.save(nuevaReserva);
		assertThat(reserva.get().getId().longValue()).isNotEqualTo(0);

		reservas = (Collection<Reserva>) this.reservaService.findAll();
		assertThat(reservas.size()).isEqualTo(found+1);
	}
	
	@ParameterizedTest
	@Transactional
	@CsvSource({"4,2020-04-23,12:00,13:00,false,5"/*,1"*/,"5,2020-04-23,12:00,13:00,false,13"/*,1"*/,"6,2020-04-23,12:00,13:00,true,13"/*,1"*/})
	public void shouldCreateReserva(Integer id, LocalDate fecha, LocalTime horaI, LocalTime horaF, Boolean evento, Integer nPersonas/*, Integer idRes*/) {
		
		//Restaurante res = new Restaurante();
		//this.restauranteService.save(res);
		Reserva r = new Reserva();
		r.setId(id);
		r.setFecha(fecha);
		r.setHoraInicio(horaI);
		r.setHoraFin(horaF);
		r.setnPersonas(nPersonas);
		r.setEvento(evento);
		//r.setRestaurante(res);
		
		
		Collection<Reserva> reservas = (Collection<Reserva>) this.reservaService.findAll();
		int found= reservas.size();
		this.reservaService.save(r);
		Collection<Reserva> elementoAñadido = (Collection<Reserva>) this.reservaService.findAll();
		assertThat(elementoAñadido.size()).isEqualTo(found+1);
		
		
	}
	
	@ParameterizedTest
	@Transactional
	@CsvSource({"4,2020-04-23,12:00,13:00,true,5"/*,1"*/,"5,2020-04-23,14:00,13:00,false,5"/*,1"*/})
	public void shouldNotCreateReserva(Integer id, LocalDate fecha, LocalTime horaI, LocalTime horaF, Boolean evento, Integer nPersonas/*, Integer idRes*/) {
		
		//Restaurante res = new Restaurante();
		//this.restauranteService.save(res);
		Reserva r = new Reserva();
		r.setId(id);
		r.setFecha(fecha);
		r.setHoraInicio(horaI);
		r.setHoraFin(horaF);
		r.setnPersonas(nPersonas);
		r.setEvento(evento);
		//r.setRestaurante(res);
		
		
		Collection<Reserva> reservas = (Collection<Reserva>) this.reservaService.findAll();
		int found= reservas.size();
		this.reservaService.save(r);
		Collection<Reserva> elementoAñadido = (Collection<Reserva>) this.reservaService.findAll();
		assertThat(elementoAñadido.size()).isEqualTo(found);
		
		
	}
	
}
