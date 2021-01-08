package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Pedido;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.service.PedidoService;
import org.springframework.samples.petclinic.service.exceptions.CantCancelOrderException;
import org.springframework.samples.petclinic.service.exceptions.MinOrderPriceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {
	
	@Autowired
	private PedidoService pedidoService;
	
	@GetMapping()
	public String listadoPedidos(ModelMap modelMap) {
		String vista = "pedidos/listadoPedidos";
		Iterable<Pedido> pedidos = pedidoService.findAll();
		modelMap.addAttribute("pedidos",pedidos);
		return vista;
		
	}
	
	
	@GetMapping(path="/new")
	public String nuevoPedido(ModelMap modelMap) {
		String view="pedidos/nuevoPedido";
		modelMap.addAttribute("pedido",new Pedido());
		return view;
		}
	
	@PostMapping(path="/order")
	public String tramitarPedido(@Valid Pedido pedido, BindingResult result, ModelMap modelMap) {
		String view ="pedidos/listadoPedidos";
		/*Double precio = pedidoService.getTotalPrice(pedido.getId());*/
		if(result.hasErrors())
		{
			modelMap.addAttribute("pedido",pedido);
			return "pedidos/nuevoPedido";
		}else {
			/*pedido.setPrice(precio);*/
			pedidoService.save(pedido);
			modelMap.addAttribute("message","Pedido encargado con éxito");
			view=listadoPedidos(modelMap);
		}
		return view;
	}

	@GetMapping(path="cancel/{pedidoId}")
	public String cancelarPedido(@PathVariable("pedidoId") int pedidoId, ModelMap modelMap) {
	String view="pedidos/listadoPedidos";
	Optional<Pedido> pedido= pedidoService.findPedidoById(pedidoId);
	if(pedido.isPresent()) {
		try {
			pedidoService.delete(pedido.get());
		} catch (CantCancelOrderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		modelMap.addAttribute("message","Pedido cancelado satisfactoriamente" );
		view=listadoPedidos(modelMap);
	}else {
		modelMap.addAttribute("message","No se encontró el pedido");
		view=listadoPedidos(modelMap);
	}
	return view;
}
	@ModelAttribute("productos")
	public Iterable<Producto> producto() {
		return this.pedidoService.getAllProductos();
	}
}