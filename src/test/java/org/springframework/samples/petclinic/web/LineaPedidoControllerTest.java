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
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Estado;
import org.springframework.samples.petclinic.model.LineaPedido;
import org.springframework.samples.petclinic.model.Pedido;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.service.LineaPedidoService;
import org.springframework.samples.petclinic.service.PedidoService;
import org.springframework.samples.petclinic.service.ProductoService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = LineaPedidoController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)


public class LineaPedidoControllerTest {

private static final int TEST_LINEAPEDIDO_ID = 1;
private static final int TEST_PEDIDO_ID = 1;
private static final int TEST_PRODUCTO_ID = 1;
	
	@Autowired
	private LineaPedidoController lineaPedidoController;

	@MockBean
	private LineaPedidoService lineaPedidoService;
	
	@MockBean
	private PedidoService pedidoService;
	
	@MockBean
	private ProductoService productoService;

	@Autowired
	private MockMvc mockMvc;
	
	private LineaPedido lineaPedido;
	private Pedido ped1;
	private Producto tarta;
	
	@BeforeEach
	void setup() {
		
		Pedido ped1 = new Pedido();
		ped1.setAdress("Calle A");
		ped1.setEstado(Estado.PROCESANDO);
		ped1.setId(TEST_PEDIDO_ID);
		ped1.setOrderDate(LocalDate.of(2020, 8, 13));
		ped1.setPrice(17.3);
		
		tarta = new Producto();
		tarta.setAlergenos("Lacteos, Huevo y Gluten");
		tarta.setId(TEST_PRODUCTO_ID);
		tarta.setName("Tarta");
		tarta.setPrecio(6.0);
		
		lineaPedido = new LineaPedido();
		lineaPedido.setCantidad(2);
		lineaPedido.setId(TEST_LINEAPEDIDO_ID);
		lineaPedido.setPedido(ped1);
		lineaPedido.setProducto(tarta);
		
		given(this.productoService.findProductoById(TEST_PRODUCTO_ID)).willReturn(Optional.of(tarta));
		given(this.pedidoService.findPedidoById(TEST_PEDIDO_ID)).willReturn(Optional.of(ped1));
		given(this.lineaPedidoService.findLineaPedidoById(TEST_LINEAPEDIDO_ID)).willReturn(Optional.of(lineaPedido));


		
		
}
	//CREATION TESTS
	
	@WithMockUser(value = "spring")
    @Test
    void testInitCreationForm() throws Exception {
	mockMvc.perform(get("/lineaPedidos/new")).andExpect(status().isOk()).andExpect(model().attributeExists("lineaPedido"))
			.andExpect(view().name("lineaPedidos/editLineaPedido"));
}
	
	
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/lineaPedidos/new").with(csrf()).param("producto", "Macarrones con Queso").param("cantidad", "2"))
			.andExpect(status().is2xxSuccessful());
}
	

	//TEST LISTAR LINEAPEDIDOS
		@WithMockUser(value = "spring")
	    @Test
	    void testListadoLineaPedido() throws Exception {
			mockMvc.perform(get("/lineaPedidos")).andExpect(status().isOk()).andExpect(model().attributeExists("lineaPedidos"))
			.andExpect(status().is2xxSuccessful()).andExpect(view().name("lineaPedidos/listadoLineaPedidos"));
		}
	
	
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
