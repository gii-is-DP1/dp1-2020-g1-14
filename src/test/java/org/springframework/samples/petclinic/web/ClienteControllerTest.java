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
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=ClienteController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)


public class ClienteControllerTest {

	
	private static final int TEST_CLIENTE_ID = 10;

	@Autowired
	private ClienteController clienteController;

	@MockBean
	private ClienteService clienteService;
        
	@MockBean
	private UserService userService;
	        
	@MockBean
	private AuthoritiesService authoritiesService; 
	
	@Autowired
	private MockMvc mockMvc;

	private Cliente juan;
	private Cliente francisco;
	private Cliente javier;
	private User cliente1;
	private User cliente2;
	private User cliente3;

	@BeforeEach
	void setup() {
		
		cliente1=new User();
		cliente1.setEnabled(true);
		cliente1.setPassword("cliente1");
		cliente1.setrDate(LocalDate.of(2000, 10, 11));
		cliente1.setUsername("cliente1");
		
		juan = new Cliente();
		juan.setId(TEST_CLIENTE_ID);
		juan.setEsSocio(true);
		juan.setNumPedidos(12);
		juan.setTlf("954765812");
		juan.setUser(cliente1);
		
		given(this.clienteService.findClienteById(TEST_CLIENTE_ID)).willReturn(Optional.of(juan));

	}
	
	//CREATION TESTS
	
	@WithMockUser(value = "spring")
    @Test
    void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/clientes/new")).andExpect(status().isOk()).andExpect(model().attributeExists("cliente"))
			.andExpect(view().name("clientes/editCliente"));
}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/clientes/new")
						.param("user.username", "cliente4")
						.with(csrf())
						.param("user.rDate", "2005-12-03")
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
						.param("user.username", "cliente4")
						.param("user.rDate", "2005-12-03")
						.param("numPedidos", "15")
						.param("esSocio", "true")
						.param("tlf", ""))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("cliente"))
			.andExpect(model().attributeHasFieldErrors("cliente", "tlf"))
			.andExpect(view().name("clientes/editCliente"));
}

	
	
	//TEST QUE COMPRUEBA QUE LISTA LOS SOCIOS
	

	@WithMockUser(value = "spring")
    @Test
    void testListaSocios() throws Exception {
		
		cliente2=new User();
		cliente2.setEnabled(true);
		cliente2.setPassword("cliente2");
		cliente2.setrDate(LocalDate.of(1998, 01, 13));
		cliente2.setUsername("cliente2");
		
		cliente3=new User();
		cliente3.setEnabled(true);
		cliente3.setPassword("cliente3");
		cliente3.setrDate(LocalDate.of(1999, 8, 10));
		cliente3.setUsername("cliente3");
		
		francisco = new Cliente();
		francisco.setId(11);
		francisco.setEsSocio(true);
		francisco.setNumPedidos(12);
		francisco.setTlf("954357811");
		francisco.setUser(cliente2);
		
		javier = new Cliente();
		javier.setId(12);
		javier.setEsSocio(false);
		javier.setNumPedidos(11);
		javier.setTlf("954736516");
		javier.setUser(cliente3);
		
		
		given(this.clienteService.findSocios()).willReturn(Lists.newArrayList(juan, francisco, javier));

	mockMvc.perform(get("/clientes/socios")).andExpect(status().isOk()).andExpect(view().name("clientes/listadoSocios"));
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