package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Ingrediente;
import org.springframework.samples.petclinic.model.Oferta;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.service.exceptions.WrongDataProductosException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ProductoServiceTest {
	@Autowired
	private ProductoService productoService;

	@Autowired
	private IngredienteService ingredienteService;
		
	@Test
	public void testCountWithInitialData() {
		int count = productoService.productoCount();
		assertEquals(count, 5);
	}
	
	//Test para comprobar que se inserta un producto correctamente.
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
			
                               
                
		this.productoService.save(p);
		assertThat(producto.get().getId().longValue()).isNotEqualTo(0);

		productos = (Collection<Producto>) this.productoService.findAll();
		assertThat(productos.size()).isEqualTo(found+1);
	}
	
	
	//Test para comprobar que se elimina un producto correctamente.
	@ParameterizedTest
	@CsvSource({"Bacalao, Pescado, 10."})
	@Transactional
	public void shouldDeleteProducto(String name, String al, Double pr) {
		Optional<Ingrediente> i = ingredienteService.findIngredienteById(1);
		Set<Ingrediente> ing= new HashSet<>();
		ing.add(i.get());
		Producto p = new Producto();
		p.setName(name);
		p.setAlergenos(al);
		p.setPrecio(pr);
		p.setIngredientes(ing);
		this.productoService.save(p);
		
		Set<Producto> pr2 = i.get().getProductos();
		pr2.add(p);
		i.get().setProductos(pr2);
		ingredienteService.save(i.get());
		
		Collection<Producto> elementoAñadido = (Collection<Producto>) this.productoService.findAll(); 
		int found = elementoAñadido.size(); 
		this.productoService.delete(p); 
		Collection<Producto> elementoEliminado = (Collection<Producto>) this.productoService.findAll();
		assertThat(elementoEliminado.size()).isEqualTo(found-1);
    	
	}
	
	//Test para comprobar que se actualiza un producto correctamente.
	@Test
	@Transactional
	public void shouldUpdateProducto() throws WrongDataProductosException {
		Optional<Producto> producto = this.productoService.findProductoById(1);
		String newName = "Bizcocho";
		producto.get().setName("Bizcocho");
		
		String oldAlergeno = producto.get().getAlergenos();
		String newAlergeno = oldAlergeno + "Gluten";
		
		producto.get().setAlergenos(newAlergeno);
		
		Double oldPrecio = producto.get().getPrecio();
		Double newPrecio= 2. + oldPrecio;
		
		producto.get().setPrecio(newPrecio);
		
		this.productoService.save(producto.get());
		producto = this.productoService.findProductoById(1);
		assertThat(producto.get().getName()).isEqualTo(newName);
		assertThat(producto.get().getAlergenos()).isEqualTo(newAlergeno);
		assertThat(producto.get().getPrecio()).isEqualTo(newPrecio);
	}
	
	//Test para comprobar que se encuentra un producto por su id
	@Test
	@Transactional
	public void shouldFindProductWithCorrectId() {
		Optional<Producto> producto = this.productoService.findProductoById(1);
		assertThat(producto.get().getName()).isEqualTo("Tarta");
		assertThat(producto.get().getAlergenos()).isEqualTo("Lacteos, Huevo y Gluten");
		assertThat(producto.get().getPrecio()).isEqualTo(6.);
	}
	
	//Test para comprobar el funcionamiento del filtro de precios por producto.
	@Test
	@Transactional
	public void shouldFindProductByPrecio() {
		Collection<Producto> productos = this.productoService.findProductoByPrecio(6.);
		assertThat(productos.size()).isEqualTo(1);
		
		productos = this.productoService.findProductoByPrecio(2.);
		assertThat(productos.isEmpty()).isTrue();
	}
	
	//Test para comprobar que se encuentra un producto por su nombre
	@Test
	@Transactional
	public void shouldFindProductoByName() {
		Collection<Producto> productos = this.productoService.findProductoByName("Tarta");
		assertThat(productos.size()).isEqualTo(1);
		
		productos= this.productoService.findProductoByName("Calamares fritos");
		assertThat(productos.isEmpty()).isTrue();
		
	}
	
	@Test
	@Transactional
	public void shouldFindProductosByRestauranteId() {
		Iterable<Producto> productos = productoService.findProductosByRestauranteId(1);
		boolean productosCorrectos=true;
		for(Producto p:productos) {
			if(!p.getRestaurante().getId().equals(1)) {
				productosCorrectos=false;
			}
		}
		assertThat(productosCorrectos).isEqualTo(true);
	}		
	}


