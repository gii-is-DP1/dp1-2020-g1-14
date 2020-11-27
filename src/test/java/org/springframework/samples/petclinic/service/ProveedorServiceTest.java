package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Proveedor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ProveedorServiceTest {
	@Autowired
	private ProveedorService proveedorService;
	
	@Test
	public void testCountWithInitialData() {
		int count = proveedorService.proveedorCount();
		assertEquals(count, 3);
	}
	@Test
	@Transactional
	public void shouldInsertProveedor() {
	Collection<Proveedor> proveedores = (Collection<Proveedor>) this.proveedorService.findAll();
		int found = proveedores.size();
		Optional<Proveedor> proveedor=proveedorService.findProveedorById(1);

		Proveedor p = new Proveedor();
		p.setName("Juan");
		p.setTlf("954873612");
		
			
                               
                
		this.proveedorService.save(p);
		assertThat(proveedor.get().getId().longValue()).isNotEqualTo(0);

		proveedores = (Collection<Proveedor>) this.proveedorService.findAll();
		assertThat(proveedores.size()).isEqualTo(found+1);
	}
}
