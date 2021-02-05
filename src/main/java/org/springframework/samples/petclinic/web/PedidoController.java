package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Estado;
import org.springframework.samples.petclinic.model.Oferta;
import org.springframework.samples.petclinic.model.Pedido;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.OfertaService;
import org.springframework.samples.petclinic.service.PedidoService;
import org.springframework.samples.petclinic.service.RestauranteService;
import org.springframework.samples.petclinic.service.exceptions.CantCancelOrderException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("restaurantes/{restauranteId}/pedidos/{userName}")
public class PedidoController {

	private static final Logger log = (Logger) LoggerFactory.getLogger(PedidoController.class);

	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private RestauranteService restauranteService;
	@Autowired
	private OfertaService ofertaService;
	@Autowired
	private ClienteService clienteService;


	@ModelAttribute("ofertas")
	public Iterable<Oferta> oferta() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		Optional<Cliente> cliente = clienteService.findClienteByUsuario(name);
		List<Oferta> ofertasVIP = (List<Oferta>) ofertaService.findAll();
		List<Oferta> ofertas = new ArrayList<>();
		if(!cliente.get().getEsSocio()) {
			for(int i=0;i<ofertasVIP.size();i++) {
				Oferta oferta = ofertasVIP.get(i);
				if(!oferta.getExclusivo()) {
					ofertas.add(oferta);

				}
			}
			return ofertas;
		}
		return ofertasVIP;
	}
	
	@GetMapping()
	public String listadoPedidos(ModelMap modelMap, @PathVariable("restauranteId") int restauranteId, @PathVariable("userName") String usuario) {
		String vista = "pedidos/listadoPedidos";
		Restaurante restaurante = restauranteService.findRestauranteById(restauranteId).get();
		modelMap.addAttribute("restaurante", restaurante);
		modelMap.addAttribute("name", usuario);
		log.info("listando pedidos de un restaurante indicado y usuario actual");
		return vista;
	}

	@GetMapping(path = "/new")
	public String nuevoPedido(ModelMap modelMap, @PathVariable("restauranteId") int restauranteId,@PathVariable("userName") String usuario) {
		String view = "pedidos/nuevoPedido";
		modelMap.addAttribute("pedido", new Pedido());
		modelMap.addAttribute("restauranteId", restauranteId);
		modelMap.addAttribute("name", usuario);
		log.info("Operación para añadir pedido en ejecucion");

		return view;
	}

	@PostMapping(path = "/order")
	public String tramitarPedido(@Valid Pedido pedido, BindingResult result, ModelMap modelMap,
			@PathVariable("restauranteId") int restauranteId,@PathVariable("userName") String usuario)  {
		String view = "pedidos/listadoPedidos";
		if (result.hasErrors()) {
			modelMap.addAttribute("pedido", pedido);
			modelMap.addAttribute("restaurante", restauranteService.findRestauranteById(restauranteId).get());
			log.error("Los datos introducidos no cumplen ciertas condiciones, revisar los campos");

			return "pedidos/nuevoPedido";
		} else {
			pedido.setRestaurante(restauranteService.findRestauranteById(restauranteId).get());
			pedido.setCliente(clienteService.findClienteByUsuario(usuario).get());
			pedidoService.save(pedido);
			modelMap.addAttribute("message", "Pedido creado con éxito");
			view = listadoPedidos(modelMap, restauranteId, usuario);

			log.info("Pedido creado con éxito");

		}

		return "redirect:/restaurantes/{restauranteId}/pedidos/{userName}";
	}

	@GetMapping(path = "/{pedidoId}/oferta")
	public String seleccionaOferta(@PathVariable("pedidoId") int pedidoId, ModelMap modelMap,
			@PathVariable("restauranteId") int restauranteId,@PathVariable("userName") String usuario) {
		String view;
		Optional<Pedido> pedido = pedidoService.findPedidoById(pedidoId);
		Optional<Restaurante> restaurante = restauranteService.findRestauranteById(restauranteId);
		if(pedido.get().getEstado() == Estado.SIN_VERIFICAR) {
			if(pedido.get().getPrice() != null) {
				view = "pedidos/selectOferta";
				modelMap.addAttribute("restaurante", restaurante.get());
				modelMap.addAttribute("pedido", pedido.get());
			}else {
				modelMap.addAttribute("message", "Agrega productos y refresca el pedido antes de añadir una oferta");
				view = listadoPedidos(modelMap,restauranteId, usuario);
			}
		}else {

			modelMap.addAttribute("message", "No puede agregar ofertas. El pedido ya está verificado.");
			view = listadoPedidos(modelMap,restauranteId, usuario);
		}
		return view;
	}


	@PostMapping(path = "/{pedidoId}/oferta")
	public String añadeOferta(@RequestParam("oferta") Oferta oferta, ModelMap modelMap,
			@PathVariable("restauranteId") int restauranteId, @PathVariable("pedidoId") int pedidoId,@PathVariable("userName") String usuario) {
		Optional<Restaurante> restaurante = restauranteService.findRestauranteById(restauranteId);
		Optional<Pedido> pedido = pedidoService.findPedidoById(pedidoId);
		// modelMap.addAttribute("pedido", pedido.get());
		// pedido.get().setOferta(oferta);
		// System.out.println("cualquier cosa" + pedido.get().getOferta().getId());

		if(oferta != null) {
			if(pedido.get().getPrice() < oferta.getMinPrice()) {
				modelMap.addAttribute("message","Esta oferta solo es aplicable para pedidos con un precio mayor o igual a " + oferta.getMinPrice());
				modelMap.addAttribute("restaurante",restaurante.get());
				modelMap.addAttribute("pedido",pedido.get());
				return "pedidos/selectOferta";
			}
			pedido.get().setCheckea(false);
			pedido.get().setOferta(oferta);
			pedidoService.save(pedido.get());
			modelMap.addAttribute("message", "Se ha creado el evento");
			log.info("Oferta añadida con éxito");
			return "redirect:/restaurantes/{restauranteId}/pedidos/{userName}";
		}else {
			modelMap.addAttribute("restaurante", restaurante.get());
			modelMap.addAttribute("pedido", pedido.get());
			modelMap.addAttribute("message","Selecciona una oferta");
			return "pedidos/selectOferta";
		}
	}

	@GetMapping(path = "/cancel/{pedidoId}")
	public String cancelarPedido(@PathVariable("pedidoId") int pedidoId, ModelMap modelMap,
			@PathVariable("restauranteId") int restauranteId, @PathVariable("userName") String usuario) {
		String view = "pedidos/listadoPedidos";
		Optional<Pedido> pedido = pedidoService.findPedidoById(pedidoId);

		if (pedido.isPresent()) {
			try {
				pedidoService.delete(pedido.get());
			} catch (CantCancelOrderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			view = "redirect:/restaurantes/{restauranteId}/pedidos/{userName}";
			log.info("Pedido eliminado con éxito");
		} else {
			modelMap.addAttribute("message", "No se encontró el pedido");
			view = listadoPedidos(modelMap, restauranteId, usuario);

			log.error("No se ha encontrado el pedido para eliminar");
		}
		return view;
	}

	@ModelAttribute("productos")
	public Iterable<Producto> producto() {
		return this.pedidoService.getAllProductos();

	}

	@GetMapping(path = "/refresh/{pedidoId}")
	public String refreshPrice(@PathVariable("pedidoId") int pedidoId, ModelMap modelMap,
			@PathVariable("restauranteId") int restauranteId, @PathVariable("userName") String usuario)  {
		String view = "pedidos/listadoPedidos";
		Optional<Pedido> pedido = pedidoService.findPedidoById(pedidoId);
		Double price = pedidoService.getTotalPrice(pedido.get().getId());
		if(pedido.get().getOferta() != null) {
			Double descuento = pedido.get().getOferta().getDescuento();
			price -= descuento;
			if(price < 0.) {
				price = 0.;
			}
		}
		pedido.get().setCheckea(true);
		pedido.get().setPrice(price);
		pedidoService.save(pedido.get());
		modelMap.addAttribute("pedido", pedido);
		view = listadoPedidos(modelMap, restauranteId, usuario);

		log.info("Precios actualizados con éxito");
		return view;
	}

	@GetMapping(path = "/verify/{pedidoId}")
	public String verificaPedido(@PathVariable("pedidoId") int pedidoId, ModelMap modelMap,
			@PathVariable("restauranteId") int restauranteId, @PathVariable("userName") String usuario)  {
		String view = "pedidos/listadoPedidos";
		Optional<Pedido> pedido = pedidoService.findPedidoById(pedidoId);

		if(pedido.get().getPrice() != null) {


			if (pedido.get().getEstado() == Estado.SIN_VERIFICAR) {

				if (pedido.get().getPrice() >= 10) {

					if (pedido.get().getCheckea() == true) {
						pedido.get().setEstado(Estado.PROCESANDO);
						pedidoService.save(pedido.get());
						modelMap.addAttribute("pedido", pedido);
						modelMap.addAttribute("message",
								"Se ha realizado el pedido satisfactoriamente y ahora está se está procesando en nuestra central.");
						view = listadoPedidos(modelMap, restauranteId, usuario);

						log.info("Se ha realizado el pedido");

					} else {

						modelMap.addAttribute("pedido", pedido);
						modelMap.addAttribute("message", "¡Refresca el pedido antes de verificarlo!");
						view = listadoPedidos(modelMap, restauranteId, usuario);
					}
				} else {
					modelMap.addAttribute("pedido", pedido);
					modelMap.addAttribute("message", "¡El precio del pedido debe de ser mayor o igual a 10!");
					view = listadoPedidos(modelMap, restauranteId, usuario);
				}

			} else {
				modelMap.addAttribute("message", "El pedido debe se debe de estar sin verificar previamente a procesarlo");
				view = listadoPedidos(modelMap, restauranteId, usuario);

				log.error("No se puede pasar del estado actual a PROCESANDO");
				view = listadoPedidos(modelMap, restauranteId, usuario);
			}
		}else {
			modelMap.addAttribute("pedido", pedido);
			modelMap.addAttribute("message", "¡Agrega un producto y refresca!");
			view = listadoPedidos(modelMap, restauranteId, usuario);
		}
		return view;

	}

	////////////////////////////////////////////////////////////////// BOTONES PARA
	////////////////////////////////////////////////////////////////// GERENTES///////////////////////////////////////////

	@GetMapping(path = "/preparando/{pedidoId}")
	public String actualizadoPedidoPreparando(@PathVariable("pedidoId") int pedidoId, ModelMap modelMap,
			@PathVariable("restauranteId") int restauranteId, @PathVariable("userName") String usuario) {
		String view = "pedidos/listadoPedidos";
		Optional<Pedido> pedido = pedidoService.findPedidoById(pedidoId);

		if (pedido.get().getEstado() == Estado.PREPARANDO) {

			modelMap.addAttribute("message", "El pedido ya se encuentra en este estado");
			view = listadoPedidos(modelMap, restauranteId, usuario);

			log.error("El pedido ya se encuentra en este estado.");
			return view;

		} else if (pedido.get().getEstado() != Estado.PROCESANDO) {

			modelMap.addAttribute("message", "El pedido debe se debe de estar procesando previamente a prepararlo");
			view = listadoPedidos(modelMap, restauranteId, usuario);

			log.error("No se puede pasar del estado actual a PREPARANDO");
			return view;
		}
		pedido.get().setEstado(Estado.PREPARANDO);
		pedidoService.save(pedido.get());
		modelMap.addAttribute("pedido", pedido);
		modelMap.addAttribute("message", "Se ha actualizado el estado del pedido");
		view = listadoPedidos(modelMap, restauranteId, usuario);

		log.info("Se ha actualizado el estado del pedido a Preparando");
		return view;
	}

	@GetMapping(path = "/reparto/{pedidoId}")
	public String actualizadoPedidoReparto(@PathVariable("pedidoId") int pedidoId, ModelMap modelMap,
			@PathVariable("restauranteId") int restauranteId, @PathVariable("userName") String usuario)  {
		String view = "pedidos/listadoPedidos";
		Optional<Pedido> pedido = pedidoService.findPedidoById(pedidoId);

		if (pedido.get().getEstado() == Estado.EN_REPARTO) {

			modelMap.addAttribute("message", "El pedido ya se encuentra en este estado");
			view = listadoPedidos(modelMap, restauranteId, usuario);

			log.error("El pedido ya se encuentra en este estado.");
			return view;

		} else if (pedido.get().getEstado() != Estado.PREPARANDO) {

			modelMap.addAttribute("message",
					"El pedido debe se debe de estar preparando previamente a estar en reparto");
			view = listadoPedidos(modelMap, restauranteId, usuario);

			log.error("No se puede pasar del estado actual a EN_REPARTO");
			return view;
		}
		pedido.get().setEstado(Estado.EN_REPARTO);
		pedidoService.save(pedido.get());
		modelMap.addAttribute("pedido", pedido);
		modelMap.addAttribute("message", "Se ha actualizado el estado del pedido");
		view = listadoPedidos(modelMap, restauranteId,usuario);

		log.info("Se ha actualizado el estado del pedido a EN_REPARTO");
		return view;
	}

	@GetMapping(path = "/recibido/{pedidoId}")
	public String actualizadoPedidoRecibido(@PathVariable("pedidoId") int pedidoId, ModelMap modelMap,
			@PathVariable("restauranteId") int restauranteId, @PathVariable("userName") String usuario)  {
		String view = "pedidos/listadoPedidos";
		Optional<Pedido> pedido = pedidoService.findPedidoById(pedidoId);

		if (pedido.get().getEstado() == Estado.RECIBIDO) {

			modelMap.addAttribute("message", "El pedido ya se encuentra en este estado");
			view = listadoPedidos(modelMap, restauranteId,usuario);

			log.error("El pedido ya se encuentra en este estado.");
			return view;

		} else if (pedido.get().getEstado() != Estado.EN_REPARTO) {

			modelMap.addAttribute("message", "El pedido debe de estar en reparto previamente a pasar a este estado");
			view = listadoPedidos(modelMap, restauranteId,usuario);

			log.error("No se puede pasar del estado actual a RECIBIDO");
			return view;
		}
		pedido.get().setEstado(Estado.RECIBIDO);
		pedidoService.save(pedido.get());
		modelMap.addAttribute("pedido", pedido);
		modelMap.addAttribute("message", "Se ha actualizado el estado del pedido");
		view = listadoPedidos(modelMap, restauranteId,usuario);

		log.info("Se ha actualizado el estado del pedido a RECIBIDO");
		return view;
	}

}