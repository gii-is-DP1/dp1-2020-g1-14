package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Estado;
import org.springframework.samples.petclinic.model.Pedido;
import org.springframework.samples.petclinic.service.exceptions.CantCancelOrderException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PedidoServiceTest {
	
	@Autowired
	private PedidoService pedidoService;
	
	@Test
	public void testCountWithInitalData() {
		int count = pedidoService.pedidoCount();
		assertEquals(count,4);
	}
	@ParameterizedTest
	@Transactional
	@CsvSource({"1,PROCESANDO","2,PREPARANDO"})
	public void shouldCancelPedidos(Integer id, Estado estado) throws CantCancelOrderException {
		Collection<Pedido> pedidos = (Collection<Pedido>) this.pedidoService.findAll();
		int found = pedidos.size();
		
		Pedido p = new Pedido();
		p.setId(id);
		p.setEstado(estado);
		
		this.pedidoService.delete(p);
		
		pedidos = (Collection<Pedido>) this.pedidoService.findAll();
		assertThat(pedidos.size()).isEqualTo(found-1);
		
		
	}
}
