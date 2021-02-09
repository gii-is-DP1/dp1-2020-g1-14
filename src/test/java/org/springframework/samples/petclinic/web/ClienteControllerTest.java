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
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers= {ClienteController.class, CustomErrorController.class},
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)


public class ClienteControllerTest {

	@MockBean
	private ClienteValidator clienteValidator;
	
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

	private Cliente cliente1;
	private Cliente cliente2;
	private Cliente cliente3;
	private Cliente cliente4;
	private User user;
	private Authorities authorities;
	

	@BeforeEach
	void setup() {

		authorities = new Authorities();
		authorities.setId(10);
		authorities.setUser(user);
		authorities.setAuthority("cliente");

		user=new User();
		user.setEnabled(true);
		user.setPassword("cliente1");
		user.setrDate(LocalDate.of(2020, 01, 01));
		user.setUsername("cliente1");
		user.setAuthorities(authorities);

		cliente1 = new Cliente();
		cliente1.setId(1);
		cliente1.setEsSocio(false);
		cliente1.setNumPedidos(12);
		cliente1.setTlf("954765812");
		cliente1.setMonedero(300.);
		cliente1.setUser(user);
	

		given(this.clienteService.findClienteById(1)).willReturn(Optional.of(cliente1));

	}

	//TEST QUE COMPRUEBA QUE LISTA LOS CLIENTES
	@WithMockUser(value = "spring")
	@Test
	void testListadoClientes() throws Exception {
		mockMvc.perform(get("/clientes")).andExpect(status().isOk())
		.andExpect(model().attributeExists("clientes"))
		.andExpect(view().name("clientes/listadoClientes"));
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
	void testSaveClienteSuccess() throws Exception {
		mockMvc.perform(post("/clientes/save").with(csrf())
				.param("user.username", "cliente4")
				.param("user.rDate", "2005-12-03")
				.param("user.password", "cliente4")
				.param("numPedidos", "15")
				.param("esSocio", "false")
				.param("tlf", "955857896"))
		.andExpect(status().is2xxSuccessful());
	}


	@WithMockUser(value = "spring")
	@Test
	void testSaveClienteHasErrors() throws Exception {
		mockMvc.perform(post("/clientes/save")
				.with(csrf())
				.param("user.username", "cliente4")
				.param("user.rDate", "2005-12-03")
				.param("user.password", "cliente4")
				.param("numPedidos", "15")
				.param("esSocio", "false")
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

		authorities = new Authorities();
		authorities.setId(11);
		authorities.setUser(user);
		authorities.setAuthority("cliente");

		authorities = new Authorities();
		authorities.setId(12);
		authorities.setUser(user);
		authorities.setAuthority("cliente");

		user=new User();
		user.setEnabled(true);
		user.setPassword("cliente2");
		user.setrDate(LocalDate.of(2020, 01, 01));
		user.setUsername("cliente2");

		user=new User();
		user.setEnabled(true);
		user.setPassword("cliente3");
		user.setrDate(LocalDate.of(2020, 01, 01));
		user.setUsername("cliente3");

		cliente2 = new Cliente();
		cliente2.setId(2);
		cliente2.setEsSocio(true);
		cliente2.setNumPedidos(12);
		cliente2.setMonedero(100.);
		cliente2.setTlf("954357811");
		cliente2.setUser(user);

		cliente3 = new Cliente();
		cliente3.setId(3);
		cliente3.setEsSocio(false);
		cliente3.setNumPedidos(11);
		cliente3.setTlf("954736516");
		cliente3.setMonedero(30.);
		cliente3.setUser(user);


		given(this.clienteService.findSocios()).willReturn(Lists.newArrayList(cliente1, cliente2, cliente3));

		mockMvc.perform(get("/clientes/socios")).andExpect(status().isOk()).andExpect(view().name("clientes/listadoSocios"));
	}




}