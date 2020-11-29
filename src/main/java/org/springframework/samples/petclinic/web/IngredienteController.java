package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Ingrediente;
import org.springframework.samples.petclinic.service.IngredienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ingredientes")
public class IngredienteController {

	@Autowired
	private IngredienteService ingService;
	
	/*@GetMapping()
	public String listadoIngredientesPorRestaurante(ModelMap modelMap, int id) {
		String vista = "ingredientes/listadoIngredientes";
		Iterable<Ingrediente> ingredientes = ingService.findIngredientesByRestaurante(id);
		modelMap.addAttribute("ingredientes", ingredientes);
		return vista;
	}*/
	
	@GetMapping()
	public String listadoIngredientes(ModelMap modelMap) {
		String vista = "ingredientes/listadoIngredientes";
		Iterable<Ingrediente> ingredientes = ingService.findAll();
		modelMap.addAttribute("ingredientes", ingredientes);
		return vista;
	}
	
	@GetMapping(path="/new")
	public String añadirIngrediente(ModelMap modelMap) {
		String vista = "ingredientes/editarIngrediente";
		modelMap.addAttribute("ingrediente", new Ingrediente());
		return vista;
	}
	
	@PostMapping(path="/save")
	public String guardarIngrediente(@Valid Ingrediente ingrediente, BindingResult result, ModelMap modelMap) {
		String vista = "ingredientes/listadoIngredientes";
		if(result.hasErrors()) {
			modelMap.addAttribute("ingrediente", ingrediente);
			return "ingredientes/editarIngrediente";
		}else {
			ingService.save(ingrediente);
			modelMap.addAttribute("mensaje", "Ingrediente guardado");
			vista = listadoIngredientes(modelMap);
		}
		return vista;		
	}
	
	@GetMapping(path="/delete/{ingredienteId}")
	public String borrarIngrediente(@PathVariable("ingredienteId") int id, ModelMap modelMap) {
		String vista = "ingredientes/listadoIngredientes";
		Optional<Ingrediente> ingrediente = ingService.findIngredienteById(id);
		if(ingrediente.isPresent()) {
			ingService.delete(ingrediente.get());
			modelMap.addAttribute("message","Event succesfully deleted!");
		}else {
			modelMap.addAttribute("message","Event not found!");
		}
		vista = listadoIngredientes(modelMap);
		return vista;
	}
}
