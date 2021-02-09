package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Ingrediente;
import org.springframework.samples.petclinic.model.Medida;
import org.springframework.samples.petclinic.model.Proveedor;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.service.IngredienteService;
import org.springframework.samples.petclinic.service.ProveedorService;
import org.springframework.samples.petclinic.service.RestauranteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/restaurantes/{restauranteId}/ingredientes")
public class IngredienteController {

	private static final String VIEWS_INGREDIENTES_CREATE_OR_UPDATE_FORM = "ingredientes/editarIngrediente";
	@Autowired
	private IngredienteService ingService;
	@Autowired
	private RestauranteService resService;
	@Autowired
	private ProveedorService proveedorService;
	
	@GetMapping()
	public String listadoIngredientes(@PathVariable("restauranteId") int restauranteId, ModelMap modelMap) {
		String vista = "ingredientes/listadoIngredientes";
		Iterable<Ingrediente> ingredientes = ingService.findIngredientesByRestauranteId(restauranteId);
		modelMap.addAttribute("ingredientes", ingredientes);
		modelMap.addAttribute("restauranteId", restauranteId);
		log.info("Mostrando listado de ingredientes");
		return vista;
	}
	
	@GetMapping(path="/new")
	public String añadirIngrediente(@PathVariable("restauranteId") int restauranteId,ModelMap modelMap) {
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
	public String guardarIngrediente(@PathVariable("restauranteId") int restauranteId, @Valid Ingrediente ingrediente, BindingResult result, ModelMap modelMap) {
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

			log.info("Restaurante creado");
			return "redirect:/restaurantes/{restauranteId}/ingredientes";
		}
			
	}
	
	@GetMapping(path = "/{ingredienteId}/edit")
	public String initUpdateForm(@PathVariable("restauranteId") int restauranteId, @PathVariable("ingredienteId") int ingredienteId, ModelMap model) {
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
	
	@PostMapping(path="/save/{ingredienteId}")
	public String guardarIngrediente(@PathVariable("restauranteId") int restauranteId, @Valid Ingrediente ingrediente, BindingResult result, ModelMap modelMap,
			@RequestParam(value = "version", required = false) Integer version, @PathVariable("ingredienteId") int ingredienteId) {
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
			Ingrediente ingToUpdate = ingService.findIngredienteById(ingredienteId).get();
			if(ingToUpdate.getVersion() != version) {
				log.error("Las versiones de ingrediente no coinciden: ingToUpdate version " + ingToUpdate.getVersion() + " ingrediente version "+version);
				EnumSet<Medida> set = EnumSet.allOf(Medida.class);
				ArrayList<String> medidas = new ArrayList<String>();
				for(Medida medida: set) {
					medidas.add(medida.toString());
				}
				modelMap.addAttribute("medidas", medidas);
				modelMap.addAttribute("restaurante", resService.findRestauranteById(restauranteId).get());
				modelMap.addAttribute("ingrediente", ingrediente);
				modelMap.addAttribute("message", "Ha ocurrido un error inesperado por favor intentalo de nuevo");
				return listadoIngredientes(restauranteId, modelMap);
			}
			ingService.save(ingrediente);
			modelMap.addAttribute("mensaje", "Ingrediente guardado");
      

			log.info("Restaurante creado");
			return "redirect:/restaurantes/{restauranteId}/ingredientes";
		}
			
	}
	
	@GetMapping(path="/delete/{ingredienteId}")
	public String borrarIngrediente(@PathVariable("restauranteId") int restauranteId, @PathVariable("ingredienteId") int id, ModelMap modelMap) {
		String vista;
		Optional<Ingrediente> ingrediente = ingService.findIngredienteById(id);
		if(ingrediente.isPresent()) {
			ingService.delete(ingrediente.get());
			modelMap.addAttribute("message","ingrediente succesfully deleted!");
			log.info("ingrediente borrado");
		}else {
			modelMap.addAttribute("message","ingrediente not found!");
			log.warn("ingrediente no encontrado");
		}
		vista = listadoIngredientes(restauranteId, modelMap);


		return vista;
	}
	
	@GetMapping(path="/{proveedorId}")
	public String proveedoresIngrediente(@PathVariable("restauranteId") int restauranteId, @PathVariable("proveedorId") int proveedorId, ModelMap modelMap) {
		Optional<Proveedor> proveedor = proveedorService.findProveedorById(proveedorId);
		if(proveedor.isPresent()) {
			Iterable<Ingrediente> ingredients = ingService.findIngredientesByRestauranteId(restauranteId);
			Set<Ingrediente> ingredientes = new HashSet<>();
			for(Ingrediente i:ingredients) {
				if(i.getProveedores().contains(proveedor.get())) {
					ingredientes.add(i);
				}
			}
			modelMap.addAttribute("ingredientes",ingredientes);
			return "ingredientes/listadoIngredientes";
		}else {
			modelMap.addAttribute("message","proveedor no encontrado!");
			log.warn("proveedor no encontrado");
			return "redirect: /restaurantes/{restauranteId}/ingredientes";
		}
	}
}
