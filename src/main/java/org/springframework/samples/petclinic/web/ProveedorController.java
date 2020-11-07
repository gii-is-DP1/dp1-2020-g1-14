package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

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

@Controller
@RequestMapping("/proveedores")
public class ProveedorController {
	
	@Autowired
	private ProveedorService proveedorService;
	@GetMapping()
	public String listadoProveedores(ModelMap modelMap) {
		String vista ="proveedores/listadoProveedores";
		Iterable<Proveedor> proveedores = proveedorService.findAll();
		modelMap.addAttribute("proveedores",proveedores);
		return vista;
	}
	
	@GetMapping(path="/new")
	public String crearProveedor(ModelMap modelMap) {
		String view ="proveedores/editProveedor";
		modelMap.addAttribute("proveedor", new Proveedor());
		return view;
	}
	@PostMapping(path="/save")
	public String salvarProveedor(@Valid Proveedor proveedor, BindingResult result, ModelMap modelMap) {
	String view="proveedores/listadoProveedores";
	if(result.hasErrors())
	{
		modelMap.addAttribute("proveedor", proveedor);
		return "proveedores/editProveedor";
	}else {
		proveedorService.save(proveedor);
		modelMap.addAttribute("message", "Event successfully saved!");
		view=listadoProveedores(modelMap);
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
	}else {
		modelMap.addAttribute("message","Event not found!");
		view=listadoProveedores(modelMap);
	}
	return view;
}
}

