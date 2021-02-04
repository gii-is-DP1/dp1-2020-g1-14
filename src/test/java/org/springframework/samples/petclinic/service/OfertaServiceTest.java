package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Oferta;
import org.springframework.samples.petclinic.model.Proveedor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))

public class OfertaServiceTest {
	
	@Autowired
	private OfertaService ofertaService;
	
	@Test
	public void testCountWithInitialData() {
		int count = ofertaService.ofertaCount();
		assertEquals(count, 4);
	}
	
	@Test
	@Transactional
	public void shouldInsertOferta() {
	Collection<Oferta> ofertas = (Collection<Oferta>) this.ofertaService.findAll();
		int found = ofertas.size();
		Optional<Oferta> oferta=ofertaService.findOfertaById(1);

		Oferta p = new Oferta();
		p.setDescripcion("Oferta 2");
		p.setDescuento(2.);
		p.setMinPrice(2.);
		p.setExclusivo(true);
		
			
        this.ofertaService.save(p);
		assertThat(oferta.get().getId().longValue()).isNotEqualTo(0);

		ofertas = (Collection<Oferta>) this.ofertaService.findAll();
		assertThat(ofertas.size()).isEqualTo(found+1);
	}
	
	@Test
	@Transactional
	public void shouldDeleteOferta() {
		
		Oferta p = new Oferta();
		p.setDescripcion("Oferta5");
		p.setDescuento(2.);
		p.setMinPrice(2.);
		p.setExclusivo(true);
		this.ofertaService.save(p);
	
		Collection<Oferta> elementoAñadido = (Collection<Oferta>) this.ofertaService.findAll();
		int found = elementoAñadido.size();
		this.ofertaService.delete(p);
		Collection<Oferta> elementoEliminado = (Collection<Oferta>) this.ofertaService.findAll();
		assertThat(elementoEliminado.size()).isEqualTo(found-1);
	}
	
	public void shouldFindOfertarWithCorrectId() {
		Optional<Oferta> oferta = this.ofertaService.findOfertaById(1);
		assertThat(oferta.get().getDescripcion()).isEqualTo("Oferta numero 1");
		
	}
	
	@Test
	@Transactional
	public void shouldUpdateOferta() {
		
		Optional<Oferta> oferta = this.ofertaService.findOfertaById(1);
		String newDesc = "Oferta 1";
		oferta.get().setDescripcion(newDesc);
		this.ofertaService.save(oferta.get());
	
		oferta = this.ofertaService.findOfertaById(1);
		assertThat(oferta.get().getDescripcion()).isEqualTo(newDesc);
		
	}
	
}
