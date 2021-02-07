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

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Medida;
import org.springframework.samples.petclinic.model.Reclamacion;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ProductoService;
import org.springframework.samples.petclinic.service.ReclamacionService;
import org.springframework.samples.petclinic.service.RestauranteService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers= {ReclamacionController.class, CustomErrorController.class},
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)

public class ReclamacionControllerTest {

	private static final int TEST_RECLAMACION_ID = 1;
	private static final int TEST_RESTAURANTE_ID = 1;
	
	@Autowired
	ReclamacionController reclamacionController;
	
	@MockBean
	private ReclamacionService reclamacionService;
	
	@MockBean
	private RestauranteService restauranteService;
	
	@MockBean
    private AuthoritiesService authoritiesService; 
	
	@MockBean
	private ProductoService productoService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Reclamacion reclamacion;
	private Restaurante restaurante;
	
	@BeforeEach
	void setup() {
		restaurante = new Restaurante();
		restaurante.setId(TEST_RESTAURANTE_ID);
		restaurante.setName("Prueba");
		restaurante.setAforomax(100);
		restaurante.setLocalizacion("Sevilla");
		restaurante.setTipo("Italiano");
		given(this.restauranteService.findRestauranteById(TEST_RESTAURANTE_ID)).willReturn(Optional.of(restaurante));
		
		reclamacion = new Reclamacion();
		reclamacion.setId(TEST_RECLAMACION_ID);
		reclamacion.setDescripcion("Reclamacion de prueba");
		reclamacion.setRestaurante(restaurante);
		given(this.reclamacionService.findReclamacionById(TEST_RECLAMACION_ID)).willReturn(Optional.of(reclamacion));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessListSuccess() throws Exception {
		given(this.reclamacionService.findAll()).willReturn(Lists.newArrayList(reclamacion, new Reclamacion()));

		mockMvc.perform(get("/restaurantes/{restauranteId}/reclamaciones", TEST_RESTAURANTE_ID)).andExpect(status().isOk()).andExpect(view().name("reclamaciones/listadoReclamaciones"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/restaurantes/{restauranteId}/reclamaciones/new", TEST_RESTAURANTE_ID)).andExpect(status().isOk()).andExpect(model().attributeExists("reclamacion"))
				.andExpect(view().name("reclamaciones/crearReclamacion"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/restaurantes/{restauranteId}/reclamaciones/new", TEST_RESTAURANTE_ID)
				.param("descripcion", "Hola buenas tardes")
				.with(csrf())
				.param("restaurante", "Prueba"))
				.andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/restaurantes/{restauranteId}/reclamaciones/save", TEST_RESTAURANTE_ID)
				.with(csrf())
				.param("descripcion", ""))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("reclamacion"))
				.andExpect(model().attributeHasFieldErrors("reclamacion", "descripcion"))
				.andExpect(view().name("/reclamaciones/crearReclamacion"));
	}
}
