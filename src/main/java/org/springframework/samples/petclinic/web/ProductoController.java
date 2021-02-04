package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.model.Restaurante;
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

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("/restaurantes/{restaurantesId}/productos")
public class ProductoController {

	private static final Logger log = (Logger) LoggerFactory.getLogger(ProductoController.class);
	public static final String VIEWS_PRODUCTOS_CREATE_OR_UPDATE_FORM = "productos/editProducto";

	@Autowired
	private ProductoService productoService;
	@Autowired
	private RestauranteService resService;

	@GetMapping()
	public String listadoProductos(ModelMap modelMap, @PathVariable("restaurantesId") int restauranteId) {
		String vista= "productos/listadoProductos";
		Restaurante restaurante = resService.findRestauranteById(restauranteId).get();
		modelMap.addAttribute("restaurante",restaurante);
		log.info("Mostrando lista de productos");
		return vista;
	}

	@GetMapping(path="/new")
	public String crearProducto(ModelMap modelMap, @PathVariable("restaurantesId") int restauranteId) {
		String view ="productos/editProducto";
		Restaurante restaurante = resService.findRestauranteById(restauranteId).get();
		modelMap.addAttribute("restaurante",restaurante);
		modelMap.addAttribute("producto", new Producto());

		log.info("Operación para añadir producto en ejecucion");

		return view;
	}

	@PostMapping(path="/save")
	public String salvarProducto(@Valid Producto producto, BindingResult result, ModelMap modelMap, @PathVariable("restaurantesId") int restauranteId) {
		String view="productos/listadoProducto";
		if(result.hasErrors())
		{
			modelMap.addAttribute("producto", producto);

			log.error("Los datos introducidos no cumplen ciertas condiciones, revisar los campos");

			return "productos/editProducto";
		}else {
			try {
				productoService.save(producto);
			} catch (WrongDataProductosException e) {

				e.printStackTrace();
			}
			modelMap.addAttribute("message", "Event successfully saved!");
			view=listadoProductos(modelMap, restauranteId);

			log.info("Producto creado con éxito");
			return "redirect:/restaurantes/{restaurantesId}/productos";
		}
	}
	@GetMapping(path="delete/{productoId}")
	public String borrarProducto(@PathVariable("productoId") int productoId, ModelMap modelMap, @PathVariable("restaurantesId") int restauranteId) {
		Optional<Producto> producto=productoService.findProductoById(productoId);

		if(producto.isPresent()) {
			productoService.delete(producto.get());
			modelMap.addAttribute("message","Event succesfully deleted!");
			log.info("Producto eliminado con éxito");
		}else {
			modelMap.addAttribute("message","Event not found!");
			log.error("No se ha encontrado el producto para eliminar");
		}
		return listadoProductos(modelMap, restauranteId);
	}
	
	@GetMapping(path = "/{productoId}/edit")
	public String initUpdateForm(@PathVariable("productoId") int productoId, ModelMap model) {
		Producto producto = this.productoService.findProductoById(productoId).get();
		model.addAttribute(producto);

		log.info("Operación para editar producto en ejecucion");

		return VIEWS_PRODUCTOS_CREATE_OR_UPDATE_FORM;
	}
	@PostMapping(value="/{productoId}/edit")
	public String processUpdateProductoForm(@Valid Producto producto, BindingResult result, @PathVariable("productoId") int productoId) {
		if (result.hasErrors()) {

			log.error("Los datos introducidos no cumplen ciertas condiciones, revisar los campos");

			return VIEWS_PRODUCTOS_CREATE_OR_UPDATE_FORM;
		}
		else {
			producto.setId(productoId);
			try {
				this.productoService.save(producto);
			} catch (WrongDataProductosException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				log.info("Producto editado satisfactoriamente");
			}
			return "redirect:/productos/{productoId}";
		}
	}

}
