package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Proveedor;
import org.springframework.samples.petclinic.service.ProveedorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("/proveedores")
public class ProveedorController {
	
	
	private static final Logger log = (Logger) LoggerFactory.getLogger(ProveedorController.class);
	public static final String VIEWS_PROVEEDORES_CREATE_OR_UPDATE_FORM = "proveedores/editProveedor";
	
	@Autowired
	private ProveedorService proveedorService;
	
	@GetMapping()
	public String listadoProveedores(ModelMap modelMap) {
		String vista ="proveedores/listadoProveedores";
		Iterable<Proveedor> proveedores = proveedorService.findAll();
		modelMap.addAttribute("proveedores",proveedores);
		
		log.info("Mostrando lista de proveedores");
		return vista;
	}
	
	@GetMapping(path="/new")
	public String crearProveedor(ModelMap modelMap) {
		String view ="proveedores/editProveedor";
		modelMap.addAttribute("proveedor", new Proveedor());
		
		log.info("Operación para añadir proveedor en ejecucion");
		
		return view;
	}
	
	@PostMapping(path="/save")
	public String salvarProveedor(@Valid Proveedor proveedor, BindingResult result, ModelMap modelMap) {
	String view="proveedores/listadoProveedores";
	if(result.hasErrors())
	{
		modelMap.addAttribute("proveedor", proveedor);
		
		log.error("Los datos introducidos no cumplen ciertas condiciones, revisar los campos");
		
		return "proveedores/editProveedor";
	}else {
		proveedorService.save(proveedor);
		modelMap.addAttribute("message", "Event successfully saved!");
		view=listadoProveedores(modelMap);
		
		log.info("Proveedor creado con éxito");
	}
	return view;
	}
	@GetMapping(path="delete/{proveedorId}")
	public String borrarProveedor(@PathVariable("proveedorId") int proveedorId, ModelMap modelMap) {
	String view="proveedores/listadoProveedores";
	Optional<Proveedor> proveedor=proveedorService.findProveedorById(proveedorId);
	
	if(proveedor.isPresent()) {
		proveedorService.delete(proveedor.get());
		modelMap.addAttribute("message","Event succesfully deleted!");
		view=listadoProveedores(modelMap);
		
		log.info("Proveedor eliminado con éxito");
	}else {
		modelMap.addAttribute("message","Event not found!");
		view=listadoProveedores(modelMap);
		
		log.error("No se ha encontrado el proveedor para eliminar");
	}
	return view;
}
	@GetMapping(path="/{proveedorId/edit") 
	public String initUpdateForm(@PathVariable("proveedorId") int proveedorId, ModelMap model) {
		Proveedor proveedor = this.proveedorService.findProveedorById(proveedorId).get();
		model.addAttribute(proveedor);
		
		log.info("Operación para editar proveedor en ejecucion");
		
		return VIEWS_PROVEEDORES_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value="/{proveedorId/edit")
	public String processUpdateProveedorForm(@Valid Proveedor proveedor, BindingResult result, @PathVariable("proveedorId") int proveedorId) {
		if(result.hasErrors()) {
			
			log.error("Los datos introducidos no cumplen ciertas condiciones, revisar los campos");
			
			return VIEWS_PROVEEDORES_CREATE_OR_UPDATE_FORM;
		}
		else {
			proveedor.setId(proveedorId);
			this.proveedorService.save(proveedor);
			
			log.info("Proveedor editado satisfactoriamente");
		}
		return "redirect:/proveedor/{proveedorId}";
	}
}

