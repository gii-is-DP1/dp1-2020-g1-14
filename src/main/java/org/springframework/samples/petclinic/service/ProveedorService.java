package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Proveedor;
import org.springframework.samples.petclinic.repository.ProveedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProveedorService {
	@Autowired
	private ProveedorRepository proveedorRepo;
	
	@Transactional
	public int proveedorCount() {
		return (int) proveedorRepo.count();
	}
	
	@Transactional
	public Iterable<Proveedor> findAll() {
		return proveedorRepo.findAll();
		
	}
	
	@Transactional(readOnly=true)
	public Optional<Proveedor> findProveedorById(int id) {
		return proveedorRepo.findById(id);
	}
	
	@Transactional
	public void save(Proveedor proveedor) {
		proveedorRepo.save(proveedor);
	}
	
	@Transactional
	public void delete(Proveedor proveedor) {
		proveedorRepo.delete(proveedor);
	}
}	
