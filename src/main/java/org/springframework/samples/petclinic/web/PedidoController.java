package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Estado;
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

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

	private static final Logger log = (Logger) LoggerFactory.getLogger(PedidoController.class);

	@Autowired
	private PedidoService pedidoService;

	@GetMapping()
	public String listadoPedidos(ModelMap modelMap) {
		String vista = "pedidos/listadoPedidos";
		Iterable<Pedido> pedidos = pedidoService.findAll();
		modelMap.addAttribute("pedidos", pedidos);

		log.info("Mostrando lista de pedidos");

		return vista;

	}

	@GetMapping(path = "/new")
	public String nuevoPedido(ModelMap modelMap) {
		String view = "pedidos/nuevoPedido";
		modelMap.addAttribute("pedido", new Pedido());

		log.info("Operación para añadir pedido en ejecucion");

		return view;
	}

	@PostMapping(path = "/order")
	public String tramitarPedido(@Valid Pedido pedido, BindingResult result, ModelMap modelMap) {
		String view = "pedidos/listadoPedidos";
		if (result.hasErrors()) {
			modelMap.addAttribute("pedido", pedido);

			log.error("Los datos introducidos no cumplen ciertas condiciones, revisar los campos");

			return "pedidos/nuevoPedido";
		} else {
			pedidoService.save(pedido);
			modelMap.addAttribute("message", "Pedido creado con éxito");
			view = listadoPedidos(modelMap);

			log.info("Pedido creado con éxito");

		}

		return view;
	}

	@GetMapping(path = "cancel/{pedidoId}")
	public String cancelarPedido(@PathVariable("pedidoId") int pedidoId, ModelMap modelMap) {
		String view = "pedidos/listadoPedidos";
		Optional<Pedido> pedido = pedidoService.findPedidoById(pedidoId);

		if (pedido.isPresent()) {
			try {
				pedidoService.delete(pedido.get());
			} catch (CantCancelOrderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			modelMap.addAttribute("message", "Pedido cancelado satisfactoriamente");
			view = listadoPedidos(modelMap);

			log.info("Pedido eliminado con éxito");
		} else {
			modelMap.addAttribute("message", "No se encontró el pedido");
			view = listadoPedidos(modelMap);

			log.error("No se ha encontrado el pedido para eliminar");
		}
		return view;
	}

	@ModelAttribute("productos")
	public Iterable<Producto> producto() {
		return this.pedidoService.getAllProductos();

	}

	@GetMapping(path = "/refresh/{pedidoId}")
	public String refreshPrice(@PathVariable("pedidoId") int pedidoId, ModelMap modelMap) {
		String view = "pedidos/listadoPedidos";
		Optional<Pedido> pedido = pedidoService.findPedidoById(pedidoId);
		Double price = pedidoService.getTotalPrice(pedido.get().getId());
		pedido.get().setCheckea(true);
		pedido.get().setPrice(price);
		modelMap.addAttribute("pedido", pedido);
		view = listadoPedidos(modelMap);

		log.info("Precios actualizados con éxito");
		return view;
	}

	@GetMapping(path = "/verify/{pedidoId}")
	public String verificaPedido(@PathVariable("pedidoId") int pedidoId, ModelMap modelMap) {
		String view = "pedidos/listadoPedidos";
		Optional<Pedido> pedido = pedidoService.findPedidoById(pedidoId);

		if (pedido.get().getEstado() == Estado.SIN_VERIFICAR) {
			
			if (pedido.get().getPrice() >= 10) {

				if (pedido.get().getCheckea() == true) {
					pedido.get().setEstado(Estado.PROCESANDO);
					modelMap.addAttribute("pedido", pedido);
					modelMap.addAttribute("message",
							"Se ha realizado el pedido satisfactoriamente y ahora está se está procesando en nuestra central.");
					view = listadoPedidos(modelMap);

					log.info("Se ha realizado el pedido");

				} else {

					modelMap.addAttribute("pedido", pedido);
					modelMap.addAttribute("message", "¡Refresca el pedido antes de verificarlo!");
					view = listadoPedidos(modelMap);
				}
			} else {
				modelMap.addAttribute("pedido", pedido);
				modelMap.addAttribute("message", "¡El precio del pedido debe de ser mayor o igual a 10!");
				view = listadoPedidos(modelMap);
			}
			
		}else {
			modelMap.addAttribute("message", "El pedido debe se debe de estar sin verificar previamente a procesarlo");
			view = listadoPedidos(modelMap);

			log.error("No se puede pasar del estado actual a PROCESANDO");
			view=listadoPedidos(modelMap);
		}
		return view;

	}

	////////////////////////////////////////////////////////////////// BOTONES PARA
	////////////////////////////////////////////////////////////////// GERENTES///////////////////////////////////////////

	@GetMapping(path = "/preparando/{pedidoId}")
	public String actualizadoPedidoPreparando(@PathVariable("pedidoId") int pedidoId, ModelMap modelMap) {
		String view = "pedidos/listadoPedidos";
		Optional<Pedido> pedido = pedidoService.findPedidoById(pedidoId);

		if (pedido.get().getEstado() == Estado.PREPARANDO) {

			modelMap.addAttribute("message", "El pedido ya se encuentra en este estado");
			view = listadoPedidos(modelMap);

			log.error("El pedido ya se encuentra en este estado.");
			return view;

		} else if (pedido.get().getEstado() != Estado.PROCESANDO) {

			modelMap.addAttribute("message", "El pedido debe se debe de estar procesando previamente a prepararlo");
			view = listadoPedidos(modelMap);

			log.error("No se puede pasar del estado actual a PREPARANDO");
			return view;
		}
		pedido.get().setEstado(Estado.PREPARANDO);
		modelMap.addAttribute("pedido", pedido);
		modelMap.addAttribute("message", "Se ha actualizado el estado del pedido");
		view = listadoPedidos(modelMap);

		log.info("Se ha actualizado el estado del pedido a Preparando");
		return view;
	}

	@GetMapping(path = "/reparto/{pedidoId}")
	public String actualizadoPedidoReparto(@PathVariable("pedidoId") int pedidoId, ModelMap modelMap) {
		String view = "pedidos/listadoPedidos";
		Optional<Pedido> pedido = pedidoService.findPedidoById(pedidoId);

		if (pedido.get().getEstado() == Estado.EN_REPARTO) {

			modelMap.addAttribute("message", "El pedido ya se encuentra en este estado");
			view = listadoPedidos(modelMap);

			log.error("El pedido ya se encuentra en este estado.");
			return view;

		} else if (pedido.get().getEstado() != Estado.PREPARANDO) {

			modelMap.addAttribute("message",
					"El pedido debe se debe de estar preparando previamente a estar en reparto");
			view = listadoPedidos(modelMap);

			log.error("No se puede pasar del estado actual a EN_REPARTO");
			return view;
		}
		pedido.get().setEstado(Estado.EN_REPARTO);
		modelMap.addAttribute("pedido", pedido);
		modelMap.addAttribute("message", "Se ha actualizado el estado del pedido");
		view = listadoPedidos(modelMap);

		log.info("Se ha actualizado el estado del pedido a EN_REPARTO");
		return view;
	}

	@GetMapping(path = "/recibido/{pedidoId}")
	public String actualizadoPedidoRecibido(@PathVariable("pedidoId") int pedidoId, ModelMap modelMap) {
		String view = "pedidos/listadoPedidos";
		Optional<Pedido> pedido = pedidoService.findPedidoById(pedidoId);

		if (pedido.get().getEstado() == Estado.RECIBIDO) {

			modelMap.addAttribute("message", "El pedido ya se encuentra en este estado");
			view = listadoPedidos(modelMap);

			log.error("El pedido ya se encuentra en este estado.");
			return view;

		} else if (pedido.get().getEstado() != Estado.EN_REPARTO) {

			modelMap.addAttribute("message", "El pedido debe de estar en reparto previamente a pasar a este estado");
			view = listadoPedidos(modelMap);

			log.error("No se puede pasar del estado actual a RECIBIDO");
			return view;
		}
		pedido.get().setEstado(Estado.RECIBIDO);
		modelMap.addAttribute("pedido", pedido);
		modelMap.addAttribute("message", "Se ha actualizado el estado del pedido");
		view = listadoPedidos(modelMap);

		log.info("Se ha actualizado el estado del pedido a RECIBIDO");
		return view;
	}

}