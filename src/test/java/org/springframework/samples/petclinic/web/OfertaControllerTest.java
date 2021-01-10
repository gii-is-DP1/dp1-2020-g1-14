/*	
package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Oferta;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.service.OfertaService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ProductoController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)

public class OfertaControllerTest {

	private static final int TEST_OFERTA_ID =1;
	
	@Autowired
	private OfertaController ofertaController;
	
	@MockBean
	private OfertaService ofertaService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Oferta oferta;
	
	@BeforeEach
	void setup() {
		
		this.oferta = new Oferta();
		this.oferta.setDescripcion("Oferta numero");
		this.oferta.setId(TEST_OFERTA_ID);

		given(this.ofertaService.findOfertaById(TEST_OFERTA_ID)).willReturn(Optional.of(oferta));
	}
}
	*/