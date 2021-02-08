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
import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ProductoService;
import org.springframework.samples.petclinic.service.RestauranteService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers= {RestauranteController.class, CustomErrorController.class},
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class RestauranteControllerTest {
	
	@Autowired
	private RestauranteController restauranteController;
	
	@MockBean
	private RestauranteService restauranteService;
	
	@MockBean
	private AuthoritiesService authoritiesService;
	
	@MockBean
	private ProductoService productoService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Restaurante restaurante;
	
	@BeforeEach
	void setup() {
		restaurante = new Restaurante();
		restaurante.setId(1);
		restaurante.setName("Restaurante");
		restaurante.setTipo("Italiano");
		restaurante.setLocalizacion("localizado en algun lado");
		restaurante.setAforomax(100);
		
		given(this.restauranteService.findRestauranteById(1)).willReturn(Optional.of(restaurante));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testInitCreationForm() throws Exception{
		mockMvc.perform(get("/restaurantes/new")).andExpect(status().isOk()).andExpect(model().attributeExists("restaurante"))
		.andExpect(view().name("restaurantes/editRestaurantes"));
	}
	
	 @WithMockUser(value = "spring")
	 @Test
	 void testProcessCreationFormSuccess() throws Exception {
		 mockMvc.perform(get("/restaurantes/new").param("name", "restaurante").param("tipo", "japones")
				 .param("localizacion", "C/ Camas")
				 .param("aforoMax", "50")
				 .with(csrf()))
		 .andExpect(status().is2xxSuccessful());
	 }
	 @WithMockUser(value = "spring")
	 @Test
	 void testInitUpdateRestauranteForm() throws Exception {
		mockMvc.perform(get("/restaurantes/{restauranteId}/edit", 1)).andExpect(status().isOk())
				.andExpect(model().attributeExists("restaurante"))
				.andExpect(model().attribute("restaurante", hasProperty("name", is("Restaurante"))))
				.andExpect(model().attribute("restaurante", hasProperty("tipo", is("Italiano"))))
				.andExpect(model().attribute("restaurante", hasProperty("localizacion", is("localizado en algun lado"))))
				.andExpect(model().attribute("restaurante", hasProperty("aforomax", is(100))))
				.andExpect(view().name("restaurantes/editRestaurantes"));
	 }
}
