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



}
