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
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=ClienteController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)


public class ClienteControllerTest {

	
	private static final int TEST_CLIENTE_ID = 1;

	@Autowired
	private ClienteController clienteController;

	@MockBean
	private ClienteService clienteService;
        

	@Autowired
	private MockMvc mockMvc;

	private Cliente juan;
	private Cliente francisco;
	private Cliente javier;

	@BeforeEach
	void setup() {

		juan = new Cliente();
		juan.setId(TEST_CLIENTE_ID);
		juan.getUser().setPassword("pass1");
		juan.getUser().setrDate(LocalDate.of(2000, 10, 11));
		juan.setEsSocio(true);
		juan.setNumPedidos(12);
		juan.setTlf("954765812");
		given(this.clienteService.findClienteById(TEST_CLIENTE_ID)).willReturn(Optional.of(juan));

	}
	
	//CREATION TESTS

	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/clientes/new")
						.param("name", "Pedro")
						.with(csrf())
						.param("rDate", "2005-12-03")
						.param("password", "pass5")
						.param("numPedidos", "15")
						.param("esSocio", "true")
						.param("tlf", "955857896"))
		.andExpect(status().is2xxSuccessful());
}
	

	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/clientes/save")
						.with(csrf())
						.param("name", "Pedro")
						.param("rDate", "2005-12-03")
						.param("password", "")
						.param("numPedidos", "15")
						.param("esSocio", "true")
						.param("tlf", ""))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("cliente"))
			.andExpect(model().attributeHasFieldErrors("cliente", "password"))
			.andExpect(model().attributeHasFieldErrors("cliente", "tlf"))
			.andExpect(view().name("clientes/editCliente"));
}

	
	
	//TEST QUE COMPRUEBA QUE LISTA LOS SOCIOS
	
	@WithMockUser(value = "spring")
    @Test
    void testListaSocios() throws Exception {
		
		francisco = new Cliente();
		francisco.setId(2);
		francisco.getUser().setPassword("pass2");
		francisco.getUser().setrDate(LocalDate.of(1998, 01, 13));
		francisco.setEsSocio(true);
		francisco.setNumPedidos(12);
		francisco.setTlf("954357811");
		
		javier = new Cliente();
		javier.setId(3);
		javier.getUser().setPassword("pass3");
		javier.getUser().setrDate(LocalDate.of(1999, 8, 10));
		javier.setEsSocio(false);
		javier.setNumPedidos(11);
		javier.setTlf("954736516");
		
		
		given(this.clienteService.findSocios()).willReturn(Lists.newArrayList(juan, francisco, javier));

	mockMvc.perform(get("/clientes")).andExpect(status().isOk()).andExpect(view().name("clientes/listadoClientes"));
}
	
	

   //TEST QUE COMPRUEBA QUE LISTA TODOS LOS CLIENTES
	@WithMockUser(value = "spring")
	@Test
	void testListadoClientes() throws Exception {
		mockMvc.perform(get("/clientes")).andExpect(status().isOk())
				.andExpect(model().attributeExists("clientes"))
				.andExpect(view().name("clientes/listadoClientes"));
	}
	
}