package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Reclamacion;
import org.springframework.samples.petclinic.service.ReclamacionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
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
}
