package org.springframework.samples.petclinic.web;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Ingrediente;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.service.IngredienteService;
import org.springframework.samples.petclinic.service.ProductoService;
import org.springframework.samples.petclinic.service.RestauranteService;
import org.springframework.samples.petclinic.service.exceptions.WrongDataProductosException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("/restaurantes/{restauranteId}/productos")
public class ProductoController {

	private static final Logger log = (Logger) LoggerFactory.getLogger(ProductoController.class);
	public static final String VIEWS_PRODUCTOS_CREATE_OR_UPDATE_FORM = "productos/editProducto";

	@Autowired
	private ProductoService productoService;
	@Autowired
	private RestauranteService resService;
	@Autowired
	private IngredienteService ingredienteService;

	//Se obtiene la lista de productos por restaurante.
	@GetMapping()
	public String listadoProductos(ModelMap modelMap, @PathVariable("restauranteId") int restauranteId) {
		String vista= "productos/listadoProductos";
		Iterable<Producto> productos = productoService.findProductosByRestauranteId(restauranteId);
		modelMap.addAttribute("restauranteId",restauranteId);
		modelMap.addAttribute("productos",productos);
		log.info("Mostrando lista de productos");
		return vista;
	}

	//Creación de un nuevo producto.
	@GetMapping(path="/new")
	public String crearProducto(ModelMap modelMap, @PathVariable("restauranteId") int restauranteId) {
		String view ="productos/editProducto";
		Restaurante restaurante = resService.findRestauranteById(restauranteId).get();
		modelMap.addAttribute("restaurante",restaurante);
		modelMap.addAttribute("restauranteId",restauranteId);
		modelMap.addAttribute("producto", new Producto());

		log.info("Operación para añadir producto en ejecucion");

		return view;
	}
	
	//Guardamos un nuevo producto creado.
	@PostMapping(path="/save/{productoId}")
	public String salvarProducto(@Valid Producto producto, BindingResult result, ModelMap modelMap, @PathVariable("restauranteId") int restauranteId,
								@RequestParam(value = "version", required = false) Integer version, @PathVariable("productoId") int productoId) throws WrongDataProductosException {
		if(result.hasErrors())
		{
			modelMap.addAttribute("producto", producto);
			
			log.error("Los datos introducidos no cumplen ciertas condiciones, revisar los campos");

			return "productos/editProducto";
		}else {
			Producto productoToUpdate = productoService.findProductoById(productoId).get();
			if(productoToUpdate.getVersion() != version) {
				log.error("Las versiones de oferta no coinciden: ofertaToUpdate version " + productoToUpdate.getVersion() + " oferta version "+version);
				modelMap.addAttribute("message", "Ha ocurrido un error inesperado por favor intentalo de nuevo");
				return listadoProductos(modelMap, restauranteId);
			}else {
				productoService.save(producto);
				modelMap.addAttribute("message", "Event successfully saved!");
				log.info("Producto creado con éxito");
				return "redirect:/restaurantes/{restauranteId}/productos";
			}
		}
	}
	
	//Guardamos un nuevo producto creado.
	@PostMapping(path="/save")
	public String salvarProducto(@Valid Producto producto, BindingResult result, ModelMap modelMap, @PathVariable("restauranteId") int restauranteId) throws WrongDataProductosException {
		if(result.hasErrors())
		{
			modelMap.addAttribute("producto", producto);
			log.error("Los datos introducidos no cumplen ciertas condiciones, revisar los campos");
			return "productos/editProducto";
		}else {
			if((producto.getName().length() < 3 || producto.getName().length() > 50) || !producto.getAlergenos().matches("^[a-zA-Z,.!? ]*$")||producto.getPrecio() <= 0) {
				log.error("No se cumplen las condiciones al crear el producto");
				modelMap.addAttribute("producto", producto);
				log.error("Los datos introducidos no cumplen ciertas condiciones, revisar los campos");
				throw new WrongDataProductosException();
				
				//return "productos/editProducto";
			}else {
				productoService.save(producto);
				modelMap.addAttribute("message", "product successfully saved!");
				log.info("Producto creado con éxito");
				return "redirect:/restaurantes/{restauranteId}/productos";
			}
		}
	}
	
	//Eliminamos un producto ya existente.
	@GetMapping(path="delete/{productoId}")
	public String borrarProducto(@PathVariable("productoId") int productoId, ModelMap modelMap, @PathVariable("restauranteId") int restauranteId) {
		Optional<Producto> producto=productoService.findProductoById(productoId);

		if(producto.isPresent()) {
			productoService.delete(producto.get());
			modelMap.addAttribute("message","product succesfully deleted!");
			log.info("Producto eliminado con éxito");
		}else {
			modelMap.addAttribute("message","product not found!");
			log.error("No se ha encontrado el producto para eliminar");
		}
		return listadoProductos(modelMap, restauranteId);
	}
	
	//Se edita un producto ya existente.
	@GetMapping(path = "/edit/{productoId}")
	public String initUpdateForm(@PathVariable("productoId") int productoId, ModelMap model, @PathVariable("restauranteId") int restauranteId) {
		Producto producto = this.productoService.findProductoById(productoId).get();
		Restaurante restaurante = resService.findRestauranteById(restauranteId).get();
		model.addAttribute("producto", producto);
		model.addAttribute("restaurante",restaurante);
		log.info("Operación para editar producto en ejecucion");

		return VIEWS_PRODUCTOS_CREATE_OR_UPDATE_FORM;
	}
	
	@GetMapping(path="/{IngredienteId}")
	public String productoIngredientes(@PathVariable("restauranteId") int restauranteId, @PathVariable("IngredienteId") int IngredienteId, ModelMap modelMap) {
		Optional<Ingrediente> ingrediente = ingredienteService.findIngredienteById(IngredienteId);
		if(ingrediente.isPresent()) {
			Iterable<Producto> porducts = productoService.findProductosByRestauranteId(restauranteId);
			Set<Producto> productos = new HashSet<>();
			for(Producto p:porducts) {
				if(p.getIngredientes().contains(ingrediente.get())) {
					productos.add(p);
				}
			}
			modelMap.addAttribute("productos",productos);
			return "productos/listadoProductos";
		}else {
			modelMap.addAttribute("message","ingrediente no encontrado!");
			log.warn("ingrediente no encontrado");
			return "redirect: /restaurantes/{restauranteId}/ingredientes";
		}
	}
}
