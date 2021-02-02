package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Gerente;
import org.springframework.samples.petclinic.model.Ingrediente;
import org.springframework.samples.petclinic.model.Medida;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.service.IngredienteService;
import org.springframework.samples.petclinic.service.RestauranteService;
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
@RequestMapping("/restaurantes/{restaurantesId}/ingredientes")
public class IngredienteController {

	private static final String VIEWS_INGREDIENTES_CREATE_OR_UPDATE_FORM = "ingredientes/editarIngrediente";
	@Autowired
	private IngredienteService ingService;
	@Autowired
	private RestauranteService resService;
	
	@GetMapping()
	public String listadoIngredientes(@PathVariable("restaurantesId") int restauranteId, ModelMap modelMap) {
		String vista = "ingredientes/listadoIngredientes";
		Restaurante restaurante = resService.findRestauranteById(restauranteId).get();
		modelMap.addAttribute("restaurante", restaurante);
		log.info("Mostrando listado de ingredientes");
		return vista;
	}
	
	@GetMapping(path="/new")
	public String añadirIngrediente(@PathVariable("restaurantesId") int restauranteId,ModelMap modelMap) {
		String vista = "ingredientes/editarIngrediente";
		EnumSet<Medida> set = EnumSet.allOf(Medida.class);
		ArrayList<String> medidas = new ArrayList<String>();
		for(Medida medida: set) {
			medidas.add(medida.toString());
		}
		modelMap.addAttribute("medidas", medidas);
		modelMap.addAttribute("restaurante", resService.findRestauranteById(restauranteId).get());
		modelMap.addAttribute("ingrediente", new Ingrediente());
		log.info("Inicialización de creación de ingrediente");
		
		
		return vista;
	}
	
	@PostMapping(path="/save")
	public String guardarIngrediente(@PathVariable("restaurantesId") int restauranteId, @Valid Ingrediente ingrediente, BindingResult result, ModelMap modelMap) {
		if(result.hasErrors()) {
			EnumSet<Medida> set = EnumSet.allOf(Medida.class);
			ArrayList<String> medidas = new ArrayList<String>();
			for(Medida medida: set) {
				medidas.add(medida.toString());
			}
			modelMap.addAttribute("medidas", medidas);
			modelMap.addAttribute("restaurante", resService.findRestauranteById(restauranteId).get());
			modelMap.addAttribute("ingrediente", ingrediente);
			log.warn("Error de validación");
			return "ingredientes/editarIngrediente";
		}else {
			ingService.save(ingrediente);
			modelMap.addAttribute("mensaje", "Ingrediente guardado");
			String vista = listadoIngredientes(restauranteId, modelMap);
			log.info("Restaurante creado");
			return vista;	
		}
			
	}
	
	@GetMapping(path = "/{ingredienteId}/edit")
	public String initUpdateForm(@PathVariable("restaurantesId") int restauranteId, @PathVariable("ingredienteId") int ingredienteId, ModelMap model) {
		Restaurante restaurante = this.resService.findRestauranteById(restauranteId).get();
		Ingrediente ingrediente = this.ingService.findIngredienteById(ingredienteId).get();
		EnumSet<Medida> set = EnumSet.allOf(Medida.class);
		ArrayList<String> medidas = new ArrayList<String>();
		for(Medida medida: set) {
			medidas.add(medida.toString());
		}
		model.addAttribute("medidas", medidas);
		model.addAttribute(ingrediente);
		model.addAttribute(restaurante);
		log.info("Inicialización de edición de ingrediente");
		return VIEWS_INGREDIENTES_CREATE_OR_UPDATE_FORM;
	}
	
//	@PostMapping(value = "/{ingredienteId}/edit")
//	public String processUpdateOwnerForm(@Valid Ingrediente ingrediente, BindingResult result,
//			@PathVariable("ingredienteId") int ingredienteId) {
//		if (result.hasErrors()) {
//			return VIEWS_INGREDIENTES_CREATE_OR_UPDATE_FORM;
//		}
//		else {
//			ingrediente.setId(ingredienteId);
//			this.ingService.save(ingrediente);
//			return "redirect:/ingredientes/{ingredienteId}";
//		}
//	}
	
	@GetMapping(path="/delete/{ingredienteId}")
	public String borrarIngrediente(@PathVariable("restaurantesId") int restauranteId, @PathVariable("ingredienteId") int id, ModelMap modelMap) {
		String vista = "ingredientes/listadoIngredientes";
		Optional<Ingrediente> ingrediente = ingService.findIngredienteById(id);
		if(ingrediente.isPresent()) {
			ingService.delete(ingrediente.get());
			modelMap.addAttribute("message","Event succesfully deleted!");
			log.info("ingrediente borrado");
		}else {
			modelMap.addAttribute("message","Event not found!");
			log.warn("ingrediente no encontrado");
		}
		vista = listadoIngredientes(restauranteId, modelMap);
		
//		vista = "redirect:/restaurantes/{restauranteId}/ingredientes";
		return vista;
	}
}
