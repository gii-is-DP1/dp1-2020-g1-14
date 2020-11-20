package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.service.RestauranteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	private RestauranteService restauranteService;
	
	@GetMapping()
	public String listadoRestaurantes(ModelMap modelMap) {
		String vista = "restaurantes/listadoRestaurantes";
		Iterable<Restaurante> restaurantes= restauranteService.findAll();
		modelMap.addAttribute("restaurantes", restaurantes);
		return vista;
	}
}
