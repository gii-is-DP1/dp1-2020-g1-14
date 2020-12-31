package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Estado;
import org.springframework.samples.petclinic.model.LineaPedido;
import org.springframework.samples.petclinic.model.Pedido;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.PedidoService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=PedidoController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)

public class PedidoControllerTest {

	private static final int TEST_PEDIDO_ID = 1;
	
	@Autowired
	private PedidoController pedidoController;

	@MockBean
	private PedidoService pedidoService;

	@Autowired
	private MockMvc mockMvc;
	
	private Cliente Antonio = new Cliente();
	//private LineaPedido listPed = new LineaPedido();

	@BeforeEach
	void setup() {
		
		Pedido ped1 = new Pedido();
		ped1.setAdress("Calle X");
		ped1.setCliente(Antonio);
		ped1.setEstado(Estado.PREPARANDO);
		ped1.setId(TEST_PEDIDO_ID);
		//ped1.setLineaPedido(List<listPed>);
		ped1.setOrderDate(LocalDate.of(2020, 12, 12));
		ped1.setPrice(11.5);
		given(this.pedidoService.findPedidoById(TEST_PEDIDO_ID)).willReturn(Optional.of(ped1));
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
