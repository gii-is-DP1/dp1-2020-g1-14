package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Estado;
import org.springframework.samples.petclinic.model.LineaPedido;
import org.springframework.samples.petclinic.model.Pedido;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.service.exceptions.CantCancelOrderException;
import org.springframework.samples.petclinic.service.exceptions.MinOrderPriceException;
import org.springframework.samples.petclinic.service.exceptions.WrongDataProductosException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PedidoServiceTest {
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private LineaPedidoService lineaPedidoService;

	@Test
	public void testCountWithInitalData() {
		int count = pedidoService.pedidoCount();
		assertEquals(count,4);
	}
	
	//Test para comprobar que se inserta un pedido correctamente.
	@Test
	@Transactional
	public void shouldInsertPedido() {
		Collection<Pedido> pedidos = (Collection<Pedido>) this.pedidoService.findAll();
		int found = pedidos.size();
		Optional<Pedido> pedido=pedidoService.findPedidoById(1);
		
		Pedido p = new Pedido();
		p.setPrice(22.);
		p.setOrderDate(LocalDate.now());
		p.setAdress("Calle B");
		p.setEstado(Estado.EN_REPARTO);
		
		this.pedidoService.save(p);
		
		assertThat(pedido.get().getId().longValue()).isNotEqualTo(0);
		pedidos = (Collection<Pedido>) this.pedidoService.findAll();
		assertThat(pedidos.size()).isEqualTo(found+1);
		
	}
	
	//Test parametrizado donde se comprueba que se cancelan los pedidos dependiendo de su estado.
	@ParameterizedTest
	@Transactional
	@CsvSource({"5,PROCESANDO,12,2020-10-04,A","6,PREPARANDO,12,2020-10-05,B"})
	public void shouldCancelPedidos(Integer id, Estado estado, Double price, LocalDate orderDate, String adress) throws CantCancelOrderException {
		
		Pedido p = new Pedido();
		p.setId(id);
		p.setEstado(estado);
		p.setAdress(adress);
		p.setOrderDate(orderDate);
		p.setPrice(price);
		
	
		this.pedidoService.save(p); //5
		
		
		
		Collection <Pedido> elementoAñadido = (Collection<Pedido>) this.pedidoService.findAll(); //5
		int found = elementoAñadido.size();  //5
		this.pedidoService.delete(p); //4
		Collection<Pedido> elementoEliminado = (Collection<Pedido>) this.pedidoService.findAll();
		assertThat(elementoEliminado.size()).isEqualTo(found-1);
		                          //4            //4
		
	}
	
	//Test para encontrar un pedido por su Id.
	@Test
	@Transactional
	public void shouldFindPedidoWithCorrectId() {
		Optional<Pedido> pedido = this.pedidoService.findPedidoById(1);
		assertThat(pedido.get().getAdress()).isEqualTo("Calle A");
		assertThat(pedido.get().getEstado()).isEqualByComparingTo(Estado.PROCESANDO);
		assertThat(pedido.get().getOrderDate()).isEqualTo("2020-08-13");
		assertThat(pedido.get().getPrice()).isEqualTo(17.3);
		
	}
	
	//Test para verificar el funcionamiento de getTotalPrice, para determinar el dinero exacto a través de un pedido y sus lineas de pedido.
	@Test
	public void testGetTotalPrice() throws WrongDataProductosException, MinOrderPriceException  {
	
		Optional<Pedido> pedido = pedidoService.findPedidoById(1);
		Iterable<LineaPedido> lineasPedido = lineaPedidoService.findLineaPedidoByPedidoId(1);
		Double suma = 0.;
		for(LineaPedido l: lineasPedido) {
			Integer c = l.getCantidad();
			Double p = l.getProducto().getPrecio();
			suma += (c*p);

		}
	
		Double total = pedidoService.getTotalPrice(pedido.get().getId());
		assertThat(total).isEqualTo(suma);
		
		
	}
	
	//Test para comprobar que no se lanza la excepción de precio mínimo.
	@Test
	public void minOrderPrice() throws MinOrderPriceException {
		Double min = 10.;
		Double total = pedidoService.getTotalPriceE(1);
		assertThat(total).isGreaterThan(min);
	}
	
	//Test para comprobar que lanza la excepción de precio mínimo.
	@Test
	public void shouldThrowMinOrderPriceException() {
		Assertions.assertThrows(MinOrderPriceException.class, () -> {
    		pedidoService.getTotalPriceE(2);
    	});
	}
	
	//Test para comprobar que se obtienen todos los productos.
	@Test
	public void getAllProductsTest() {
		Collection<Producto> productos = (Collection<Producto>) pedidoService.getAllProductos();
		int tamaño = productos.size();
		Integer productosL = (int) productos.stream().count();
	
		assertThat(tamaño).isEqualTo(productosL);
	}
	

}
