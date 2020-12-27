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
	
	private Producto tarta;
	
	@BeforeEach
	void setup() {
		
		this.tarta = new Producto();
		this.tarta.setAlergenos("Lacteos, Huevo y Gluten");
		this.tarta.setId(TEST_PRODUCTO_ID);
		this.tarta.setName("Tarta");
		this.tarta.setPrecio(6.0);
		given(this.productoService.findProductoById(TEST_PRODUCTO_ID)).willReturn(Optional.of(tarta));
		
}
	//CREATION TESTS
	
	@WithMockUser(value = "spring")
    @Test
    void testInitCreationForm() throws Exception {
	mockMvc.perform(get("/productos/new")).andExpect(status().isOk()).andExpect(model().attributeExists("producto"))
			.andExpect(view().name("productos/editProducto"));
}
	
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/productos/new").param("name", "Macarrones con Queso").param("alergenos", "Queso")
						.with(csrf())
						.param("precio", "6.0"))
			.andExpect(status().is2xxSuccessful());
}
	
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception{
		mockMvc.perform(post("/productos/save")
				.with(csrf())
				.param("name", "Macarrones con Queso")
				.param("alergenos", "Queso")
				.param("precio", "dfgdfght5y897q34thdfg"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("producto"))
		.andExpect(model().attributeHasFieldErrors("producto", "precio"))
		.andExpect(view().name("productos/editProducto"));
	}
	
	
	//TEST LISTAR PRODUCTOS
	@WithMockUser(value = "spring")
    @Test
    void testListadoProducto() throws Exception {
		mockMvc.perform(get("/productos")).andExpect(status().isOk()).andExpect(model().attributeExists("productos"))
		.andExpect(status().is2xxSuccessful()).andExpect(view().name("productos/listadoProductos"));
	}
	
	
	//UPDATE TESTS
	
	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateProductForm() throws Exception {
			mockMvc.perform(get("/productos/{productoId}/edit", TEST_PRODUCTO_ID)).andExpect(status().isOk())
					.andExpect(model().attributeExists("producto"))
					.andExpect(model().attribute("producto", hasProperty("name", is("Tarta"))))
					.andExpect(model().attribute("producto", hasProperty("precio", is(6.0))))
					.andExpect(model().attribute("producto", hasProperty("alergenos", is("Lacteos, Huevo y Gluten"))))
					.andExpect(view().name("productos/editProducto"));
}
	

	
		@WithMockUser(value = "spring")
		@Test
		void testProcessUpdateProductoFormSuccess() throws Exception {
			mockMvc.perform(post("/productos/{productoId}/edit", TEST_PRODUCTO_ID)
								.with(csrf())
								.param("name", "Macarrones con Queso")
								.param("alergenos", "Queso")
								.param("precio", "7.70"))
								.andExpect(status().is3xxRedirection())
					.andExpect(view().name("redirect:/productos/{productoId}"));
		}

	
	
	
		@WithMockUser(value = "spring")
		@Test
		void testProcessUpdateProductoFormHasErrors() throws Exception{
			mockMvc.perform(post("/productos/{productoId}/edit", TEST_PRODUCTO_ID)
					.with(csrf())
					.param("name", "Macarrones con Queso")
					.param("alergenos", "Queso")
					.param("precio", "dfgdfght5y897q34thdfg"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("producto"))
			.andExpect(model().attributeHasFieldErrors("producto", "precio"))
			.andExpect(view().name("productos/editProducto"));
		}
	
	
	

	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
