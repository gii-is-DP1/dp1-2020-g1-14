package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;


import java.time.LocalDate;

import java.util.Collection;
import java.util.Locale;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Pedido;
import org.springframework.samples.petclinic.service.exceptions.CantBeAMemberException;
import org.springframework.stereotype.Service;

import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.repository.ClienteRepository;
import org.springframework.samples.petclinic.service.exceptions.WrongDataProductosException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;


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
    public void checkSocioTest() throws CantBeAMemberException {
    	Optional<Cliente> cliente = this.clienteService.findClienteById(3);
    	
			clienteService.checkSocio(cliente.get());
		
    	assertThat(cliente.get().getEsSocio() == true);
    }
    //En este test comprobamos que al pasar un cliente que no satisface las condiciciones para ser socio, se capture la excepción.
    @Test
   
    public void shouldThrowDoesNotMeetConditionsException() {
    	Optional<Cliente> cliente = this.clienteService.findClienteById(4);
    	try {
			clienteService.checkSocio(cliente.get());
		} catch (CantBeAMemberException e) {
			e.printStackTrace();
		}
    	Assertions.assertThrows(CantBeAMemberException.class, () -> {
    		clienteService.checkSocio(cliente.get());
    	});
    }


    @ParameterizedTest
    @CsvSource({"2020-03-11,12,true"
    	,"2020-02-13,10,false"})
    public void checkSocioTestParameterized(LocalDate fecha, int nPedidos, Boolean esSocio) throws CantBeAMemberException {
    	Cliente c = new Cliente();
    	c.setEsSocio(esSocio);
    	c.setNumPedidos(nPedidos);
    	c.setrDate(fecha);
    	clienteService.checkSocio(c);
    	assertThat(c.getEsSocio() == true);
    }
    @ParameterizedTest
    @CsvSource({"2020-10-11,7"
    	,"2020-02-13,5",
    	"2020-10-10,9"})
    public void shouldThrowDoesNotMeetConditionsExceptionParameterized(LocalDate fecha ,int nPedidos ) {
    	Cliente c = new Cliente();
    	c.setNumPedidos(nPedidos);
    	c.setrDate(fecha);
    	try {
			clienteService.checkSocio(c);
		} catch (CantBeAMemberException e) {
			e.printStackTrace();
		}
    	Assertions.assertThrows(CantBeAMemberException.class, () -> {
    		clienteService.checkSocio(c);
    	});
    }
    @Test
    @Transactional
    public void shouldInsertCliente() {
    	Collection<Cliente> clientes = (Collection<Cliente>) this.clienteService.findAll();
    	int found = clientes.size();
    	Optional<Cliente> cliente = clienteService.findClienteById(1);
    	
    	Cliente c = new Cliente();
    	c.setName("Pepe");
    	c.setrDate(LocalDate.now());
    	c.setEsSocio(true);
    	c.setPassword("asdas3343");
    	c.setNumPedidos(12);
    	c.setTlf("954764582");
    	
    	this.clienteService.save(c);
    	assertThat(cliente.get().getId().longValue()).isNotEqualTo(0);
    	clientes= (Collection<Cliente>) this.clienteService.findAll();
    	assertThat(clientes.size()).isEqualTo(found+1);
    	
    	
    	}
    @Test
    @Transactional
    public void shouldDeleteCliente() {
    	
    	Cliente c = new Cliente();
    	c.setName("Pepe");
    	c.setrDate(LocalDate.now());
    	c.setEsSocio(true);
    	c.setPassword("asdas3343");
    	c.setNumPedidos(12);
    	c.setTlf("954764582");
    	
    	this.clienteService.save(c);
    	
    	Collection <Cliente> elementoAñadido = (Collection<Cliente>) this.clienteService.findAll(); //5
		int found = elementoAñadido.size(); //5
		this.clienteService.delete(c); //4
		Collection<Cliente> elementoEliminado = (Collection<Cliente>) this.clienteService.findAll();
		assertThat(elementoEliminado.size()).isEqualTo(found-1);
    	
    }
    
    @Test
    @Transactional
    public void shouldFindClienteWithCorrectId() {
    	Optional<Cliente> cliente = this.clienteService.findClienteById(1);
    	assertThat(cliente.get().getName()).isEqualTo("Juan");
    	assertThat(cliente.get().getrDate()).isEqualTo("2000-10-11");
    	assertThat(cliente.get().getNumPedidos()).isEqualTo(12);
    	assertThat(cliente.get().getTlf()).isEqualTo("954765812");
    }
    
}