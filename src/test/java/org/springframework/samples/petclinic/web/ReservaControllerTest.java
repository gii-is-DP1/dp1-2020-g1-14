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
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Reserva;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.ReservaService;
import org.springframework.samples.petclinic.service.RestauranteService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers= {ReservaController.class, CustomErrorController.class},
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class ReservaControllerTest {

	@Autowired
	private ReservaController reservaController;

	@MockBean
	private ReservaService reservaService;

	@MockBean
	private RestauranteService restauranteService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@MockBean
	private ClienteService clienteService;

	@MockBean
	private UserService userService;

	@Autowired
	private MockMvc mockMvc;

	private Reserva reserva;

	private Restaurante restaurante;
	
	private Authorities authorities;
	
	private User user1;
	
	private Cliente cliente1;

	@BeforeEach
	void setup() {
		
		authorities = new Authorities();
		authorities.setId(10);
		authorities.setUser(user1);
		authorities.setAuthority("cliente");
		
		user1=new User();
		user1.setEnabled(true);
		user1.setPassword("cliente1");
		user1.setrDate(LocalDate.of(2020, 01, 01));
		user1.setUsername("cliente1");
		user1.setAuthorities(authorities);
		
		cliente1 = new Cliente();
		cliente1.setEsSocio(false);
		cliente1.setNumPedidos(12);
		cliente1.setTlf("954765812");
		cliente1.setMonedero(300.);
		cliente1.setUser(user1);

		restaurante = new Restaurante();
		restaurante.setId(1);
		restaurante.setName("Restaurante");
		restaurante.setTipo("Italiano");
		restaurante.setLocalizacion("localizado en algun lado");
		restaurante.setAforomax(100);


		reserva = new Reserva();
		reserva.setId(1);
		reserva.setFecha(LocalDate.of(2020, 12, 28));
		reserva.setHoraInicio(LocalTime.of(12, 00));
		reserva.setHoraFin(LocalTime.of(13, 00));
		reserva.setEvento(false);
		reserva.setnPersonas(5);
		
		given(this.restauranteService.findRestauranteById(1)).willReturn(Optional.of(restaurante));
		given(this.reservaService.findReservaById(1)).willReturn(Optional.of(reserva));
		given(this.userService.findUser(user1.getUsername())).willReturn(Optional.of(user1));
		given(this.clienteService.findClienteByUsuario(user1.getUsername())).willReturn(Optional.of(cliente1));
		

	}

	@WithMockUser(authorities = "cliente", username = "cliente1",password = "cliente1")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/restaurantes/{restauranteId}/reservas/{username}/new", 1, cliente1.getUser().getUsername()))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("reserva"))
		.andExpect(view().name("reservas/editReservas"));
	}

	@WithMockUser(authorities = "cliente", username = "cliente1",password = "cliente1")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(get("/restaurantes/{restauranteId}/reservas/{username}/new", 1, cliente1.getUser().getUsername())
				.param("fecha", "2050/12/28")
				.param("horaInicio", "12:00")
				.with(csrf())
				.param("horaFin", "13:00")
				.param("evento", "false")
				.param("nPersonas", "5"))
		.andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(authorities = "cliente", username = "cliente1",password = "cliente1")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(get("/restaurantes/{restauranteId}/reservas/{username}/save", 1, cliente1.getUser().getUsername())
				.with(csrf())
				.param("fecha", "error")
				.param("horaInicio", "error")
				.param("horaFin", "error")
				.param("evento", "error")
				.param("nPersonas", "error"))
		.andExpect(status().is4xxClientError());
	}

	
}
