package org.springframework.samples.petclinic.web;

import static org.hamcrest.Matchers.hasProperty;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.PedidoService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=PedidoController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)

public class PedidoControllerTest {

	private static final int TEST_PEDIDO_ID = 1;
	private static final int TEST_CLIENTE_ID = 1;
	
	@Autowired
	private PedidoController pedidoController;

	@MockBean
	private PedidoService pedidoService;
	
	@MockBean
	private ClienteService clienteService;

	@Autowired
	private MockMvc mockMvc;
	
	private Cliente juan;
	private Pedido ped1;
	

	@BeforeEach
	void setup() {
	
		juan = new Cliente();
		juan.setEsSocio(true);
		juan.setId(TEST_CLIENTE_ID);
		juan.setNumPedidos(12);
		juan.getUser().setPassword("pass1");
		juan.getUser().setrDate(LocalDate.of(2000, 10, 11));
		juan.setTlf("954765812");
		
		Pedido ped1 = new Pedido();
		ped1.setAdress("Calle A");
		ped1.setCliente(juan);
		ped1.setEstado(Estado.PROCESANDO);
		ped1.setId(TEST_PEDIDO_ID);
		ped1.setOrderDate(LocalDate.of(2020, 8, 13));
		ped1.setPrice(17.3);
		
		given(this.pedidoService.findPedidoById(TEST_PEDIDO_ID)).willReturn(Optional.of(ped1));
		given(this.clienteService.findClienteById(TEST_CLIENTE_ID)).willReturn(Optional.of(juan));
		
	}
	
	//CREATION TESTS
	
	@WithMockUser(value = "spring")
    @Test
    void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/pedidos/new", TEST_PEDIDO_ID)).andExpect(status().isOk())
			.andExpect(view().name("pedidos/nuevoPedido"));
}

	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/pedidos/new")
						.with(csrf())
						.param("adress", "Calle X")
						.param("orderDate", "2020-12-27")
						.param("price", "18.3"))
			.andExpect(status().is2xxSuccessful());
}

		
	
	//TEST LISTAR PEDIDOS
		@WithMockUser(value = "spring")
	    @Test
	    void testListadoPedidos() throws Exception {
			mockMvc.perform(get("/pedidos")).andExpect(status().isOk()).andExpect(model().attributeExists("pedidos"))
			.andExpect(status().is2xxSuccessful()).andExpect(view().name("pedidos/listadoPedidos"));
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
