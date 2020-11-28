package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Propietario;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PropietarioServiceTest {
	
    @Autowired
    private PropietarioService propietarioService;
    
    @Test
    public void testCountWithInitalData() {
        int count = propietarioService.propietarioCount();
        assertEquals(count,1);
    }
    
    @Test
	void shouldFindPropietarioById() {
		Optional<Propietario> propietario = this.propietarioService.findPropietarioById(1);
		assertThat(propietario.get().getId()).isEqualTo(1);
	}

	/*@Test
	void shouldUpdatePropietario() {
		Optional<Propietario> propietario = this.propietarioService.findPropietarioById(1);
		propietario.get().setName("editNombre");
		propietario.get().setDni("12345678X");
		propietario.get().setPassword("editPass1");
		assertThat(propietario.get().getName()).startsWith("editNombre");
		assertThat(propietario.get().getDni()).isEqualTo("12345678X");
		assertThat(propietario.get().getPassword()).isEqualTo("editPass1");
	}

	@Test
	@Transactional
	void shouldNotUpdatePropietario() {
		Optional<Propietario> propietario = this.propietarioService.findPropietarioById(1);
		String oldDni = propietario.get().getDni();
		String newDni = oldDni + "X";

		propietario.get().setDni(newDni);
		assertThat(propietario.get().getDni()).isNotEqualTo(newDni);
	}*/

}
