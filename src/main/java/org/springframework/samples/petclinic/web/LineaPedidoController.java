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


	@ModelAttribute("nombres")
	public Iterable<Producto> findAllProductos() {
		return this.productoService.findAll();
	}

	/*
	 * @InitBinder("productos") public void initProductoBinder(WebDataBinder
	 * dataBinder) { dataBinder.setValidator(new ProductoValidator());
	 * 
	 * }
	 */

	@GetMapping()
	public String listadoLineaPedidos(ModelMap modelMap, @PathVariable("restauranteId") int restauranteId, @PathVariable("userName") String usuario) {

		String vista = "lineaPedidos/listadoLineaPedidos";
		Restaurante restaurante  = restauranteService.findRestauranteById(restauranteId).get();
		modelMap.addAttribute("restaurante", restaurante);

		log.info("Mostrando lista de lineas de pedido");

		return vista;
	}
	
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

	/*
	 * @GetMapping(path="delete/{lineaPedidoId}") public String
	 * borrarProveedor(@PathVariable("lineaPedidoId") int lineaPedidoId, ModelMap
	 * modelMap) { String view="lineaPedidos/listadoLineaPedidos";
	 * Optional<LineaPedido>
	 * lineaPedido=lineaPedidoService.findLineaPedidoById(lineaPedidoId);
	 * 
	 * if(lineaPedido.isPresent()) { lineaPedidoService.delete(lineaPedido.get());
	 * modelMap.addAttribute("message","Event succesfully deleted!");
	 * view=listadoLineaPedidos(modelMap);
	 * 
	 * log.info("LineaPedido eliminada con éxito"); }else {
	 * modelMap.addAttribute("message","Event not found!");
	 * view=listadoLineaPedidos(modelMap);
	 * 
	 * log.error("No se ha encontrado la lineaPedido para eliminar"); } return view;
	 * }
	 * 
	 * @GetMapping(path = "/{lineaPedidoId}/edit") public String
	 * initUpdateForm(@PathVariable("lineaPedidoId") int lineaPedidoId, ModelMap
	 * model) { LineaPedido lineaPedido =
	 * this.lineaPedidoService.findLineaPedidoById(lineaPedidoId).get();
	 * model.addAttribute(lineaPedido);
	 * 
	 * log.info("Operación para editar lineaPedido en ejecucion");
	 * 
	 * return VIEWS_LINEAPEDIDO_CREATE_OR_UPDATE_FORM; }
	 * 
	 * @PostMapping(value="/{lineaPedidoId}/edit") public String
	 * processUpdateProductoForm(@Valid LineaPedido lineaPedido, BindingResult
	 * result, @PathVariable("lineaPedidoId" ) int lineaPedidoId) { if
	 * (result.hasErrors()) {
	 * 
	 * log.
	 * error("Los datos introducidos no cumplen ciertas condiciones, revisar los campos"
	 * );
	 * 
	 * return VIEWS_LINEAPEDIDO_CREATE_OR_UPDATE_FORM; } else {
	 * lineaPedido.setId(lineaPedidoId); this.lineaPedidoService.save(lineaPedido);
	 * 
	 * 
	 * log.info("LineaPedido editada satisfactoriamente"); } return
	 * "redirect:/productos/{productoId}"; }
	 */
	@ModelAttribute("productos")
	public List<Producto> producto() {
		return this.lineaPedidoService.getAllProductos();
	}

	@ModelAttribute("nameProd")
	public List<String> nombreProductos() {
		return this.lineaPedidoService.getAllProductosName();
	}
}