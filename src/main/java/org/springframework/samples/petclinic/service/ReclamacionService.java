package org.springframework.samples.petclinic.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Reclamacion;
import org.springframework.samples.petclinic.repository.ReclamacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReclamacionService {
	@Autowired
	private ReclamacionRepository reclamacionRepo;
	
	@Transactional
	public int reclamacionCount() {
		return(int) reclamacionRepo.count();	}
	
	@Transactional
	public Iterable<Reclamacion> findAll() {
		return reclamacionRepo.findAll();
	    }

    @Transactional(readOnly=true)
    public Optional<Reclamacion> findReclamacionById(int id) {
        return reclamacionRepo.findById(id);
    }
	
	public void save(Reclamacion reclamacion) {
		reclamacionRepo.save(reclamacion);
	}
	
}
