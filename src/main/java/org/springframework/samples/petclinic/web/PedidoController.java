package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Estado;
import org.springframework.samples.petclinic.model.LineaPedido;
import org.springframework.samples.petclinic.model.Oferta;
import org.springframework.samples.petclinic.model.Pedido;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.LineaPedidoService;
import org.springframework.samples.petclinic.service.OfertaService;
import org.springframework.samples.petclinic.service.PedidoService;
import org.springframework.samples.petclinic.service.RestauranteService;
import org.springframework.samples.petclinic.service.UserService;
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
	@Autowired
	private LineaPedidoService lineapedidoService;
	@Autowired
	private UserService userService;

	//Obtener todas las ofertas disponibles dependiendo del tipo de cliente.
	//Los socios tendrán acceso a todas las ofertas.
	//Los clientes que no son socios solo podrán aplicar algunas.
	@ModelAttribute("ofertas")
	public Iterable<Oferta> oferta() {
		List<Oferta> ofertas = new ArrayList<>();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		String autoridad = userService.findUser(auth.getName()).get().getAuthorities().getAuthority();
		if(autoridad.equals("cliente")) {
			Optional<Cliente> cliente = clienteService.findClienteByUsuario(name);
			log.info("ID DEL CLIENTE: "+cliente.get().getId());
			List<Oferta> ofertasVIP = (List<Oferta>) ofertaService.findAll();
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
		return ofertas;
	}

	//Obtener la lista de pedidos por restaurante.
	@GetMapping()
	public String listadoPedidos(ModelMap modelMap, @PathVariable("restauranteId") int restauranteId, @PathVariable("userName") String usuario) {
		String vista = "pedidos/listadoPedidos";
		Iterable<Pedido> pedidos;
		String autoridad = userService.findUser(usuario).get().getAuthorities().getAuthority();
		if(autoridad.equals("cliente")) {
			pedidos = pedidoService.findPedidosByUsuarioIdYRestauranteId(usuario, restauranteId);
		}else {
			pedidos = pedidoService.findPedidosByRestauranteId(restauranteId);
		}
		Iterable<LineaPedido> lineaPedidos = lineapedidoService.findAll();
		modelMap.addAttribute("restauranteId", restauranteId);
		modelMap.addAttribute("pedidos", pedidos);
		modelMap.addAttribute("lineaPedidos", lineaPedidos);
		modelMap.addAttribute("userName", usuario);
		log.info("listando pedidos de un restaurante indicado y usuario actual");
		return vista;
	}
	
	//Crear un nuevo pedido.
	@GetMapping(path = "/new")
	public String nuevoPedido(ModelMap modelMap, @PathVariable("restauranteId") int restauranteId,@PathVariable("userName") String usuario) {
		String view = "pedidos/nuevoPedido";
		Restaurante restaurante = restauranteService.findRestauranteById(restauranteId).get();
		modelMap.addAttribute("pedido", new Pedido());
		modelMap.addAttribute("restauranteId", restauranteId);
		modelMap.addAttribute("restaurante", restaurante);
		modelMap.addAttribute("userName", usuario);
		log.info("Operación para añadir pedido en ejecucion");

		return view;
	}

	//Guardamos el pedido. Se establecerá por defecto en el estado SIN_VERIFICAR.
	@PostMapping(path = "/order")
	public String tramitarPedido(@Valid Pedido pedido, BindingResult result, ModelMap modelMap,
			@PathVariable("restauranteId") int restauranteId,@PathVariable("userName") String usuario)  {
		if (result.hasErrors()) {
			log.error(result.toString());
			Restaurante restaurante = restauranteService.findRestauranteById(restauranteId).get();
			modelMap.addAttribute("pedido", pedido);
			modelMap.addAttribute("restauranteId", restauranteId);
			modelMap.addAttribute("restaurante", restaurante);
			modelMap.addAttribute("message", "Los datos introducidos no cumplen ciertas condiciones, revisar los campos");
			
			log.error("Los datos introducidos no cumplen ciertas condiciones, revisar los campos");
			return "pedidos/nuevoPedido";
		} else {
			pedido.setRestaurante(restauranteService.findRestauranteById(restauranteId).get());
			pedido.setCliente(clienteService.findClienteByUsuario(usuario).get());
			pedidoService.save(pedido);
			modelMap.addAttribute("message", "Pedido creado con éxito");
      
			log.info("Pedido creado con éxito");
			return "redirect:/restaurantes/{restauranteId}/pedidos/{userName}";
		}
	}

	
	//Pantalla de selección de ofertas disponibles.
	//Se debe de haber refrescado el pedido previamente para que precio tenga un valor.
	//Solo se puede añadir ofertas si está sin verificar el pedido.
	@GetMapping(path = "/{pedidoId}/oferta")
	public String seleccionaOferta(@PathVariable("pedidoId") int pedidoId, ModelMap modelMap,
			@PathVariable("restauranteId") int restauranteId,@PathVariable("userName") String usuario) {
		String view;
		Optional<Pedido> pedido = pedidoService.findPedidoById(pedidoId);
		if(pedido.get().getEstado() == Estado.SIN_VERIFICAR) {
			if(pedido.get().getPrice() != null) {
				view = "pedidos/selectOferta";
				modelMap.addAttribute("restauranteId", restauranteId);
				modelMap.addAttribute("pedido", pedido.get());
				modelMap.addAttribute("userName", usuario);
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

	
	//Aplicamos la oferta seleccionada.
	//Saltará un aviso si no se selecciona ninguna oferta.
	//Saltará otro aviso si se intenta añadir una oferta cuyo precio mínimo supere al precio actual del pedido.
	@PostMapping(path = "/{pedidoId}/oferta")
	public String añadeOferta(@RequestParam("oferta") Oferta oferta, ModelMap modelMap,
			@PathVariable("restauranteId") int restauranteId, @PathVariable("pedidoId") int pedidoId,@PathVariable("userName") String usuario) {
		Optional<Pedido> pedido = pedidoService.findPedidoById(pedidoId);

		if(oferta != null) {
			if(pedido.get().getPrice() < oferta.getMinPrice()) {
				modelMap.addAttribute("message","Esta oferta solo es aplicable para pedidos con un precio mayor o igual a " + oferta.getMinPrice());
				modelMap.addAttribute("restauranteId",restauranteId);
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
			modelMap.addAttribute("restauranteId", restauranteId);
			modelMap.addAttribute("pedido", pedido.get());
			modelMap.addAttribute("message","Selecciona una oferta");
			return "pedidos/selectOferta";
		}
	}

	//Cancelar un pedido.
	//No se podrán cancelar pedidos que se encuentren en reparto o ya se hayan recibido.
	@GetMapping(path = "/cancel/{pedidoId}")
	public String cancelarPedido(@PathVariable("pedidoId") int pedidoId, ModelMap modelMap,
			@PathVariable("restauranteId") int restauranteId, @PathVariable("userName") String usuario) throws CantCancelOrderException {
		String view = "pedidos/listadoPedidos";
		Optional<Pedido> pedido = pedidoService.findPedidoById(pedidoId);

		if (pedido.isPresent()) {
			if (pedido.get().getEstado() == Estado.EN_REPARTO || pedido.get().getEstado() == Estado.RECIBIDO) {

				log.error("No se puede cancelar el pedido debido al estado en el que se encuentra");
				throw new CantCancelOrderException();
			} else {
				Cliente cliente = clienteService.findClienteByUsuario(usuario).get();
				cliente.setMonedero(cliente.getMonedero()+pedido.get().getPrice());
				pedidoService.delete(pedido.get());
				clienteService.update(cliente);
				view = "redirect:/restaurantes/{restauranteId}/pedidos/{userName}";
				log.info("Pedido eliminado con éxito");
			}
		} else {
			modelMap.addAttribute("message", "No se encontró el pedido");
			view = listadoPedidos(modelMap, restauranteId, usuario);

			log.error("No se ha encontrado el pedido para eliminar");
		}
		return view;
	}
	
	//Obtenemos todos los productos disponibles
	@ModelAttribute("productos")
	public Iterable<Producto> producto() {
		return this.pedidoService.getAllProductos();

	}

	//Refresca los datos del pedido y cambia el estado de check a true.
	//Sirve para actualizar el precio del pedido después de haber agregado una oferta o un nuevo producto.
	//Se necesita refrescar el pedido para realizar diversas operaciones.
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

	//Encarga el pedido después de añadir todos los datos necesarios.
	//Cambia el estado de SIN_VERIFICAR a PROCESANDO.
	//Comprobamos que el precio del pedido sea distinto de null y que supere el mínimo requerido.
	//Se añaden restricciones para evitar verificar un pedido que ya ha sido verificado o está en un estado posterior.
	@GetMapping(path = "/verify/{pedidoId}")
	public String verificaPedido(@PathVariable("pedidoId") int pedidoId, ModelMap modelMap,
			@PathVariable("restauranteId") int restauranteId, @PathVariable("userName") String usuario)  {
		String view = "pedidos/listadoPedidos";
		Optional<Pedido> pedido = pedidoService.findPedidoById(pedidoId);

		if(pedido.get().getPrice() != null) {


			if (pedido.get().getEstado() == Estado.SIN_VERIFICAR) {

				if (pedido.get().getPrice() >= 10) {

					if (pedido.get().getCheckea() == true) {
						Cliente cliente = clienteService.findClienteByUsuario(usuario).get();
						cliente.setMonedero(cliente.getMonedero()-pedido.get().getPrice());
						pedido.get().setEstado(Estado.PROCESANDO);
						pedidoService.save(pedido.get());
						clienteService.update(cliente);
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
				modelMap.addAttribute("message", "Este pedido ya ha sido confirmado");
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

	//Actualiza el estado del pedido a PREPARANDO.
	//Se verifica que para pasar a este estado, el pedido se encuentre en el estado previo adecuado.
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
	
	//Actualiza el estado del pedido a EN_REPARTO.
	//Se verifica que para pasar a este estado, el pedido se encuentre en el estado previo adecuado.
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
	
	//Actualiza el estado del pedido a RECIBIDO.
	//Se verifica que para pasar a este estado, el pedido se encuentre en el estado previo adecuado.
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