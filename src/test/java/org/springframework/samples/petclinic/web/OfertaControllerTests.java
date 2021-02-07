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
import org.springframework.samples.petclinic.model.Oferta;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.service.OfertaService;
import org.springframework.samples.petclinic.service.RestauranteService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = {OfertaController.class, CustomErrorController.class},
includeFilters = @ComponentScan.Filter(value = OfertaFormatter.class, type = FilterType.ASSIGNABLE_TYPE),
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)

public class OfertaControllerTests {


		private static final int TEST_OFERTA_ID = 1;
		private static final int TEST_RESTAURANTE_ID = 1;


		@Autowired
		private OfertaController ofertaController;


		@MockBean
		private OfertaService ofertaService;

		@MockBean
		private RestauranteService restauranteService;
		
		@Autowired
		private MockMvc mockMvc;

		
		private Oferta oferta;
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
			
			
			oferta = new Oferta();
			oferta.setId(TEST_OFERTA_ID);
			oferta.setDescripcion("Descuento 3 euros");
			oferta.setDescuento(3.0);
			oferta.setExclusivo(false);
			oferta.setMinPrice(13.0);
			oferta.setRestaurante(restaurante);
			
			given(this.ofertaService.findOfertaById(TEST_OFERTA_ID)).willReturn(Optional.of(oferta));
			given(this.restauranteService.findRestauranteById(TEST_RESTAURANTE_ID)).willReturn(Optional.of(restaurante));
			
		}
	
	
		//CREATION TESTS
		
		@WithMockUser(value = "spring")
	    @Test
	    void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/restaurantes/{restauranteId}/ofertas/new", TEST_RESTAURANTE_ID)).andExpect(status().isOk()).andExpect(model().attributeExists("oferta"))
				.andExpect(view().name("ofertas/editOferta"));
	}
	
		@WithMockUser(value = "spring")
        @Test
        void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/restaurantes/{restauranteId}/ofertas/new", TEST_RESTAURANTE_ID)
							.with(csrf())
							.param("descripcion", "Descuento de 2 euros")
							.param("descuento", "2.0")
							.param("minPrice", "15.0")
							.param("exclusivo", "false")
							.param("restaurante","Restaurante 7"))
				.andExpect(status().is2xxSuccessful());
	}


		//TEST LISTAR OFERTAS
		@WithMockUser(value = "spring")
	    @Test
	    void testListadoOferta() throws Exception {
			mockMvc.perform(get("/restaurantes/{restauranteId}/ofertas", TEST_RESTAURANTE_ID)).andExpect(status().isOk()).andExpect(model().attributeExists("ofertas"))
			.andExpect(status().is2xxSuccessful()).andExpect(view().name("ofertas/listadoOfertas"));
		}
		
	
		//UPDATE TESTS
		
		@WithMockUser(value = "spring")
		@Test
		void testInitUpdateOfertaForm() throws Exception {
				mockMvc.perform(get("/restaurantes/{restauranteId}/ofertas/edit/{ofertaId}",TEST_RESTAURANTE_ID, TEST_OFERTA_ID))
				.andExpect(status().isOk())
						.andExpect(model().attributeExists("oferta"))
						.andExpect(model().attribute("oferta", hasProperty("descripcion", is("Descuento 3 euros"))))
						.andExpect(model().attribute("oferta", hasProperty("descuento", is(3.0))))
						.andExpect(model().attribute("oferta", hasProperty("minPrice", is(13.0))))
						.andExpect(model().attribute("oferta", hasProperty("exclusivo", is(false))))
						.andExpect(model().attribute("oferta", hasProperty("restaurante", is(restaurante))))
						.andExpect(view().name("ofertas/editOferta"));
	}
		

		
			@WithMockUser(value = "spring")
			@Test
			void testProcessUpdateOfertaFormSuccess() throws Exception {
				mockMvc.perform(post("/restaurantes/{restauranteId}/ofertas/edit/{ofertaId}", TEST_RESTAURANTE_ID, TEST_OFERTA_ID)
									.with(csrf())
									.param("descripcion", "Descuento de 2 euros")
									.param("descuento", "2.0")
									.param("minPrice", "15.0")
									.param("exclusivo", "false")
									.param("restaurante","Restaurante 7"))
									.andExpect(status().is2xxSuccessful());
			}

		
		
		

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
