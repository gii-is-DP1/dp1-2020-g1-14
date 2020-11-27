package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Propietario;
import org.springframework.samples.petclinic.repository.PropietarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PropietarioService {
	
	@Autowired
    private PropietarioRepository propietarioRepo;
    
    @Transactional 
    public int propietarioCount() {
        return (int) propietarioRepo.count();
    }
    @Transactional
    public Iterable<Propietario> findAll() {
        return propietarioRepo.findAll();
        
    }
    
    @Transactional(readOnly=true)
    public Optional<Propietario> findClienteById(int id) {
        return propietarioRepo.findById(id);
    }
    
    @Transactional
    public void delete(Propietario propietario) {
        propietarioRepo.delete(propietario);
    }
}
