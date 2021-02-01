package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Gerente;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class GerenteServiceTest {
	
	@Autowired
	private GerenteService gerenteService;
	@Autowired
	private RestauranteService restauranteService;
	
	@Test
	public void testCountWithInitialData() {
		int count = gerenteService.gerenteCount();
		assertEquals(count,2);
	}
	
	@Test
	@Transactional
	public void shouldInsertGerente() {
	Collection<Gerente> gerentes = (Collection<Gerente>) this.gerenteService.findAll();
		int found = gerentes.size();

		Gerente g = new Gerente();
		g.setName("Pable");
		g.getUser().setPassword("contras1eñaTest");
		g.getUser().setrDate(LocalDate.now());
		g.setDni("34596703H");
		g.setRestaurante(restauranteService.findRestauranteById(1).get());
		
		try {
			this.gerenteService.save(g);
		} catch (Exception e) {
			e.printStackTrace();
		}

		gerentes = (Collection<Gerente>) this.gerenteService.findAll();
		assertThat(gerentes.size()).isEqualTo(found+1);
	}
	
	@Test
	@Transactional
	public void shouldUpdateGerente() {
		Gerente gerente = this.gerenteService.findGerenteById(1).get();
		String newName = "Emilio";
		gerente.setName("Emilio");
		
		String newPassword = "alfkh34as";
		gerente.getUser().setPassword(newPassword);
		
		gerente = this.gerenteService.findGerenteById(1).get();
		assertThat(gerente.getName()).isEqualTo(newName);
		assertThat(gerente.getUser().getPassword()).isEqualTo(newPassword);
	}
	
	@Test
	@Transactional
	public void shouldDeleteGerente() {
		Gerente g = new Gerente();
		g.setName("Juan");
		g.getUser().setPassword("contraseña2");
		g.getUser().setrDate(LocalDate.now());
		g.setDni("34788543H");
		g.setRestaurante(restauranteService.findRestauranteById(2).get());
		
		try {
			this.gerenteService.save(g);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Collection<Gerente> gerenteAdded = (Collection<Gerente>) this.gerenteService.findAll();
		int found = gerenteAdded.size();
		this.gerenteService.delete(g);
		Collection<Gerente> gerenteDeleted = (Collection<Gerente>) this.gerenteService.findAll();
		assertThat(gerenteDeleted.size()).isEqualTo(found-1);
	}
	
	 @Test
	 @Transactional
	 public void shouldFindGerenteWithCorrectId() {
		 Optional<Gerente> gerente = this.gerenteService.findGerenteById(1);
		 assertThat(gerente.get().getDni()).isEqualTo("12345678F");
		 assertThat(gerente.get().getRestaurante().getId()).isEqualTo(1);
	 }
	
}
