package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Ingrediente;
import org.springframework.samples.petclinic.model.Medida;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class IngredienteServiceTest {

	@Autowired
	private IngredienteService ingService;
	
	@Autowired
	private RestauranteService resService;
	
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
		i.setRestaurante(resService.findRestauranteById(1).get());
		
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
		ingrediente.setStock(newStock);
		
		Medida newMedida = Medida.KG;
		ingrediente.setMedida(newMedida);
		
		//El restaurante no se cambia
		
		ingrediente = this.ingService.findIngredienteById(1).get();
		assertThat(ingrediente.getName()).isEqualTo(newName);
		assertThat(ingrediente.getStock()).isEqualTo(newStock);
		assertThat(ingrediente.getMedida()).isEqualTo(newMedida);
	}
	
	@Test
	@Transactional
	public void shouldDeleteIngrediente() {
		Set<Ingrediente> setI = new HashSet<>();
		Set<Producto> setP = new HashSet<>();
		Ingrediente i = new Ingrediente();
		i.setName("Sprite");
		i.setStock(20.);
		i.setMedida(Medida.L);
		i.setRestaurante(resService.findRestauranteById(1).get());
		setI.add(i);
		Producto p = new Producto();
		p.setName("producto");
		p.setPrecio(15.);
		p.setAlergenos("alergeno");
		p.setRestaurante(resService.findRestauranteById(1).get());
		p.setIngredientes(setI);
		setP.add(p);
		i.setProductos(setP);
		
		try {
			this.ingService.save(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Collection<Ingrediente> ingAdded = (Collection<Ingrediente>) this.ingService.findAll();
		int found = ingAdded.size();
		this.ingService.delete(i);
		Collection<Ingrediente> ingDeleted = (Collection<Ingrediente>) this.ingService.findAll();
		assertThat(ingDeleted.size()).isEqualTo(found-1);
	}
	
	@Test
	@Transactional
	public void shouldFindIngredienteWithCorrectId() {
		Optional<Ingrediente> ingrediente = this.ingService.findIngredienteById(1);
		assertThat(ingrediente.get().getName()).isEqualTo("Nata");
		assertThat(ingrediente.get().getStock()).isEqualTo(10);
		assertThat(ingrediente.get().getMedida()).isEqualTo(Medida.L);
		assertThat(ingrediente.get().getRestaurante().getId()).isEqualTo(1);
	}
	
}
