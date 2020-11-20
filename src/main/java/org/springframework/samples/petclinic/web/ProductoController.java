package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.model.Proveedor;
import org.springframework.samples.petclinic.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/productos")
public class ProductoController {
	
	@Autowired
	private ProductoService productoService;
	@GetMapping()
	public String listadoProductos(ModelMap modelMap) {
		String vista= "productos/listadoProductos";
		Iterable<Producto> productos = productoService.findAll();
		modelMap.addAttribute("productos",productos);
		return vista;
	}
	
	@GetMapping(path="/new")
	public String crearProducto(ModelMap modelMap) {
		String view ="productos/editProducto";
		modelMap.addAttribute("producto", new Producto());
		return view;
	}
	
	@PostMapping(path="/save")
	public String salvarProducto(@Valid Producto producto, BindingResult result, ModelMap modelMap) {
	String view="productos/listadoProducto";
	if(result.hasErrors())
	{
		modelMap.addAttribute("producto", producto);
		return "productos/editProducto";
	}else {
		productoService.save(producto);
		modelMap.addAttribute("message", "Event successfully saved!");
		view=listadoProductos(modelMap);
	}
	return view;
	}
	@GetMapping(path="delete/{productoId}")
	public String borrarProducto(@PathVariable("productoId") int productoId, ModelMap modelMap) {
	String view="producto/listadoProducto";
	Optional<Producto> producto=productoService.findProductoById(productoId);
	if(producto.isPresent()) {
		productoService.delete(producto.get());
		modelMap.addAttribute("message","Event succesfully deleted!");
		view=listadoProductos(modelMap);
	}else {
		modelMap.addAttribute("message","Event not found!");
		view=listadoProductos(modelMap);
	}
	return view;
}
}
