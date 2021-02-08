package org.springframework.samples.petclinic.web;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Proveedor;
import org.springframework.samples.petclinic.model.Restaurante;
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

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("restaurantes/{restauranteId}/proveedores")
public class ProveedorController {
	
	
	private static final Logger log = (Logger) LoggerFactory.getLogger(ProveedorController.class);
	public static final String VIEWS_PROVEEDORES_CREATE_OR_UPDATE_FORM = "proveedores/editProveedor";
	
	@Autowired
	private ProveedorService proveedorService;
	@Autowired
	private RestauranteService restauranteService;
	
	//Se obtiene la lista de proveedores.
	@GetMapping()
	public String listadoProveedores(ModelMap modelMap, @PathVariable("restauranteId") int restauranteId) {
		String vista ="proveedores/listadoProveedores";
		Restaurante restaurante = restauranteService.findRestauranteById(restauranteId).get();
		Iterable<Proveedor> proveedores = restaurante.getProveedores();
		modelMap.addAttribute("proveedores",proveedores);
		modelMap.addAttribute("restauranteId",restauranteId);
		log.info("Mostrando lista de proveedores");
		return vista;
	}
	
	//Creación de un nuevo proveedor.
	@GetMapping(path="/new")
	public String crearProveedor(ModelMap modelMap, @PathVariable("restauranteId") int restauranteId) {
		String view ="proveedores/editProveedor";
		modelMap.addAttribute("proveedor", new Proveedor());
		modelMap.addAttribute("restauranteId",restauranteId);
		log.info("Operación para añadir proveedor en ejecucion");
		
		return view;
	}
	
	//Guardamos un proveedor a crear.
	@PostMapping(path="/save/{proveedorId}")
	public String salvarProveedor(@Valid Proveedor proveedor, BindingResult result, ModelMap modelMap,
			@RequestParam(value = "version", required = false) Integer version, @PathVariable("proveedorId") int proveedorId, @PathVariable("restauranteId") int restauranteId) {
	if(result.hasErrors())
	{
		modelMap.addAttribute("proveedor", proveedor);
		modelMap.addAttribute("restauranteId",restauranteId);
		log.error("Los datos introducidos no cumplen ciertas condiciones, revisar los campos");
		
		return "proveedores/editProveedor";
	}else {
		Proveedor proveedorToUpdate = proveedorService.findProveedorById(proveedorId).get();
		if(proveedorToUpdate.getVersion() != version) {
			log.error("Las versiones de proveedor no coinciden: proveedorToUpdate version " + proveedorToUpdate.getVersion() + " oferta version "+version);
			modelMap.addAttribute("proveedor", proveedor);
			modelMap.addAttribute("restauranteId",restauranteId);
			modelMap.addAttribute("message", "Ha ocurrido un error inesperado por favor intentalo de nuevo");
			return listadoProveedores(modelMap, restauranteId);
		}
		proveedorService.save(proveedor);
		modelMap.addAttribute("restauranteId",restauranteId);
		modelMap.addAttribute("message", "Proveedor successfully saved!");
		
		log.info("Proveedor creado con éxito con id "+proveedorId);
		return "redirect:/restaurantes/{restauranteId}/proveedores";
	}
	}
	
	//Guardamos un proveedor a crear.
	@PostMapping(path="/save")
	public String salvarProveedor(@Valid Proveedor proveedor, BindingResult result, ModelMap modelMap, @PathVariable("restauranteId") int restauranteId) {
		if(result.hasErrors())
		{
			modelMap.addAttribute("proveedor", proveedor);
			modelMap.addAttribute("restauranteId",restauranteId);
			log.error("Los datos introducidos no cumplen ciertas condiciones, revisar los campos");
			return "proveedores/editProveedor";
		}else {
			proveedorService.save(proveedor);
			Restaurante restaurante = restauranteService.findRestauranteById(restauranteId).get();
			Set<Proveedor> proveedores = restaurante.getProveedores();
			proveedores.add(proveedor);
			restaurante.setProveedores(proveedores);
			restauranteService.save(restaurante);
			
			Set<Restaurante> restaurantes = new HashSet<>();
			restaurantes.add(restaurante);
			proveedor.setRestaurantes(restaurantes);
			proveedorService.save(proveedor);
			modelMap.addAttribute("restauranteId",restauranteId);
			log.info("Proveedor creado con éxito");
			return "redirect:/restaurantes/{restauranteId}/proveedores";
			
		}		
	}	
	
	//Eliminamos un proveedor ya existente.
	@GetMapping(path="delete/{proveedorId}")
	public String borrarProveedor(@PathVariable("proveedorId") int proveedorId, ModelMap modelMap, @PathVariable("restauranteId") int restauranteId) {
	String view="proveedores/listadoProveedores";
	Optional<Proveedor> proveedor=proveedorService.findProveedorById(proveedorId);
	
	if(proveedor.isPresent()) {
		proveedorService.delete(proveedor.get());
		modelMap.addAttribute("restauranteId",restauranteId);
		modelMap.addAttribute("message","proveedor succesfully deleted!");
		view=listadoProveedores(modelMap, restauranteId);
		
		log.info("Proveedor eliminado con éxito");
	}else {
		modelMap.addAttribute("restauranteId",restauranteId);
		modelMap.addAttribute("message","Event not found!");
		view=listadoProveedores(modelMap, restauranteId);
		
		log.error("No se ha encontrado el proveedor para eliminar");
	}
	return view;
}
	
	//Editamos un proveedor ya existente.
	@GetMapping(path="/{proveedorId}/edit") 
	public String initUpdateForm(@PathVariable("proveedorId") int proveedorId, ModelMap model, @PathVariable("restauranteId") int restauranteId) {
		Proveedor proveedor = this.proveedorService.findProveedorById(proveedorId).get();
		model.addAttribute(proveedor);
		model.addAttribute("restauranteId",restauranteId);
		log.info("Operación para editar proveedor en ejecucion");
		
		return VIEWS_PROVEEDORES_CREATE_OR_UPDATE_FORM;
	}
	
	//Editamos un proveedor ya existente.
	@PostMapping(value="/{proveedorId}/edit")
	public String processUpdateProveedorForm(@Valid Proveedor proveedor, BindingResult result, ModelMap model, @PathVariable("proveedorId") int proveedorId, @PathVariable("restauranteId") int restauranteId) {
		if(result.hasErrors()) {
			
			log.error("Los datos introducidos no cumplen ciertas condiciones, revisar los campos");
			model.addAttribute("restauranteId",restauranteId);
			return VIEWS_PROVEEDORES_CREATE_OR_UPDATE_FORM;
		}
		else {
			proveedor.setId(proveedorId);
			this.proveedorService.save(proveedor);
			model.addAttribute("restauranteId",restauranteId);
			log.info("Proveedor editado satisfactoriamente");
		}
		return "redirect:/restaurantes/{restauranteId}/proveedores/{proveedorId}";
	}
	
	@GetMapping("/selectProveedor")
	public String seleccionProveedores(ModelMap modelMap, @PathVariable("restauranteId") int restauranteId) {
		String vista ="proveedores/listadoProveedores";
		Iterable<Proveedor> proveedores = proveedorService.findAll();
		modelMap.addAttribute("proveedores",proveedores);
		modelMap.addAttribute("restauranteId",restauranteId);
		log.info("Mostrando lista de proveedores");
		return vista;
	}
	
	@GetMapping(value="/select/{proveedorId}")
	public String processSeleccionarProveedor(ModelMap model, @PathVariable("restauranteId") int restauranteId, @PathVariable("proveedorId") int proveedorId) {
		Proveedor proveedor = proveedorService.findProveedorById(proveedorId).get();
		Restaurante restaurante = restauranteService.findRestauranteById(restauranteId).get();
		Set<Proveedor> proveedores = restaurante.getProveedores();
		proveedores.add(proveedor);
		restauranteService.save(restaurante);
		
		Set<Restaurante> restaurantes = proveedor.getRestaurantes();
		restaurantes.add(restaurante);
		proveedorService.save(proveedor);
		
		model.addAttribute("restauranteId",restauranteId);
		log.info("Proveedor asignado satisfactoriamente");

		return "redirect:/restaurantes/{restauranteId}/proveedores";
	}
}

