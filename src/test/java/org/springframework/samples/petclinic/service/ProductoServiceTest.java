package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.service.exceptions.WrongDataProductosException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ProductoServiceTest {
	@Autowired
	private ProductoService productoService;
	
	@Test
	public void testCountWithInitialData() {
		int count = productoService.productoCount();
		assertEquals(count, 3);
	}
	
	@Test
	@Transactional
	public void shouldInsertProducto() {
	Collection<Producto> productos = (Collection<Producto>) this.productoService.findAll();
		int found = productos.size();
		Optional<Producto> producto=productoService.findProductoById(1);

		Producto p = new Producto();
		p.setName("Tortilla");
		p.setAlergenos("Huevos");
		p.setPrecio(1.);
			
                               
                
		try {
			this.productoService.save(p);
		} catch (WrongDataProductosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertThat(producto.get().getId().longValue()).isNotEqualTo(0);

		productos = (Collection<Producto>) this.productoService.findAll();
		assertThat(productos.size()).isEqualTo(found+1);
	}
	@Test
	@Transactional
	public void shouldThrowWrongDataProductosException() {
		
		Producto p = new Producto();
		p.setName("ab");
		p.setAlergenos("");
		p.setPrecio(0.);
			
                               
                
		try {
			this.productoService.save(p);
		} catch (WrongDataProductosException e) {
			//Wrong data!
			e.printStackTrace();
		}
		Assertions.assertThrows(WrongDataProductosException.class, ()->{
			this.productoService.save(p);
		});
	}

	@Test
	@Transactional
	public void shouldUpdateProducto() {
		Optional<Producto> producto = this.productoService.findProductoById(1);
		String newName = "Bizcocho";
		
		producto.get().setName("Bizcocho");
		
		String oldAlergeno = producto.get().getAlergenos();
		String newAlergeno = oldAlergeno + "Gluten";
		
		producto.get().setAlergenos(newAlergeno);
		
		Double oldPrecio = producto.get().getPrecio();
		Double newPrecio= 2. + oldPrecio;
		
		producto.get().setPrecio(newPrecio);
		
		producto = this.productoService.findProductoById(1);
		assertThat(producto.get().getName()).isEqualTo(newName);
		assertThat(producto.get().getAlergenos()).isEqualTo(newAlergeno);
		assertThat(producto.get().getPrecio()).isEqualTo(newPrecio);
	}
	@Test
	@Transactional
	public void shouldFindProductWithCorrectId() {
		Optional<Producto> producto = this.productoService.findProductoById(1);
		assertThat(producto.get().getName()).isEqualTo("Tarta");
		assertThat(producto.get().getAlergenos()).isEqualTo("Lacteos, Huevo y Gluten");
		assertThat(producto.get().getPrecio()).isEqualTo(6.);
	}
	@Test
	@Transactional
	public void shouldFindProductByPrecio() {
		Collection<Producto> productos = this.productoService.findProductoByPrecio(6.);
		assertThat(productos.size()).isEqualTo(1);
		
		productos = this.productoService.findProductoByPrecio(2.);
		assertThat(productos.isEmpty()).isTrue();
	}
	@Test
	@Transactional
	public void shouldFindProductoByName() {
		Collection<Producto> productos = this.productoService.findProductoByName("Tarta");
		assertThat(productos.size()).isEqualTo(1);
		
		productos= this.productoService.findProductoByName("Calamares fritos");
		assertThat(productos.isEmpty()).isTrue();
		
	}

}
