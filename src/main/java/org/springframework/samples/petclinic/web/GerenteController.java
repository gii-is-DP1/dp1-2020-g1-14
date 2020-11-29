package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Gerente;
import org.springframework.samples.petclinic.service.GerenteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gerentes")
public class GerenteController {

	@Autowired
	private GerenteService gerenteService;
	@GetMapping()
	public String listadoGerentes(ModelMap modelMap) {
		String vista ="gerentes/listadoGerentes";
		Iterable<Gerente> gerentes = gerenteService.findAll();
		modelMap.addAttribute("gerentes",gerentes);
		return vista;
	}
	
	@GetMapping(path="/new")
	public String crearGerente(ModelMap modelMap) {
		String view ="gerentes/editGerente";
		modelMap.addAttribute("gerente", new Gerente());
		return view;
	}
	
	@PostMapping(path="/save")
	public String salvarGerente(@Valid Gerente gerente, BindingResult result, ModelMap modelMap) {
	String view="gerentes/listadoGerentes";
	if(result.hasErrors())
	{
		modelMap.addAttribute("gerentes", gerente);
		return "gerentes/editGerente";
	}else {
		gerenteService.save(gerente);
		modelMap.addAttribute("message", "Event successfully saved!");
		view=listadoGerentes(modelMap);
	}
	return view;
	}
	@GetMapping(path="delete/{gerenteId}")
	public String borrarGerente(@PathVariable("gerenteId") int gerenteId, ModelMap modelMap) {
	String view="gerentes/listadoGerentes";
	Optional<Gerente> gerente=gerenteService.findGerenteById(gerenteId);
	if(gerente.isPresent()) {
		gerenteService.delete(gerente.get());
		modelMap.addAttribute("message","Event succesfully deleted!");
		view=listadoGerentes(modelMap);
	}else {
		modelMap.addAttribute("message","Event not found!");
		view=listadoGerentes(modelMap);
	}
	return view;
}	
	
}
