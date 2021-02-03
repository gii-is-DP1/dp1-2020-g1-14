package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.LineaPedido;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.service.exceptions.WrongDataProductosException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class LineaPedidoServiceTest {
	@Autowired
	private LineaPedidoService lineaPedidoService;
	@Autowired
	private ProductoService productoService;

	@Test
	public void testCountWithInitalData() {
		int count = lineaPedidoService.pedidoCount();
		assertEquals(count,5);
	}
	
	@Test
	@Transactional
	public void shouldInsertLineaPedido() throws WrongDataProductosException {
		Collection<LineaPedido> lps = (Collection<LineaPedido>) this.lineaPedidoService.findAll();
		Optional<Producto> p = this.productoService.findProductoById(5);
		int found = lps.size();
		Optional<LineaPedido> lp = lineaPedidoService.findLineaPedidoById(1);
		
	
		
		LineaPedido l = new LineaPedido();
		l.setProducto(p.get());
		l.setCantidad(5);
		
		this.lineaPedidoService.save(l);
		assertThat(lp.get().getId().longValue()).isNotEqualTo(0);
		lps = (Collection<LineaPedido>) this.lineaPedidoService.findAll();
		assertThat(lps.size()).isEqualTo(found+1);
	}
	
	@Test
	@Transactional
	public void shouldDeleteLineaPedido() throws WrongDataProductosException {
		
		Optional<Producto> p = this.productoService.findProductoById(5);
		
		LineaPedido l = new LineaPedido();
		l.setCantidad(5);
		l.setProducto(p.get());
		this.lineaPedidoService.save(l);
		
		Collection <LineaPedido> elementoAñadido = (Collection<LineaPedido>) this.lineaPedidoService.findAll(); //5
		int found = elementoAñadido.size(); //5
		this.lineaPedidoService.delete(l); //4
		Collection<LineaPedido> elementoEliminado = (Collection<LineaPedido>) this.lineaPedidoService.findAll();
		assertThat(elementoEliminado.size()).isEqualTo(found-1);
	}
	
	@Test
	@Transactional
	public void shouldFindLineaPedidoWithCorrectId() {
		Optional<LineaPedido> lp = this.lineaPedidoService.findLineaPedidoById(1);
		assertThat(lp.get().getCantidad()).isEqualTo(2);
	}
	
	@Test
	@Transactional
	public void shouldGetAllProductos() {
		List<Producto> productos = lineaPedidoService.getAllProductos();
		int ps = productoService.productoCount();
		assertThat(productos.size()).isEqualTo(ps);
	}
	
	@Test
	@Transactional
	public void shouldGetAllProductosName() {
		List<String> names = lineaPedidoService.getAllProductosName();
		assertThat(names.get(0)).isEqualTo("Tarta");
		assertThat(names.get(1)).isEqualTo("Ensalada");
		assertThat(names.get(2)).isEqualTo("Mejillones");
		assertThat(names.get(3)).isEqualTo("Cacahuetes");
		
	}
}
