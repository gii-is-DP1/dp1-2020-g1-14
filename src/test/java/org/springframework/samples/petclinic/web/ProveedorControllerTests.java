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
import org.springframework.samples.petclinic.model.Proveedor;
import org.springframework.samples.petclinic.service.ProveedorService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {ProveedorController.class, CustomErrorController.class},
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)



public class ProveedorControllerTests {
	
	
	private static final int TEST_PROVEEDOR_ID = 1;

	
	@Autowired
	private ProveedorController proveedorController;

	@MockBean
	private ProveedorService proveedorService;

	@Autowired
	private MockMvc mockMvc;
	
	private Proveedor proveedor;
	
	@BeforeEach
	void setup() {
		
		proveedor = new Proveedor();
		proveedor.setId(TEST_PROVEEDOR_ID);
		proveedor.setName("Database");
		proveedor.setTlf("643981298");
		given(this.proveedorService.findProveedorById(TEST_PROVEEDOR_ID)).willReturn(Optional.of(proveedor));

		
}
	
    //TEST LISTAR PROVEEDORES
	@WithMockUser(value = "spring")
    @Test
    void testListadoProveedores() throws Exception {
		mockMvc.perform(get("/proveedores")).andExpect(status().isOk()).andExpect(model().attributeExists("proveedores"))
		.andExpect(status().is2xxSuccessful()).andExpect(view().name("proveedores/listadoProveedores"));
	}
	
	
	//CREATION TESTS
	
	@WithMockUser(value = "spring")
    @Test
    void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/proveedores/new")).andExpect(status().isOk()).andExpect(model().attributeExists("proveedor"))
			.andExpect(view().name("proveedores/editProveedor"));
}
	
	@WithMockUser(value = "spring")
    @Test
    void testSaveProveedorSuccess() throws Exception {
		mockMvc.perform(post("/proveedores/save/{proveedorId}", TEST_PROVEEDOR_ID)
				.with(csrf())
				.param("name", "Fernando")
				.param("tlf", "655789809"))
			.andExpect(status().is3xxRedirection());
}
	
	
	@WithMockUser(value = "spring")
    @Test
void testSaveProveedorHasErrors() throws Exception {
	mockMvc.perform(post("/proveedores/save/{proveedorId}",TEST_PROVEEDOR_ID)
						.with(csrf())
						.param("name", "Fernando")
						.param("tlf", ""))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("proveedor"))
			.andExpect(model().attributeHasFieldErrors("proveedor", "tlf"))
			.andExpect(view().name("proveedores/editProveedor"));
}
	
	
	//DELETE TESTS
	@WithMockUser(value = "spring")
	@Test
	void testDeleteProveedor() throws Exception{
		mockMvc.perform(get("/proveedores/delete/{proveedorId}", TEST_PROVEEDOR_ID))
		.andExpect(status().is2xxSuccessful());
	}
	
	
	//UPDATE TESTS
	 @WithMockUser(value = "spring")
		@Test
		void testInitUpdateProveedorForm() throws Exception {
			mockMvc.perform(get("/proveedores/{proveedorId}/edit", TEST_PROVEEDOR_ID)).andExpect(status().isOk())
					.andExpect(model().attributeExists("proveedor"))
					.andExpect(model().attribute("proveedor", hasProperty("name", is("Database"))))
					.andExpect(model().attribute("proveedor", hasProperty("tlf", is("643981298"))))
					.andExpect(view().name("proveedores/editProveedor"));
		}

	        @WithMockUser(value = "spring")
		@Test
		void testProcessUpdateProveedorFormSuccess() throws Exception {
			mockMvc.perform(post("/proveedores/{proveedorId}/edit", TEST_PROVEEDOR_ID)
								.with(csrf())
								.param("name", "Fernando")
								.param("tlf", "655789809"))
					.andExpect(status().is3xxRedirection())
					.andExpect(view().name("redirect:/proveedor/{proveedorId}"));
		}

	        @WithMockUser(value = "spring")
		@Test
		void testProcessUpdateProveedorFormHasErrors() throws Exception {
			mockMvc.perform(post("/proveedores/{proveedorId}/edit", TEST_PROVEEDOR_ID)
								.with(csrf())
								.param("name", "")
								.param("tlf", "655789809"))
					.andExpect(status().isOk())
					.andExpect(model().attributeHasErrors("proveedor"))
					.andExpect(model().attributeHasFieldErrors("proveedor", "name"))
					.andExpect(view().name("proveedores/editProveedor"));
		}

	    

	  
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
