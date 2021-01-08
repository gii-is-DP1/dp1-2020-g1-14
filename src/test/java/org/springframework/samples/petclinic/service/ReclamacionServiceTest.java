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
import org.springframework.samples.petclinic.model.Reclamacion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ReclamacionServiceTest {

	@Autowired
	private ReclamacionService reclamacionService;
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Test
	public void testCountWithInitialData() {
		int count = reclamacionService.reclamacionCount();
		assertEquals(count, 2);
	}
	
	@Test
	@Transactional
	public void shouldFindReclamacionWithCorrectId() {
		Optional<Reclamacion> reclamacion = this.reclamacionService.findReclamacionById(1);
		assertThat(reclamacion.get().getFecha()).isEqualTo("2020-10-28");
		assertThat(reclamacion.get().getDescripcion()).isEqualTo("Mal trato por parte del camarero.");
		assertThat(reclamacion.get().getRestaurante().getId()).isEqualTo(1);
		
	}
	
	
	@Test
	@Transactional
	public void shouldInsertReclamacion() {
		Collection<Reclamacion> reclamaciones = (Collection<Reclamacion>) this.reclamacionService.findAll();
		int found = reclamaciones.size();

		Reclamacion r = new Reclamacion();
		r.setDescripcion("Se ha servido la comida fría.");
		r.setRestaurante(restauranteService.findRestauranteById(1).get());
		int i = 0;
		if(r.getFecha().equals(LocalDate.now())) {
			i = 1;
		}
		assertEquals(i,1);
			                              
    
		this.reclamacionService.save(r);

	
		reclamaciones = (Collection<Reclamacion>) this.reclamacionService.findAll();
		assertThat(reclamaciones.size()).isEqualTo(found+1);
	}
	
	
}
