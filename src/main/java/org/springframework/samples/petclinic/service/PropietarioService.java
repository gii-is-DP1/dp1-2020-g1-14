package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
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
}
