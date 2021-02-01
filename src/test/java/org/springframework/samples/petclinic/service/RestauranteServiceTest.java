package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Proveedor;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class RestauranteServiceTest {
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Test
	public void testCountWithInitialData() {
		int count = restauranteService.Restaurantscount();
		assertEquals(count, 3);
	}
	
	@Test
	void shouldFindRestauranteById() {
		Optional<Restaurante> restaurante = restauranteService.findRestauranteById(2);
		assertThat(restaurante.get().getId()).isEqualTo(2);
	}
	
	@Test
	@Transactional
	public void shouldInsertRestaurante() {
		Collection<Restaurante> restaurantes = (Collection<Restaurante>) this.restauranteService.findAll();
		int found = restaurantes.size();
		Optional<Restaurante> restaurante=restauranteService.findRestauranteById(1);
		Restaurante nuevoRestaurante = new Restaurante();
		nuevoRestaurante.setName("Restaurante de Prueba");
		nuevoRestaurante.setTipo("Mexicano");
		nuevoRestaurante.setLocalizacion("Calle de prueba");
		nuevoRestaurante.setAforomax(25);
		//nuevoRestaurante.setAforores(nuevoRestaurante.getAforomax());       
        this.restauranteService.save(nuevoRestaurante);
		assertThat(restaurante.get().getId().longValue()).isNotEqualTo(0);

		restaurantes = (Collection<Restaurante>) this.restauranteService.findAll();
		assertThat(restaurantes.size()).isEqualTo(found+1);
	}
	
	//Test con el cual no se inserta un nuevo restaurante porque el res > max
	

}
