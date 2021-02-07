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



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.service.ProductoService;
import org.springframework.samples.petclinic.service.RestauranteService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(value = ProductoController.class,
includeFilters = @ComponentScan.Filter(value = ProductoFormatter.class, type = FilterType.ASSIGNABLE_TYPE),
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)

public class ProductoControllerTest {

	private static final int TEST_PRODUCTO_ID = 1;
	private static final int TEST_RESTAURANTE_ID = 1;
	
	@Autowired
	private ProductoController productoController;

	@MockBean
	private ProductoService productoService;
	
	@MockBean
	private RestauranteService restauranteService;

	@Autowired
	private MockMvc mockMvc;
	
	private Producto tarta;
	private Restaurante restaurante;
	
	@BeforeEach
	void setup() {
		
		restaurante = new Restaurante();
		restaurante.setId(TEST_RESTAURANTE_ID);
		restaurante.setName("Restaurante 1");
		restaurante.setTipo("Chino");
		restaurante.setLocalizacion("Reina Mercedes, 34");
		restaurante.setAforomax(25);
		restaurante.setSenial(20);
		
	
		tarta = new Producto();
		tarta.setAlergenos("Lacteos, Huevo y Gluten");
		tarta.setId(TEST_PRODUCTO_ID);
		tarta.setName("Tarta");
		tarta.setPrecio(6.0);
		tarta.setRestaurante(restaurante);
		given(this.productoService.findProductoById(TEST_PRODUCTO_ID)).willReturn(Optional.of(tarta));
		given(this.restauranteService.findRestauranteById(TEST_RESTAURANTE_ID)).willReturn(Optional.of(restaurante));
	
}
	
	//TEST LISTAR PRODUCTOS
		@WithMockUser(value = "spring")
	    @Test
	    void testListadoProducto() throws Exception {
			mockMvc.perform(get("/restaurantes/{restauranteId}/productos", TEST_RESTAURANTE_ID)).andExpect(status().isOk()).andExpect(model().attributeExists("productos"))
			.andExpect(status().is2xxSuccessful()).andExpect(view().name("productos/listadoProductos"));
		}
	
	
	//CREATION TESTS
	
	@WithMockUser(value = "spring")
    @Test
    void testInitCreationForm() throws Exception {
	mockMvc.perform(get("/restaurantes/{restauranteId}/productos/new", TEST_RESTAURANTE_ID)).andExpect(status().isOk()).andExpect(model().attributeExists("producto"))
			.andExpect(view().name("productos/editProducto"));
}
	
	
	@WithMockUser(value = "spring")
    @Test
    void testSaveSuccess() throws Exception {
		mockMvc.perform(post("/restaurantes/{restauranteId}/productos/new", TEST_RESTAURANTE_ID).with(csrf())
				.param("name", "Macarrones con Queso")
				.param("alergenos", "Queso")
				.param("restaurante", "Restaurante 6")
				.param("precio", "6.0"))
			.andExpect(status().is2xxSuccessful());
}
	
	
	//UPDATE TESTS
	
	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateProductForm() throws Exception {
			mockMvc.perform(get("/restaurantes/{restauranteId}/productos/edit/{productoId}",TEST_RESTAURANTE_ID, TEST_PRODUCTO_ID))
			.andExpect(status().isOk())
					.andExpect(model().attributeExists("producto"))
					.andExpect(model().attribute("producto", hasProperty("name", is("Tarta"))))
					.andExpect(model().attribute("producto", hasProperty("precio", is(6.0))))
					.andExpect(model().attribute("producto", hasProperty("alergenos", is("Lacteos, Huevo y Gluten"))))
					.andExpect(model().attribute("producto", hasProperty("restaurante", is(restaurante))))
					.andExpect(view().name("productos/editProducto"));
}
	

	
		@WithMockUser(value = "spring")
		@Test
		void testProcessUpdateProductoFormSuccess() throws Exception {
			mockMvc.perform(post("/restaurantes/{restauranteId}/productos/edit/{productoId}", TEST_RESTAURANTE_ID, TEST_PRODUCTO_ID)
								.with(csrf())
								.param("name", "Macarrones con Queso")
								.param("alergenos", "Queso")
								.param("precio", "7.70")
								.param("restaurante", "Restaurante 6"))
								.andExpect(status().is2xxSuccessful());
		}

	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
