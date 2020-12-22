package org.springframework.samples.petclinic.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.LineaPedido;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.service.LineaPedidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lineaPedidos")
public class LineaPedidoController {
	
	@Autowired
	private LineaPedidoService lineaPedidoService;
	
	
	@GetMapping()
	public String listadoLineaPedidos(ModelMap modelMap) {
		String vista ="lineaPedidos/listadoLineaPedidos";
		Iterable<LineaPedido> lineaPedidos = lineaPedidoService.findAll();
		modelMap.addAttribute("lineaPedidos",lineaPedidos);
		return vista;
	}
	@GetMapping(path="/new")
	public String crearLineaPedido(ModelMap modelMap) {
		String view ="lineaPedidos/editLineaPedido";
		modelMap.addAttribute("lineaPedido", new LineaPedido());
		return view;
	}
	
	@PostMapping(path="/save")
	public String salvarLineaPedido(@Valid LineaPedido lineaPedido, BindingResult result, ModelMap modelMap) {
	String view="lineaPedidos/listadoLineaPedidos";
	if(result.hasErrors())
	{
		modelMap.addAttribute("lineaPedido", lineaPedido);
		return "lineaPedidos/editLineaPedido";
	}else {
		lineaPedidoService.save(lineaPedido);
		modelMap.addAttribute("message", "Event successfully saved!");
		view=listadoLineaPedidos(modelMap);
	}
	return view;
	}
	@ModelAttribute("productos")
	public List<Producto> producto() {
		return this.lineaPedidoService.getAllProductos();
	}
	@ModelAttribute("nameProd")
	public List<String> nombreProductos() {
		return this.lineaPedidoService.getAllProductosName();
	}
}