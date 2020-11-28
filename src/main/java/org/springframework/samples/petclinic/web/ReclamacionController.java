package org.springframework.samples.petclinic.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Reclamacion;
import org.springframework.samples.petclinic.service.ReclamacionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reclamaciones")
public class ReclamacionController {
	
	@Autowired
	private ReclamacionService reclamacionService;
	
	@GetMapping()
	public String listadoReclamaciones(ModelMap modelMap) {
		String vista="reclamaciones/listadoReclamaciones";
		Iterable<Reclamacion> reclamaciones = reclamacionService.findAll();
		modelMap.addAttribute("reclamaciones", reclamaciones);
		return vista;
	}
	
	@GetMapping(path="/new")
	public String crearReclamacion(ModelMap modelMap) {
		String view ="reclamaciones/crearReclamacion";
		modelMap.addAttribute("reclamacion", new Reclamacion());
		return view;
	}
	
	@PostMapping(path="/save")
	public String salvarReclamacion(@Valid Reclamacion reclamacion, BindingResult result, ModelMap modelMap) {
	String view="reclamaciones/listadoReclamaciones";
	if(result.hasErrors())
	{
		modelMap.addAttribute("reclamacion", reclamacion);
		return "reclamaciones/crearReclamacion";
		
	}else {
		reclamacionService.save(reclamacion);
		modelMap.addAttribute("message", "Event successfully saved!");
		view=listadoReclamaciones(modelMap);
	}
	return view;
	}	
	
}
