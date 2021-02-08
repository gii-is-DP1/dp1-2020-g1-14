
package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Estado;
import org.springframework.samples.petclinic.model.LineaPedido;
import org.springframework.samples.petclinic.model.Pedido;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.LineaPedidoService;
import org.springframework.samples.petclinic.service.OfertaService;
import org.springframework.samples.petclinic.service.PedidoService;
import org.springframework.samples.petclinic.service.ProductoService;
import org.springframework.samples.petclinic.service.RestauranteService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers = {PedidoController.class, CustomErrorController.class},
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)


public class PedidoControllerTests {

	private static final int TEST_PEDIDO_ID = 1;
	private static final String TEST_USERNAME = "cliente1";
	private static final int TEST_RESTAURANTE_ID = 1;
	private static final int TEST_LINEAPEDIDO_ID = 1;
	private static final int TEST_PRODUCTO_ID = 1;
	
	@Autowired
	private PedidoController pedidoController;

	@MockBean
	private PedidoService pedidoService;
	
	@MockBean
	private ClienteService clienteService;
	
	@MockBean
	private UserService userService;
	        
	@MockBean
	private AuthoritiesService authoritiesService; 
	
	@MockBean
	private RestauranteService restauranteService;
	
	@MockBean
	private LineaPedidoService lineaPedidoService;
	
	@MockBean
	private ProductoService productoService;
	
	@MockBean
	private OfertaService ofertaService;

	@Autowired
	private MockMvc mockMvc;
	
	private Cliente cliente1;
	private Pedido ped1;
	private User user1;
	private Restaurante restaurante;
	private LineaPedido lineaPedido;
	private Producto tarta;
	private Authorities authorities;


	@BeforeEach
	void setup() {
	
		authorities = new Authorities();
		authorities.setId(10);
		authorities.setUser(user1);
		authorities.setAuthority("cliente");
		
		user1=new User();
		user1.setEnabled(true);
		user1.setPassword("cliente1");
		user1.setrDate(LocalDate.of(2020, 01, 01));
		user1.setUsername("cliente1");
		user1.setAuthorities(authorities);
		
		cliente1 = new Cliente();
		cliente1.setEsSocio(false);
		cliente1.setNumPedidos(12);
		cliente1.setTlf("954765812");
		cliente1.setMonedero(300.);
		cliente1.setUser(user1);
		
		restaurante = new Restaurante();
		restaurante.setId(TEST_RESTAURANTE_ID);
		restaurante.setName("Restaurante 1");
		restaurante.setTipo("Chino");
		restaurante.setLocalizacion("Reina Mercedes, 34");
		restaurante.setAforomax(25);
		restaurante.setSenial(20);
		
		lineaPedido = new LineaPedido();
		lineaPedido.setId(TEST_LINEAPEDIDO_ID);
		lineaPedido.setCantidad(2);
		lineaPedido.setPedido(ped1);
		lineaPedido.setProducto(tarta);
		
		ped1 = new Pedido();
		ped1.setId(TEST_PEDIDO_ID);
		ped1.setAdress("Calle A");
		ped1.setCheckea(true);
		ped1.setEstado(Estado.PROCESANDO);
		ped1.setOrderDate(LocalDate.of(2020, 8, 13));
		ped1.setPrice(17.3);
		ped1.setCliente(cliente1);
		ped1.setRestaurante(restaurante);
		
		tarta = new Producto();
		tarta.setAlergenos("Lacteos, Huevo y Gluten");
		tarta.setId(TEST_PRODUCTO_ID);
		tarta.setName("Tarta");
		tarta.setPrecio(6.0);
		tarta.setRestaurante(restaurante);
		
		given(this.productoService.findProductoById(TEST_PRODUCTO_ID)).willReturn(Optional.of(tarta));
		given(this.pedidoService.findPedidoById(TEST_PEDIDO_ID)).willReturn(Optional.of(ped1));
		given(this.userService.findUser(user1.getUsername())).willReturn(Optional.of(user1));
		given(this.restauranteService.findRestauranteById(TEST_RESTAURANTE_ID)).willReturn(Optional.of(restaurante));
		given(this.lineaPedidoService.findLineaPedidoById(TEST_LINEAPEDIDO_ID)).willReturn(Optional.of(lineaPedido));
		given(this.clienteService.findClienteByUsuario(user1.getUsername())).willReturn(Optional.of(cliente1));
	}
	
	
	//TEST LISTAR OFERTAS
	@WithMockUser(authorities = "cliente",username = "cliente1", password = "cliente1")
	@Test
	void testListadoPedidos() throws Exception {
		mockMvc.perform(get("/restaurantes/{restauranteId}/pedidos/{username}", TEST_RESTAURANTE_ID,TEST_USERNAME))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("pedidos"))
		.andExpect(view().name("pedidos/listadoPedidos"));
	}	
}
