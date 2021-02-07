package org.springframework.samples.petclinic.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.model.Reclamacion;
import org.springframework.samples.petclinic.service.ProductoService;
import org.springframework.samples.petclinic.service.ReclamacionService;
import org.springframework.samples.petclinic.service.RestauranteService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("/restaurantes/{restauranteId}/reclamaciones")
public class ReclamacionController {

	@Autowired
	private ReclamacionService reclamacionService;
	@Autowired
	private RestauranteService resService;
	@Autowired
	private ProductoService	productoService;

	@GetMapping()
	public String listadoReclamaciones(@PathVariable("restauranteId") int restauranteId,ModelMap modelMap) {
		String vista="reclamaciones/listadoReclamaciones";
		Iterable<Reclamacion> reclamaciones = reclamacionService.findReclamacionByRestauranteId(restauranteId);
		modelMap.addAttribute("reclamaciones", reclamaciones);
		modelMap.addAttribute("restauranteId", restauranteId);
		log.info("Mostrando listado de reclamaciones");
		return vista;
	}

	@GetMapping(path="/new")
	public String crearReclamacion(@PathVariable("restauranteId") int restauranteId,ModelMap modelMap) {
		String view ="reclamaciones/crearReclamacion";
		modelMap.addAttribute("reclamacion", new Reclamacion());
		modelMap.addAttribute("restauranteId", restauranteId);
		modelMap.addAttribute("restaurante", resService.findRestauranteById(restauranteId).get());
		log.info("Inicialización de creación de reclamacion");
		return view;
	}

	@PostMapping(path="/save")
	public String guardarReclamacion(@PathVariable("restauranteId") int restauranteId, @Valid Reclamacion reclamacion, BindingResult result, ModelMap modelMap) {
		String view="reclamaciones/listadoReclamaciones";
		if(result.hasErrors())
		{
			modelMap.addAttribute("reclamacion", reclamacion);
			modelMap.addAttribute("restauranteId", restauranteId);
			log.warn("Error de validación");
			return "/reclamaciones/crearReclamacion";

		}else {
			reclamacionService.save(reclamacion);
			modelMap.addAttribute("message", "Reclamación guardada");
			view="restaurantes/restauranteDetails";
			Iterable<Producto> productos = productoService.findProductosByRestauranteId(restauranteId);
			modelMap.addAttribute("productos",productos);
			modelMap.addAttribute("restaurante",this.resService.findRestauranteById(restauranteId).get());
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String name = auth.getName(); //get logged in username
			modelMap.addAttribute("username", name);
			log.info("Reclamacion creada");
			return view;
		}
	}	

}
