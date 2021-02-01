package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import org.springframework.samples.petclinic.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.service.exceptions.CantBeAMemberException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
    @CsvSource({"2020-03-11,12,false"
    	,"2020-02-13,10,false"})
    public void checkSocioTestParameterized(LocalDate fecha, int nPedidos, Boolean esSocio) throws CantBeAMemberException {
    	
    	Cliente c = new Cliente();
    	c.setEsSocio(esSocio);
    	c.setNumPedidos(nPedidos);
    	c.getUser().setrDate(fecha);
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
    	c.getUser().setrDate(fecha);
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
    	
    	User s = new User();
    	s.setUsername("cliente5");
    	s.setPassword("asdjasoipdad234");
    	s.setEnabled(true);
    	s.setrDate(LocalDate.now());
    	
    	Cliente c = new Cliente();
    	c.setUser(s);
    	c.setEsSocio(true);
    	c.setNumPedidos(10);
    	c.setTlf("954356912");
    	
    	this.clienteService.save(c);
    	assertThat(cliente.get().getId().longValue()).isNotEqualTo(0);
    	clientes= (Collection<Cliente>) this.clienteService.findAll();
    	assertThat(clientes.size()).isEqualTo(found+1);
    	
    	
    	
    	}
    @Test
    @Transactional
    public void shouldDeleteCliente() {
    	
    	User s = new User();
    	s.setUsername("cliente5");
    	s.setPassword("asdjasoipdad234");
    	s.setEnabled(true);
    	s.setrDate(LocalDate.now());
    	
    	
    	Cliente c = new Cliente();
    	c.setUser(s);
    	c.setEsSocio(true);
    	c.getUser().setPassword("asdas3343");
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
    	assertThat(cliente.get().getUser().getrDate()).isEqualTo("2020-01-01");
    	assertThat(cliente.get().getNumPedidos()).isEqualTo(12);
    	assertThat(cliente.get().getTlf()).isEqualTo("954765812");
    }
    
    @Test
    @Transactional
    public void shouldFindAllSocios() {
    	Collection<Cliente> socios = this.clienteService.findSocios();
    	Collection<Cliente> clientes = (Collection<Cliente>) this.clienteService.findAll();
    	int nSocios = (int) clientes.stream().filter(x->x.getEsSocio()==true).count();
    	assertThat(socios.size()).isEqualTo(nSocios);
    }
    
}