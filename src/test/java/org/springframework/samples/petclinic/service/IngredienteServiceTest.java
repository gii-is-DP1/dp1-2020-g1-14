package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Ingrediente;
import org.springframework.samples.petclinic.model.Medida;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class IngredienteServiceTest {

	@Autowired
	private IngredienteService ingService;
	
	@Test
	public void testCountWithInitialData() {
		int count = ingService.ingredienteCount();
		assertEquals(count,3);
	}
	
	@Test
	@Transactional
	public void shouldInsertIngrediente() {
	Collection<Ingrediente> ingredientes = (Collection<Ingrediente>) this.ingService.findAll();
		int found = ingredientes.size();

		Ingrediente i = new Ingrediente();
		i.setName("Aceite");
		i.setStock(50.);
		i.setMedida(Medida.L);
		
		try {
			this.ingService.save(i);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ingredientes = (Collection<Ingrediente>) this.ingService.findAll();
		assertThat(ingredientes.size()).isEqualTo(found+1);
	}
	
	@Test
	@Transactional
	public void shouldUpdateIngrediente() {
		Ingrediente ingrediente = this.ingService.findIngredienteById(1).get();
		
		String newName = "Avena";
		ingrediente.setName(newName);
		
		Double newStock = 43.5;
		ingrediente.setStock(newStock);;
		
		//El restaurante no se cambia
		
		ingrediente = this.ingService.findIngredienteById(1).get();
		assertThat(ingrediente.getName()).isEqualTo(newName);
		assertThat(ingrediente.getStock()).isEqualTo(newStock);
	}
}
