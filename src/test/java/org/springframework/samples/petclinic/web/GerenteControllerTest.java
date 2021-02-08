package org.springframework.samples.petclinic.web;

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
import org.springframework.samples.petclinic.model.Gerente;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.GerenteService;
import org.springframework.samples.petclinic.service.ProductoService;
import org.springframework.samples.petclinic.service.RestauranteService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

@WebMvcTest(controllers= {GerenteController.class, CustomErrorController.class}, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
classes = WebSecurityConfigurer.class), excludeAutoConfiguration= SecurityConfiguration.class)

public class GerenteControllerTest {

	private static final int TEST_GERENTE_ID = 1;
	private static final int TEST_RESTAURANTE_ID = 1;
	
	@Autowired
	private GerenteController gerenteController;
	
	@MockBean
	private GerenteService gerenteService;
	
	@MockBean
	private RestauranteService restauranteService;
	
	@MockBean
	private GerenteValidator validator;
	
	@MockBean
	private ProductoService productoService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Gerente nombre1;
	private Restaurante restaurante1;
	private User usuario1;
	
	@InitBinder("gerente")
	public void initGerenteBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(validator);
	}
	
	@BeforeEach
	void setup() {
		
		usuario1 = new User();
		usuario1.setPassword("gerente1");
		usuario1.setrDate(LocalDate.of(2000, 10, 11));
		
		nombre1 = new Gerente();
		nombre1.setId(TEST_GERENTE_ID);
		nombre1.setName("nombre1");
		nombre1.setUser(usuario1);
		nombre1.setDni("12345678F");
		given(this.gerenteService.findGerenteById(TEST_GERENTE_ID)).willReturn(Optional.of(nombre1));
		
		restaurante1 = new Restaurante();
		restaurante1.setId(TEST_RESTAURANTE_ID);
		restaurante1.setName("restaurante1");
		restaurante1.setTipo("Italiano");
		restaurante1.setLocalizacion("Reina Mercedes, 34");
		restaurante1.setAforomax(26);
		restaurante1.setGerente(nombre1);
		given(this.restauranteService.findRestauranteById(TEST_RESTAURANTE_ID)).willReturn(Optional.of(restaurante1));
	

	}
	
	//creation tests below
	
	@WithMockUser(value = "spring")
    @Test
    void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/restaurantes/{restauranteId}/gerentes/new", TEST_RESTAURANTE_ID))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("gerente"))
				.andExpect(view().name("gerentes/editarGerente"));
	}

	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/restaurantes/{restauranteId}/gerentes/save", TEST_RESTAURANTE_ID)
				.param("name", "Alex")
				.with(csrf())
				.param("user.username", "gerente3")
				.param("user.password","gerente3")
				.param("rDate", "2018-12-03")
				.param("dni", "42869434T"))
		.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/restaurantes/{restauranteId}/gerentes/save", TEST_RESTAURANTE_ID)	
						.with(csrf())
						.param("name", "")
						.param("password","")
						.param("rDate", "2018-12-03")
						.param("dni", "75927501T"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("user"))
			.andExpect(model().attributeHasFieldErrors("gerente", "password"))
			.andExpect(model().attributeHasFieldErrors("gerente", "name"))
			.andExpect(view().name("gerentes/editarGerente"));
	}	
	
	//test de listado de gerentes 
	@WithMockUser(value = "spring")
	@Test
	void testListadoGerentes() throws Exception {
		mockMvc.perform(get("/restaurantes/{restauranteId}/gerentes", TEST_RESTAURANTE_ID))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("gerentes"))
				.andExpect(view().name("gerentes/listadoGerentes"));
	}
}