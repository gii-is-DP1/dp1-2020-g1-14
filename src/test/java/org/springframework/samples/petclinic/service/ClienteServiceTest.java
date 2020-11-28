package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.service.exceptions.DoesNotMeetConditionsException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ClienteServiceTest {
    @Autowired
    private ClienteService clienteService;
    
    @Test
    public void testCountWithInitalData() {
        int count = clienteService.clienteCount();
        assertEquals(count,4);
    }
    //Comprobamos que un cliente que cumple las condiciones para ser socio y se cambia su valor de false a true.
    @Test
    public void checkSocioTest() {
    	Optional<Cliente> cliente = this.clienteService.findClienteById(3);
    	try {
			clienteService.checkSocio(cliente.get());
		} catch (DoesNotMeetConditionsException e) {
			e.printStackTrace();
		}
    	assertThat(cliente.get().getEsSocio() == true);
    }
    //En este test comprobamos que al pasar un cliente que no satisface las condiciciones para ser socio, se capture la excepción.
    @Test
    public void shouldThrowDoesNotMeetConditionsException() {
    	Optional<Cliente> cliente = this.clienteService.findClienteById(4);
    	try {
			clienteService.checkSocio(cliente.get());
		} catch (DoesNotMeetConditionsException e) {
			e.printStackTrace();
		}
    	Assertions.assertThrows(DoesNotMeetConditionsException.class, () -> {
    		clienteService.checkSocio(cliente.get());
    	});
    }
}