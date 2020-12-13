package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Gerente;
import org.springframework.samples.petclinic.model.Ingrediente;
import org.springframework.samples.petclinic.model.Medida;
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

	private static final String VIEWS_INGREDIENTES_CREATE_OR_UPDATE_FORM = "ingredientes/editarIngrediente";
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
		EnumSet<Medida> set = EnumSet.allOf(Medida.class);
		ArrayList<String> medidas = new ArrayList<String>();
		for(Medida medida: set) {
			medidas.add(medida.toString());
		}
		modelMap.addAttribute("medidas", medidas);
		modelMap.addAttribute("ingrediente", new Ingrediente());
		return vista;
	}
	
	@PostMapping(path="/save")
	public String guardarIngrediente(@Valid Ingrediente ingrediente, BindingResult result, ModelMap modelMap) {
		String vista = "ingredientes/listadoIngredientes";
		if(result.hasErrors()) {
			EnumSet<Medida> set = EnumSet.allOf(Medida.class);
			ArrayList<String> medidas = new ArrayList<String>();
			for(Medida medida: set) {
				medidas.add(medida.toString());
			}
			modelMap.addAttribute("medidas", medidas);
			modelMap.addAttribute("ingrediente", ingrediente);
			return "ingredientes/editarIngrediente";
		}else {
			ingService.save(ingrediente);
			modelMap.addAttribute("mensaje", "Ingrediente guardado");
			vista = listadoIngredientes(modelMap);
		}
		return vista;		
	}
	
	@GetMapping(path = "/{ingredienteId}/edit")
	public String initUpdateForm(@PathVariable("ingredienteId") int ingredienteId, ModelMap model) {
		Ingrediente ingrediente = this.ingService.findIngredienteById(ingredienteId).get();
		EnumSet<Medida> set = EnumSet.allOf(Medida.class);
		ArrayList<String> medidas = new ArrayList<String>();
		for(Medida medida: set) {
			medidas.add(medida.toString());
		}
		model.addAttribute("medidas", medidas);
		model.addAttribute(ingrediente);
		return VIEWS_INGREDIENTES_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/{gerenteId}/edit")
	public String processUpdateOwnerForm(@Valid Ingrediente ingrediente, BindingResult result,
			@PathVariable("ingredienteId") int ingredienteId) {
		if (result.hasErrors()) {
			return VIEWS_INGREDIENTES_CREATE_OR_UPDATE_FORM;
		}
		else {
			ingrediente.setId(ingredienteId);
			this.ingService.save(ingrediente);
			return "redirect:/ingredientes/{ingredienteId}";
		}
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
