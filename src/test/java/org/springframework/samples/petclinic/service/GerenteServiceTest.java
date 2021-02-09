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
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Gerente;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class GerenteServiceTest {
	
	@Autowired
	private GerenteService gerenteService;
	
	@Autowired
	private UserService userService;
	
	@Test
	public void testCountWithInitialData() {
		int count = gerenteService.gerenteCount();
		assertEquals(count,3);
	}
	
	@Test
	@Transactional
	public void shouldInsertGerente() {
	Collection<Gerente> gerentes = (Collection<Gerente>) this.gerenteService.findAll();
		int found = gerentes.size();


		User s = new User();
		s.setUsername("Pable_gerente");
		s.setPassword("contras1eñaTest");
		s.setEnabled(true);
		s.setrDate(LocalDate.now());
		userService.saveUser(s);
		
		Gerente g = new Gerente();
		g.setUser(s);
		g.setName("Pable");
		g.setDni("34596703H");
		
		try {
			this.gerenteService.save(g);
		} catch (Exception e) {
			e.printStackTrace();
		}

		gerentes = (Collection<Gerente>) this.gerenteService.findAll();
		assertThat(gerentes.size()).isEqualTo(found+1);
	}
	

	
//	@Test
//	@Transactional
//	public void shouldDeleteGerente() {
//		
//		
//		User s = new User();
//		s.setUsername("Juan_gerente");
//		s.setPassword("contrasenya2");
//		s.setEnabled(true);
//		s.setrDate(LocalDate.now());
//		userService.saveUser(s);
//		
//		Gerente g = new Gerente();
//		g.setUser(s);
//		g.setName("Juan");
//		g.setDni("34788543G");
//		
//		try {
//			this.gerenteService.save(g);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		Collection<Gerente> gerentes = (Collection<Gerente>) this.gerenteService.findAll();
//		int found = gerentes.size();
//		this.gerenteService.delete(g);
//		Collection<Gerente> gerenteDeleted = (Collection<Gerente>) this.gerenteService.findAll();
//		assertThat(gerenteDeleted.size()).isEqualTo(found-1);
//	}
	
	 @Test
	 @Transactional
	 public void shouldFindGerenteWithCorrectId() {
		 Optional<Gerente> gerente = this.gerenteService.findGerenteById(1);
		 assertThat(gerente.get().getDni()).isEqualTo("12345678F");
	 }
	
	@Test
	@Transactional
	public void shouldFindGerenteByUsuario() {
		Optional<Gerente> gerente = gerenteService.findGerenteByUsuario("gerente3");
		boolean gerenteCorrecto=true;
		if(!gerente.get().getUser().getUsername().equals("gerente3")) {
			gerenteCorrecto=false;
		}
			
			assertThat(gerenteCorrecto).isEqualTo(true);
		}
	 
}
