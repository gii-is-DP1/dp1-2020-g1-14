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
import org.springframework.samples.petclinic.model.Pedido;
import org.springframework.samples.petclinic.service.exceptions.CantCancelOrderException;
import org.springframework.samples.petclinic.service.exceptions.MinOrderPriceException;
import org.springframework.samples.petclinic.service.exceptions.WrongDataProductosException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PedidoServiceTest {
	
	@Autowired
	private PedidoService pedidoService;
	private ProductoService productoService;
	private LineaPedidoService lineaPedidoService;	
	@Test
	public void testCountWithInitalData() {
		int count = pedidoService.pedidoCount();
		assertEquals(count,4);
	}
	
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
		int found = elementoAñadido.size(); //5
		this.pedidoService.delete(p); //4
		Collection<Pedido> elementoEliminado = (Collection<Pedido>) this.pedidoService.findAll();
		assertThat(elementoEliminado.size()).isEqualTo(found-1);
		            //5                 //3
		
	}

	@Test
	public void testGetTotalPrice() throws WrongDataProductosException, MinOrderPriceException  {
		/*
		Pedido p = new Pedido();
		p.setId(99);
		
		
		p.setAdress("A");
		p.setOrderDate(LocalDate.now());
		
		Producto pr1 = new Producto();
		pr1.setId(99);
		pr1.setPrecio(6.);
		
		
		Producto pr2 = new Producto();
		pr2.setId(100);
		pr2.setPrecio(8.);
		
	
		LineaPedido lp1 = new LineaPedido();
		lp1.setId(99);
		lp1.setCantidad(2);
		lp1.setProducto(pr1);
		lp1.setPedido(p);
	
		LineaPedido lp2 = new LineaPedido();
		lp2.setId(100);
		lp2.setCantidad(3);
		lp2.setProducto(pr2);
		lp2.setPedido(p);
		
		List<LineaPedido> lineas = new ArrayList<>();
		lineas.add(lp1); lineas.add(lp2);
		
		p.setLineaPedido(lineas);
		p.setPrice(pr1.getPrecio()*lp1.getCantidad()+pr2.getPrecio()*lp2.getCantidad());
		pr1.setLineaPedido(lineas);
		pr2.setLineaPedido(lineas);
		
		lineaPedidoService.save(lp1);
		lineaPedidoService.save(lp2);
		pedidoService.save(p);
		productoService.save(pr1);
		productoService.save(pr2);
		*/
		Optional<Pedido> pedido = pedidoService.findPedidoById(1);
		Integer c1 =pedido.get().getLineaPedido().get(0).getCantidad();
		Integer c2 =pedido.get().getLineaPedido().get(1).getCantidad();
		
		Double p1=pedido.get().getLineaPedido().get(0).getProducto().getPrecio();
		Double p2=pedido.get().getLineaPedido().get(1).getProducto().getPrecio();
		
		
		Double total = pedidoService.getTotalPrice(pedido.get().getId());
		assertThat(total).isEqualTo(c1*p1+c2*p2);
	}
	
	@Test
	public void minOrderPrice() throws MinOrderPriceException {
		Double min = 10.;
		Double total = pedidoService.getTotalPrice(1);
		assertThat(total).isGreaterThan(min);
	}
	@Test
	public void shouldThrowMinOrderPriceException() {
		Assertions.assertThrows(MinOrderPriceException.class, () -> {
    		pedidoService.getTotalPrice(2);
    	});
	}

}
