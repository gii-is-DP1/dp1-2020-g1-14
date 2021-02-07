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
import org.springframework.samples.petclinic.model.Reserva;
import org.springframework.samples.petclinic.model.Restaurante;
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

	@BeforeEach
	void setup() {

		restaurante = new Restaurante();
		restaurante.setId(1);
		restaurante.setName("Restaurante");
		restaurante.setTipo("Italiano");
		restaurante.setLocalizacion("localizado en algun lado");
		restaurante.setAforomax(100);
		//restaurante.setAforores(50);


		reserva = new Reserva();
		reserva.setId(1);
		reserva.setFecha(LocalDate.of(2020, 12, 28));
		reserva.setHoraInicio(LocalTime.of(12, 00));
		reserva.setHoraFin(LocalTime.of(13, 00));
		reserva.setEvento(false);
		reserva.setnPersonas(5);
		//reserva.setRestaurante(restaurante);

		given(this.restauranteService.findRestauranteById(1)).willReturn(Optional.of(restaurante));
		given(this.reservaService.findReservaById(1)).willReturn(Optional.of(reserva));


	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/restaurantes/{restauranteId}/reservas/new", 1))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("reserva"))
		.andExpect(view().name("reservas/editReservas"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/restaurantes/{restauranteId}/reservas/new", 1).param("fecha", "20/12/28").param("horaInicio", "12:00")
				.with(csrf())
				.param("horaFin", "13:00")
				.param("evento", "false")
				.param("nPersonas", "5"))
		.andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/restaurantes/{restauranteId}/reservas/save", 1)
				.with(csrf())
				.param("fecha", "20/12/28")
				.param("horaInicio", "12:00")
				.param("horaFin", "13:00")
				.param("evento", "error")
				.param("nPersonas", "error"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("reserva"))
		.andExpect(model().attributeHasFieldErrors("reserva", "evento"))
		.andExpect(model().attributeHasFieldErrors("reserva", "nPersonas"))
		.andExpect(view().name("reservas/editReservas"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateReservaForm() throws Exception {
		mockMvc.perform(get("/restaurantes/{restauranteId}/reservas/{reservasId}/edit", 1, 1)).andExpect(status().isOk())
		.andExpect(model().attributeExists("reserva"))
		.andExpect(model().attribute("reserva", hasProperty("fecha", is(LocalDate.of(2020, 12, 28)))))
		.andExpect(model().attribute("reserva", hasProperty("horaInicio", is(LocalTime.of(12, 00)))))
		.andExpect(model().attribute("reserva", hasProperty("horaFin", is(LocalTime.of(13, 00)))))
		.andExpect(model().attribute("reserva", hasProperty("evento", is(false))))
		.andExpect(model().attribute("reserva", hasProperty("nPersonas", is(5))))
		.andExpect(view().name("reservas/editReservas"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateReservaFormSuccess() throws Exception {
		mockMvc.perform(post("/restaurantes/{restauranteId}/reservas/{reservasId}/edit", 1, 1)
				.with(csrf())
				.param("fecha", "20/12/30")
				.param("horaInicio", "13:00")
				.param("horaFin", "15:00")
				.param("evento", "true")
				.param("nPersonas", "12"))
		.andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateReservaFormHasErrors() throws Exception {
		mockMvc.perform(post("/restaurantes/{restauranteId}/reservas/{reservasId}/edit", 1, 1)
				.with(csrf())
				.param("fecha", "20/12/28")
				.param("horaInicio", "12:00")
				.param("horaFin", "13:00"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("reserva"))
		.andExpect(model().attributeHasFieldErrors("reserva", "evento"))
		.andExpect(model().attributeHasFieldErrors("reserva", "nPersonas"))
		.andExpect(view().name("reservas/editReservas"));
	}
}
