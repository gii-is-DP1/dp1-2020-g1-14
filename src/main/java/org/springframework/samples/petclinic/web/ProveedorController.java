package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Proveedor;
import org.springframework.samples.petclinic.service.ProveedorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
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
}
