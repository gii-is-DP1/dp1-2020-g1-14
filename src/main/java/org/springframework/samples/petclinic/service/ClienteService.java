package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.repository.ClienteRepository;
import org.springframework.samples.petclinic.service.exceptions.CantBeAMemberException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepo;
    
    @Transactional 
    public int clienteCount() {
        return (int) clienteRepo.count();
    }
    @Transactional
    public Iterable<Cliente> findAll() {
        return clienteRepo.findAll();
        
    }
    @Transactional
    public void save(Cliente cliente) {
    	clienteRepo.save(cliente);
    }
    
    @Transactional(readOnly=true)
    public Optional<Cliente> findClienteById(int id) {
        return clienteRepo.findById(id);
    }
    
    @Transactional
    public void delete(Cliente cliente) {
        clienteRepo.delete(cliente);
    }
    
    public Cliente checkSocio(Cliente cliente) throws CantBeAMemberException {
    	YearMonth startMonth = YearMonth.of(cliente.getrDate().getYear(), cliente.getrDate().getMonthValue());
    	Integer numPed = cliente.getNumPedidos();
    	Boolean socio = cliente.getEsSocio();
    	if(monthDiff(startMonth) < 6 || numPed < 10 || socio == true) {
    		throw new CantBeAMemberException();
    	}else
    	cliente.setEsSocio(true);
    	return cliente;
    }
    public Integer monthDiff(YearMonth startMonth) {
    	YearMonth endMonth = YearMonth.of(LocalDate.now().getYear(), LocalDate.now().getMonth());
    	long noOfMonths = ChronoUnit.MONTHS.between(startMonth, endMonth) +1;
    	return (int) noOfMonths;
    }
    

  /*  @Documented

    @Documented
 @Constraint(validatedBy= TelephoneNumberValidator.class)
    @Target( { ElementType.METHOD, ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TelephoneNumberConstraint{ 
    	String message() default "El número de teléfono no es válido. Debe introducir un número de teléfono válido p.ej: '954678970' o en caso de teléfono móvil: '657908756'.";
    	Class<?>[] groups() default {};
    	Class<? extends Payload>[] payload() default {};

    } */
    public Collection<Cliente> findSocios() {
    	return clienteRepo.findSocios();
    }

}