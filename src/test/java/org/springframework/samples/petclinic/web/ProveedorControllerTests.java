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
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
import org.springframework.samples.petclinic.model.Gerente;
import org.springframework.samples.petclinic.model.Proveedor;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.GerenteService;
import org.springframework.samples.petclinic.service.IngredienteService;
import org.springframework.samples.petclinic.service.ProveedorService;
import org.springframework.samples.petclinic.service.RestauranteService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {ProveedorController.class, CustomErrorController.class},
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)



public class ProveedorControllerTests {
	
	
	private static final int TEST_PROVEEDOR_ID = 10;
	private static final int TEST_RESTAURANTE_ID = 10;
	private static final int TEST_GERENTE_ID = 10;

	@MockBean
	private IngredienteService ingredService;
	
	@Autowired
	private ProveedorController proveedorController;

	@MockBean
	private ProveedorService proveedorService;
	
	@MockBean
	private RestauranteService restauranteService;
	
	@MockBean
	private UserService userService;
	@MockBean
	private GerenteService gerenteService;
	@MockBean
	private AuthoritiesService authService;

	@Autowired
	private MockMvc mockMvc;
	
	private Proveedor proveedor;
	private Restaurante restaurante;
	private Authorities authorities;
	private User user1;
	private Gerente gerente1;
	
	@BeforeEach
	void setup() {
		
		authorities = new Authorities();
		authorities.setId(10);
		authorities.setUser(user1);
		authorities.setAuthority("gerenete");
		
		user1=new User();
		user1.setEnabled(true);
		user1.setPassword("gerente1");
		user1.setrDate(LocalDate.of(2020, 01, 01));
		user1.setUsername("gerente1");
		user1.setAuthorities(authorities);

		gerente1 = new Gerente();
		gerente1.setDni("29560293A");
		gerente1.setId(TEST_GERENTE_ID);
		gerente1.setName("nombre");
		gerente1.setUser(user1);
		
		restaurante = new Restaurante();
		restaurante.setId(TEST_RESTAURANTE_ID);
		restaurante.setName("Restaurante 1");
		restaurante.setTipo("Chino");
		restaurante.setLocalizacion("Reina Mercedes, 34");
		restaurante.setAforomax(25);
		restaurante.setSenial(20);
		
		Set<Proveedor> proveedores = new HashSet<Proveedor>();
		Set<Restaurante> restaurantes = new HashSet<Restaurante>();
		
		proveedor = new Proveedor();
		proveedor.setId(TEST_PROVEEDOR_ID);
		proveedor.setName("Ejemplo");
		proveedor.setTlf("684312254");
		
		proveedores.add(proveedor);
		restaurantes.add(restaurante);
		
		restaurante.setProveedores(proveedores);
		proveedor.setRestaurantes(restaurantes);
		
		given(this.proveedorService.findProveedorById(TEST_PROVEEDOR_ID)).willReturn(Optional.of(proveedor));
		given(this.restauranteService.findRestauranteById(TEST_RESTAURANTE_ID)).willReturn(Optional.of(restaurante));
		given(this.userService.findUser(user1.getUsername())).willReturn(Optional.of(user1));
		given(this.gerenteService.findGerenteByUsuario(user1.getUsername())).willReturn(Optional.of(gerente1));

		
}
	
    //TEST LISTAR PROVEEDORES
	@WithMockUser(authorities = "gerente",username = "gerente1",password = "gerente1",value = "gerente1")
    @Test
    void testListadoProveedores() throws Exception {
		mockMvc.perform(get("/restaurantes/{restauranteId}/proveedores", TEST_RESTAURANTE_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("proveedores"))
		.andExpect(view().name("proveedores/listadoProveedores"));
	}
	
	
	//CREATION TESTS
	
	@WithMockUser(value = "spring")
    @Test
    void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/restaurantes/{restauranteId}/proveedores/new", TEST_RESTAURANTE_ID, TEST_PROVEEDOR_ID)).andExpect(status().isOk()).andExpect(model().attributeExists("proveedor"))
			.andExpect(view().name("proveedores/editProveedor"));
}
	
	@WithMockUser(value = "spring")
    @Test
    void testSaveProveedorSuccess() throws Exception {
		mockMvc.perform(post("/restaurantes/{restauranteId}/proveedores/save/{proveedorId}", TEST_RESTAURANTE_ID, TEST_PROVEEDOR_ID)
				.with(csrf())
				.param("name", "Fernando")
				.param("tlf", "655789809"))
			.andExpect(status().is3xxRedirection());
}
	
	
	@WithMockUser(value = "spring")
    @Test
void testSaveProveedorHasErrors() throws Exception {
	mockMvc.perform(post("/restaurantes/{restauranteId}/proveedores/save/{proveedorId}", TEST_RESTAURANTE_ID, TEST_PROVEEDOR_ID)
						.with(csrf())
						.param("name", "Fernando")
						.param("tlf", ""))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("proveedor"))
			.andExpect(model().attributeHasFieldErrors("proveedor", "tlf"))
			.andExpect(view().name("proveedores/editProveedor"));
}
	
	
	//DELETE TESTS
	@WithMockUser(authorities = "admin",username = "jacgarvel",password = "adecarry")
	@Test
	void testDeleteProveedor() throws Exception{
		mockMvc.perform(get("/restaurantes/{restauranteId}/proveedores/delete/{proveedorId}", TEST_RESTAURANTE_ID, TEST_PROVEEDOR_ID))
		.andExpect(status().isOk());
	}
	
	
	//UPDATE TESTS
	 @WithMockUser(value = "spring")
		@Test
		void testInitUpdateProveedorForm() throws Exception {
			mockMvc.perform(get("/restaurantes/{restauranteId}/proveedores/{proveedorId}/edit", TEST_RESTAURANTE_ID,TEST_PROVEEDOR_ID))
					.andExpect(status().isOk())
					.andExpect(model().attributeExists("proveedor"))
					.andExpect(model().attribute("proveedor", hasProperty("name", is("Ejemplo"))))
					.andExpect(model().attribute("proveedor", hasProperty("tlf", is("684312254"))))
					.andExpect(view().name("proveedores/editProveedor"));
		}

	        @WithMockUser(value = "spring")
		@Test
		void testProcessUpdateProveedorFormSuccess() throws Exception {
			mockMvc.perform(post("/restaurantes/{restaurante1}/proveedores/{proveedorId}/edit", TEST_RESTAURANTE_ID,TEST_PROVEEDOR_ID)
								.with(csrf())
								.param("name", "Fernando")
								.param("tlf", "655789809"))
					.andExpect(status().is3xxRedirection())
					.andExpect(view().name("redirect:/restaurantes/{restauranteId}/proveedores/{proveedorId}"));
		}

	        @WithMockUser(value = "spring")
		@Test
		void testProcessUpdateProveedorFormHasErrors() throws Exception {
			mockMvc.perform(post("/restaurantes/{restauranteId}/proveedores/{proveedorId}/edit", TEST_RESTAURANTE_ID,TEST_PROVEEDOR_ID)
								.with(csrf())
								.param("name", "")
								.param("tlf", "655789809"))
					.andExpect(status().isOk())
					.andExpect(model().attributeHasErrors("proveedor"))
					.andExpect(model().attributeHasFieldErrors("proveedor", "name"))
					.andExpect(view().name("proveedores/editProveedor"));
		}

}
