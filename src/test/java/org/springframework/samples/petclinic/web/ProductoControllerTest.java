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

import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.ProductoService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers = ProductoController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)

public class ProductoControllerTest {

	private static final int TEST_PRODUCTO_ID = 1;
	
	@Autowired
	private ProductoController productoController;

	@MockBean
	private ProductoService productoService;
	

	@Autowired
	private MockMvc mockMvc;
	
	private Producto pizza;
	
	@BeforeEach
	void setup() {
		
		this.pizza = new Producto();
		this.pizza.setAlergenos("Leche");
		this.pizza.setId(TEST_PRODUCTO_ID);
		this.pizza.setName("Pizza");
		this.pizza.setPrecio(6.50);
		given(this.productoService.findProductoById(TEST_PRODUCTO_ID)).willReturn(Optional.of(pizza));
		
}
	@WithMockUser(value = "spring")
    @Test
    void testInitCreationForm() throws Exception {
	mockMvc.perform(get("/productos/new")).andExpect(status().isOk()).andExpect(model().attributeExists("producto"))
			.andExpect(view().name("productos/editProducto"));
}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateProductForm() throws Exception {
			mockMvc.perform(get("/productos/{productoId}/edit", TEST_PRODUCTO_ID)).andExpect(status().isOk())
					.andExpect(model().attributeExists("producto"))
					.andExpect(model().attribute("producto", hasProperty("name", is("Pizza"))))
					.andExpect(model().attribute("producto", hasProperty("precio", is(6.50))))
					.andExpect(model().attribute("producto", hasProperty("alergenos", is("Leche"))))
					.andExpect(view().name("productos/editProducto"));
}
	

				
	@WithMockUser(value = "spring")
    @Test
    void testProcessFindFormSuccess() throws Exception {
		given(this.productoService.findProductoByName("")).willReturn(Lists.newArrayList(pizza, new Producto()));

		mockMvc.perform(get("/productos")).andExpect(status().isOk()).andExpect(view().name("productos/listadoProductos"));
}
		
		
	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
