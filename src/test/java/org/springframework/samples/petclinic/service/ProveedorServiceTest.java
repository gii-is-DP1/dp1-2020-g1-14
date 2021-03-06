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
import org.springframework.samples.petclinic.model.Proveedor;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ProveedorServiceTest {
	@Autowired
	private ProveedorService proveedorService;
	@Autowired
	private IngredienteService ingredienteService;
	@Autowired
	private RestauranteService restauranteService;
	
	@Test
	public void testCountWithInitialData() {
		int count = proveedorService.proveedorCount();
		assertEquals(count, 3);
	}
	
	//Test para comprobar que se inserta un proveedor.
	@Test
	@Transactional
	public void shouldInsertProveedor() {
	Collection<Proveedor> proveedores = (Collection<Proveedor>) this.proveedorService.findAll();
		int found = proveedores.size();
		Optional<Proveedor> proveedor=proveedorService.findProveedorById(1);

		Proveedor p = new Proveedor();
		p.setName("Juan");
		p.setTlf("954873612");
		
			
                               
                
		this.proveedorService.save(p);
		assertThat(proveedor.get().getId().longValue()).isNotEqualTo(0);

		proveedores = (Collection<Proveedor>) this.proveedorService.findAll();
		assertThat(proveedores.size()).isEqualTo(found+1);
	}
	
	//Test para comprobar que se elimina un proveedor.
	
	@Test
	@Transactional
	public void shouldDeleteProveedor() {
		Ingrediente i = ingredienteService.findIngredienteById(1).get();
		Restaurante r = restauranteService.findRestauranteById(1).get();
		Set<Restaurante> res = new HashSet<>();
		res.add(r);
		Set<Ingrediente> is = new HashSet<>();
		is.add(i);
		Proveedor p = new Proveedor();
		p.setName("Pepe Gomez");
		p.setTlf("954484848");
		p.setIngredientes(is);
		p.setRestaurantes(res);
		this.proveedorService.save(p);
	
		Collection<Proveedor> elementoAñadido = (Collection<Proveedor>) this.proveedorService.findAll();
		int found = elementoAñadido.size();
		this.proveedorService.delete(p);
		Collection<Proveedor> elementoEliminado = (Collection<Proveedor>) this.proveedorService.findAll();
		assertThat(elementoEliminado.size()).isEqualTo(found-1);
	}
	
	//Test para comprobar que se encuentra un proveedor correctamente por su id.
	public void shouldFindProveedorWithCorrectId() {
		Optional<Proveedor> proveedor = this.proveedorService.findProveedorById(1);
		assertThat(proveedor.get().getName()).isEqualTo("Database");
		assertThat(proveedor.get().getTlf()).isEqualTo("649983623");
	}
	
	//Test para comprobar que se actualiza un proveedor correctamente.
	@Test
	@Transactional
	public void shouldUpdateProveedor() {
		Optional<Proveedor> proveedor = this.proveedorService.findProveedorById(1);
		String newName = "Paco Pepe";
		proveedor.get().setName(newName);
		
		String newTlf = "954345485";
		proveedor.get().setTlf(newTlf);
		
		this.proveedorService.save(proveedor.get());
		proveedor = this.proveedorService.findProveedorById(1);
		assertThat(proveedor.get().getName()).isEqualTo(newName);
		assertThat(proveedor.get().getTlf()).isEqualTo(newTlf);
	}
}
