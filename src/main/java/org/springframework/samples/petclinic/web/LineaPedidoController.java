package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Estado;
import org.springframework.samples.petclinic.model.LineaPedido;
import org.springframework.samples.petclinic.model.Pedido;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.model.Proveedor;
import org.springframework.samples.petclinic.service.LineaPedidoService;
import org.springframework.samples.petclinic.service.PedidoService;
import org.springframework.samples.petclinic.service.ProductoService;
import org.springframework.samples.petclinic.service.exceptions.WrongDataProductosException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("/pedidos/{pedidoId}/lineaPedidos")
public class LineaPedidoController {

	private static final Logger log = (Logger) LoggerFactory.getLogger(LineaPedidoController.class);
	public static final String VIEWS_LINEAPEDIDO_CREATE_OR_UPDATE_FORM = "lineaPedidos/editLineaPedido";

	@Autowired
	private LineaPedidoService lineaPedidoService;
	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private ProductoService productoService;


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
	public String listadoLineaPedidos(ModelMap modelMap) {

		String vista = "lineaPedidos/listadoLineaPedidos";
		Iterable<LineaPedido> lineaPedidos = lineaPedidoService.findAll();
		modelMap.addAttribute("lineaPedidos", lineaPedidos);

		log.info("Mostrando lista de lineas de pedido");

		return vista;
	}

	@GetMapping(path = "/new")
	public String crearLineaPedido(ModelMap modelMap, @PathVariable("pedidoId") int pedidoId) {
		Optional<Pedido> pedido = pedidoService.findPedidoById(pedidoId);
		pedido.get().setCheckea(false);
		if (pedido.get().getEstado() != Estado.SIN_VERIFICAR) {
			String view = "pedidos/listadoPedidos";
			modelMap.addAttribute("pedido", pedido);
			modelMap.addAttribute("message", "¡El pedido ya se ha verificado! No puede editarlo");
			
			return view;
		} else {
			
			String view = "lineaPedidos/editLineaPedido";
			modelMap.addAttribute("pedido", pedido.get());
			modelMap.addAttribute("lineaPedido", new LineaPedido());
			
			log.info("Operación para añadir linea de pedido en ejecucion");

			return view;
		}
	}

	@PostMapping(path = "/save")
	public String salvarLineaPedido(@Valid LineaPedido lineaPedido, BindingResult result, ModelMap modelMap) {
		
		String view = "lineaPedidos/listadoLineaPedidos";
		if (result.hasErrors()) {
			modelMap.addAttribute("lineaPedido", lineaPedido);
			log.error("Los datos introducidos no cumplen ciertas condiciones, revisar los campos");
			return "lineaPedidos/editLineaPedido";

		} else {
			lineaPedidoService.save(lineaPedido);
			modelMap.addAttribute("message", "Event successfully saved!");
			view = listadoLineaPedidos(modelMap);

			log.info("Linea de pedido creado con éxito");
		}
		return view;
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