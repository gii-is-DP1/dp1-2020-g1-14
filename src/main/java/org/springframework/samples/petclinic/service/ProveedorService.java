package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Proveedor;
import org.springframework.samples.petclinic.repository.ProveedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.qos.logback.classic.Logger;

@Service
public class ProveedorService {
	@Autowired
	private ProveedorRepository proveedorRepo;

	private static final Logger log = (Logger) LoggerFactory.getLogger(ProductoService.class);

	@Transactional
	public int proveedorCount() {

		log.info("Contando el número total de elementos");
		return (int) proveedorRepo.count();
	}

	@Transactional
	public Iterable<Proveedor> findAll() {

		log.info("Buscando todos los elementos");
		return proveedorRepo.findAll();

	}

	@Transactional(readOnly = true)
	public Optional<Proveedor> findProveedorById(int id) {

		log.info("Devolviendo elemento por su id");
		return proveedorRepo.findById(id);
	}

	@Transactional
	public void save(Proveedor proveedor) {

		log.info("Guardando elemento");
		proveedorRepo.save(proveedor);
	}

	@Transactional
	public void delete(Proveedor proveedor) {

		log.info("Eliminado un elemento");
		proveedorRepo.delete(proveedor);
	}
}
