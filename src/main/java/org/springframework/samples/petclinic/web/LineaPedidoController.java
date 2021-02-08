package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Estado;
import org.springframework.samples.petclinic.model.LineaPedido;
import org.springframework.samples.petclinic.model.Pedido;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.LineaPedidoService;
import org.springframework.samples.petclinic.service.PedidoService;
import org.springframework.samples.petclinic.service.ProductoService;
import org.springframework.samples.petclinic.service.RestauranteService;
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
@RequestMapping("/restaurantes/{restauranteId}/pedidos/{userName}/{pedidoId}/lineaPedidos")
public class LineaPedidoController {

	private static final Logger log = (Logger) LoggerFactory.getLogger(LineaPedidoController.class);
	public static final String VIEWS_LINEAPEDIDO_CREATE_OR_UPDATE_FORM = "lineaPedidos/editLineaPedido";

	@Autowired
	private LineaPedidoService lineaPedidoService;
	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private ProductoService productoService;
	@Autowired
	private RestauranteService restauranteService;
	@Autowired
	private ClienteService clienteService;

	
	//Buscamos todos los productos para usarlos en la vista a la hora de seleccionarlos.
	@ModelAttribute("nombres")
	public Iterable<Producto> findAllProductos() {
		return this.productoService.findAll();
	}



	//Obtenemos la lista de lineaPedido.
	@GetMapping()
	public String listadoLineaPedidos(ModelMap modelMap, @PathVariable("restauranteId") int restauranteId, @PathVariable("userName") String usuario) {

		String vista = "lineaPedidos/listadoLineaPedidos";
		Restaurante restaurante  = restauranteService.findRestauranteById(restauranteId).get();
		modelMap.addAttribute("restaurante", restaurante);

		log.info("Mostrando lista de lineas de pedido");

		return vista;
	}
	
	//Creamos una nueva linea pedido.
	//Nos aseguramos de que para crearlo, el pedido se encuentre en un estado adecuado.
	//Se resetea el campo de checkea de pedido a false, para forzar el refresh de Pedido.
	@GetMapping(path = "/new")
	public String crearLineaPedido(ModelMap modelMap, @PathVariable("pedidoId") int pedidoId,@PathVariable("restauranteId") int restauranteId, @PathVariable("userName") String usuario)  {
		Optional<Pedido> pedido = pedidoService.findPedidoById(pedidoId);
		Optional<Restaurante> restaurante = restauranteService.findRestauranteById(restauranteId);
		Cliente cliente = clienteService.findClienteByUsuario(usuario).get();
 		Iterable<Pedido> pedidos =  pedidoService.findPedidosByClienteIdYRestauranteId(cliente.getId(), restauranteId);
		if (pedido.get().getEstado() != Estado.SIN_VERIFICAR) {
			String view = "pedidos/listadoPedidos";
			modelMap.addAttribute("pedidos", pedidos);
			modelMap.addAttribute("name", usuario);
			modelMap.addAttribute("restaurante", restaurante.get());
			modelMap.addAttribute("message", "¡El pedido ya se ha verificado! No puede editarlo");
			
			return view;
		} else {
			pedido.get().setCheckea(false);
			pedidoService.save(pedido.get());
			String view = "lineaPedidos/editLineaPedido";
			modelMap.addAttribute("restauranteId", restauranteId);
			modelMap.addAttribute("pedido", pedido.get());
			modelMap.addAttribute("name", usuario);
			modelMap.addAttribute("lineaPedido", new LineaPedido());
			
			log.info("Operación para añadir linea de pedido en ejecucion");

			return view;
		}
	}
	
	//Guardamos la linea de pedido.
	@PostMapping(path = "/save")
	public String salvarLineaPedido(@Valid LineaPedido lineaPedido, BindingResult result, ModelMap modelMap, @PathVariable("restauranteId") int restauranteId,@PathVariable("pedidoId") int pedidoId, @PathVariable("userName") String usuario) {
		Optional<Pedido> pedido = pedidoService.findPedidoById(pedidoId);
		if (result.hasErrors()) {
			modelMap.addAttribute("lineaPedido", lineaPedido);
			modelMap.addAttribute("name", usuario);
			modelMap.addAttribute("restaurante", restauranteService.findRestauranteById(restauranteId).get());
			modelMap.addAttribute("restauranteId", restauranteId);
			modelMap.addAttribute("pedido",pedido.get());
			log.error("Los datos introducidos no cumplen ciertas condiciones, revisar los campos");
			return "lineaPedidos/editLineaPedido";

		} else {
		
			lineaPedidoService.save(lineaPedido);
			modelMap.addAttribute("message", "Event successfully saved!");
			modelMap.addAttribute("name", usuario);
			log.info("Linea de pedido creado con éxito");
			return "redirect:/restaurantes/{restauranteId}/pedidos/{userName}";
		}
	}


	@ModelAttribute("productos")
	public List<Producto> producto() {
		return this.lineaPedidoService.getAllProductos();
	}
	
	//Buscamos todos los nombres de los productos que se visualizarán a la hora de crear la linea de pedido.
	@ModelAttribute("nameProd")
	public List<String> nombreProductos() {
		return this.lineaPedidoService.getAllProductosName();
	}
}