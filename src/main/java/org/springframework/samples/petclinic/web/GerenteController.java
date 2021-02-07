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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/gerentes")
public class GerenteController {

	@Autowired
	private GerenteService gerenteService;
	
	private static final String VIEWS_GERENTES_CREATE_OR_UPDATE_FORM = "gerentes/editarGerente";
	
	@GetMapping()
	public String listadoGerentes(ModelMap modelMap) {
		String vista ="gerentes/listadoGerentes";
		Iterable<Gerente> gerentes = gerenteService.findAll();
		modelMap.addAttribute("gerentes",gerentes);
		log.info("Mostrando listado gerentes");
		return vista;
	}
	
	@GetMapping(path="/new")
	public String crearGerente(ModelMap modelMap) {
		String view ="gerentes/editarGerente";
		modelMap.addAttribute("gerente", new Gerente());
		log.info("Inicialización creación de gerente");
		return view;
	}
	

	
	@PostMapping(path="/save")
	public String salvarGerente(@Valid Gerente gerente, BindingResult result, ModelMap modelMap) {
	String view="gerentes/listadoGerentes";
	if(result.hasErrors())
	{
		modelMap.addAttribute("gerentes", gerente);
		log.warn("Error de validacion");
		return "gerentes/editarGerente";
	}else {
		gerenteService.save(gerente);
		modelMap.addAttribute("message", "Event successfully saved!");
		log.info("Gerente creado");
		view=listadoGerentes(modelMap);
	}
	return view;
	}
	
	@GetMapping(path = "/{gerenteId}/edit")
	public String initUpdateForm(@PathVariable("gerenteId") int gerenteId, ModelMap model) {
		Gerente gerente = this.gerenteService.findGerenteById(gerenteId).get();
		model.addAttribute(gerente);
		log.info("Inicialización de la edición de gerente");
		return VIEWS_GERENTES_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/{gerenteId}/edit")
	public String processUpdateOwnerForm(@Valid Gerente gerente, BindingResult result,
			@PathVariable("gerenteId") int gerenteId) {
		if (result.hasErrors()) {
			log.warn("error de validación");
			return VIEWS_GERENTES_CREATE_OR_UPDATE_FORM;
		}
		else {
			gerente.setId(gerenteId);
			this.gerenteService.save(gerente);
			log.info("Guardado de cambios realizados");
			return "redirect:/gerentes/{gerenteId}";
		}
	}
	
	@GetMapping(path="delete/{gerenteId}")
	public String borrarGerente(@PathVariable("gerenteId") int gerenteId, ModelMap modelMap) {
	String view="gerentes/listadoGerentes";
	Optional<Gerente> gerente=gerenteService.findGerenteById(gerenteId);
	if(gerente.isPresent()) {
		gerenteService.delete(gerente.get());
		modelMap.addAttribute("message","Event succesfully deleted!");
		view=listadoGerentes(modelMap);
		log.info("Gerente borrado");
	}else {
		modelMap.addAttribute("message","Event not found!");
		view=listadoGerentes(modelMap);
		log.warn("Gerente no encontrado");
	}
	return view;
}	
	
}
