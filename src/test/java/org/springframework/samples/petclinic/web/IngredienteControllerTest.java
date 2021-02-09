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
import org.springframework.samples.petclinic.model.Ingrediente;
import org.springframework.samples.petclinic.model.Medida;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.IngredienteService;
import org.springframework.samples.petclinic.service.ProductoService;
import org.springframework.samples.petclinic.service.ProveedorService;
import org.springframework.samples.petclinic.service.RestauranteService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers= {IngredienteController.class, CustomErrorController.class},
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class IngredienteControllerTest {

	private static final int TEST_INGREDIENTE_ID = 1;
	private static final int TEST_RESTAURANTE_ID = 1;
	
	@Autowired
	private IngredienteController ingredienteController;
	
	@MockBean
	private IngredienteService ingredienteService;
	
	@MockBean
	private RestauranteService restauranteService;
	
	@MockBean
    private AuthoritiesService authoritiesService;
	
	@MockBean
	private ProveedorService proveedoreService;
	
	@MockBean
	private ProductoService productoService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Ingrediente ingrediente;
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
		
		ingrediente = new Ingrediente();
		ingrediente.setId(TEST_INGREDIENTE_ID);
		ingrediente.setName("Leche");
		ingrediente.setStock(50);
		ingrediente.setMedida(Medida.L);
		ingrediente.setRestaurante(restaurante);
		ingrediente.setVersion(0);
		given(this.ingredienteService.findIngredienteById(TEST_INGREDIENTE_ID)).willReturn(Optional.of(ingrediente));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessListSuccess() throws Exception {
		given(this.ingredienteService.findAll()).willReturn(Lists.newArrayList(ingrediente, new Ingrediente()));

		mockMvc.perform(get("/restaurantes/{restauranteId}/ingredientes", TEST_RESTAURANTE_ID)).andExpect(status().isOk()).andExpect(view().name("ingredientes/listadoIngredientes"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/restaurantes/{restauranteId}/ingredientes/new", TEST_RESTAURANTE_ID)).andExpect(status().isOk()).andExpect(model().attributeExists("ingrediente"))
				.andExpect(view().name("ingredientes/editarIngrediente"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
	mockMvc.perform(post("/restaurantes/{restauranteId}/ingredientes/save", TEST_RESTAURANTE_ID)
						.param("Name", "Harina")
						.param("Medida", "KG")
						.with(csrf())
						.param("restaurante", String.valueOf(TEST_RESTAURANTE_ID))
						.param("stock", "30"))
						.andExpect(status().is3xxRedirection())
						.andExpect(view().name("redirect:/restaurantes/{restauranteId}/ingredientes"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
	mockMvc.perform(post("/restaurantes/{restauranteId}/ingredientes/save", TEST_RESTAURANTE_ID)
						.with(csrf())
						.param("Name", "Harina")
						.param("Medida", "error")
						.param("stock", "no"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("ingrediente"))
			.andExpect(model().attributeHasFieldErrors("ingrediente", "Medida"))
			.andExpect(model().attributeHasFieldErrors("ingrediente", "stock"))
			.andExpect(view().name("ingredientes/editarIngrediente"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testInitUpdateIngredienteForm() throws Exception {
		mockMvc.perform(get("/restaurantes/{restauranteId}/ingredientes/{ingredienteId}/edit", TEST_RESTAURANTE_ID, TEST_INGREDIENTE_ID))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("ingrediente"))
				.andExpect(model().attribute("ingrediente", hasProperty("name", is("Leche"))))
				.andExpect(model().attribute("ingrediente", hasProperty("medida", is(Medida.L))))
				.andExpect(model().attribute("ingrediente", hasProperty("stock", is(50.))))
				.andExpect(view().name("ingredientes/editarIngrediente"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessUpdateFormSuccess() throws Exception {
	mockMvc.perform(post("/restaurantes/{restauranteId}/ingredientes/save/{ingredienteId}", TEST_RESTAURANTE_ID, TEST_INGREDIENTE_ID)
						.param("version", String.valueOf(ingrediente.getVersion()))
						.param("Name", "Harina")
						.param("Medida", "KG")
						.with(csrf())
						.param("restaurante", String.valueOf(TEST_RESTAURANTE_ID))
						.param("stock", "30")
						.param("id", String.valueOf(TEST_INGREDIENTE_ID)))
						.andExpect(status().is3xxRedirection())
						.andExpect(view().name("redirect:/restaurantes/{restauranteId}/ingredientes"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessUpdateFormHasErrors() throws Exception {
	mockMvc.perform(post("/restaurantes/{restauranteId}/ingredientes/save/{ingredienteId}", TEST_RESTAURANTE_ID, TEST_INGREDIENTE_ID)
						.with(csrf())
						.param("Name", "Harina")
						.param("Medida", "error")
						.param("stock", "no")
						.param("id", String.valueOf(TEST_INGREDIENTE_ID))
						.param("version", String.valueOf(ingrediente.getVersion())))
						.andExpect(status().isOk())
						.andExpect(model().attributeHasErrors("ingrediente"))
						.andExpect(model().attributeHasFieldErrors("ingrediente", "Medida"))
						.andExpect(model().attributeHasFieldErrors("ingrediente", "stock"))
						.andExpect(view().name("ingredientes/editarIngrediente"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessUpdateFormHasVersionError() throws Exception {
	mockMvc.perform(post("/restaurantes/{restauranteId}/ingredientes/save/{ingredienteId}", TEST_RESTAURANTE_ID, TEST_INGREDIENTE_ID)
						.param("version", String.valueOf(ingrediente.getVersion()+1))
						.param("Name", "Harina")
						.param("Medida", "KG")
						.with(csrf())
						.param("restaurante", String.valueOf(TEST_RESTAURANTE_ID))
						.param("stock", "30")
						.param("id", String.valueOf(TEST_INGREDIENTE_ID)))
						.andExpect(status().isOk())
						.andExpect(view().name("ingredientes/listadoIngredientes"));
	}
}
