package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.service.ProductoService;
import org.springframework.samples.petclinic.service.RestauranteService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	private static final String VIEWS_RESTAURANTES_CREATE_OR_UPDATE_FORM = "restaurantes/editRestaurantes";
	
	@Autowired
	private RestauranteService restauranteService;
	@Autowired
	private ProductoService productoService;
	
	@GetMapping()
	public String listadoRestaurantes(ModelMap modelMap) {
		String vista = "restaurantes/listadoRestaurantes";
		Iterable<Restaurante> restaurantes= restauranteService.findAll();
		modelMap.addAttribute("restaurantes", restaurantes);
		log.info("Mostrando lista de restaurantes");
		return vista;
	}
	
	@GetMapping(path = "/new")
	public String crearRestaurantes(ModelMap modelMap) {
		String view = "restaurantes/editRestaurantes";
		modelMap.addAttribute("restaurante", new Restaurante());
		log.info("Inicialización de creación de Restaurante");
		return view;
	}
	
	@GetMapping(path = "/{restauranteId}/edit")
	public String initUpdateForm(@PathVariable("restauranteId") int restauranteId, ModelMap model) {
		Restaurante restaurante = this.restauranteService.findRestauranteById(restauranteId).get();
		model.addAttribute(restaurante);
		log.info("Inicialización de edición de Restaurante");
		return VIEWS_RESTAURANTES_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(path="/save/{restauranteId}")
	public String salvarRestaurantes(@Valid Restaurante restaurante, BindingResult res, ModelMap modelMap,
			@RequestParam(value = "version", required = false) Integer version, @PathVariable("restauranteId") int restauranteId) {
		String vista = "restaurantes/listadoRestaurantes";
		if(res.hasErrors()) {
			modelMap.addAttribute("restaurante", restaurante);
			log.warn("Error de validación");
			return "restaurantes/editRestaurantes";
		}else {
			Restaurante resToUpdate = restauranteService.findRestauranteById(restauranteId).get();
			if(resToUpdate.getVersion() != version) {
				log.error("Las versiones de los restaurantes no coinciden: resToUpdate:" + resToUpdate.getVersion() + " Restaurante " + version);
				modelMap.addAttribute("message", "Ha ocurrido un error inesperado por favor intentalo de nuevo");
				modelMap.addAttribute("restaurante", restaurante);
				return listadoRestaurantes(modelMap);
			}
			restauranteService.save(restaurante);
			modelMap.addAttribute("message", "Restaurante guardado con exito");
			vista=listadoRestaurantes(modelMap);
			log.info("Restaurante creado");
		}
		return vista;
	}
	
	@PostMapping(path="/save")
	public String salvarRestaurantes(@Valid Restaurante restaurante, BindingResult res, ModelMap modelMap) {
		String vista = "restaurantes/listadoRestaurantes";
		if(res.hasErrors()) {
			modelMap.addAttribute("restaurante", restaurante);
			log.warn("Error de validación " + res.toString());
			log.warn("Error de validación");
			return "restaurantes/editRestaurantes";
		}else {
			restauranteService.save(restaurante);
			modelMap.addAttribute("message", "Restaurante guardado con exito");
			vista=listadoRestaurantes(modelMap);
			log.info("Restaurante creado");
		}
		return vista;
	}
	
	@GetMapping(path="/delete/{restauranteId}")
	public String borrarRestaurante(@PathVariable("restauranteId") int restauranteId, ModelMap modelMap) {
		String vista = "restaurantes/listadoRestaurantes";
		Optional<Restaurante> restaurante = restauranteService.findRestauranteById(restauranteId);
		if(restaurante.isPresent()) {
			restauranteService.delete(restaurante.get());
			modelMap.addAttribute("message","Restaurante borrado con exito");
			vista= listadoRestaurantes(modelMap);
			log.info("restaurante borrado");
		}else {
			modelMap.addAttribute("message","Restaurante no encontrado");
			vista= listadoRestaurantes(modelMap);
			log.warn("Restaurante no encontrado");
		}		
		return vista;
	}
	
	@GetMapping("/{restauranteId}")
	public ModelAndView showRestaurante(@PathVariable("restauranteId") int restauranteId) {
		ModelAndView mav = new ModelAndView("restaurantes/restauranteDetails");
		Restaurante restaurante = restauranteService.findRestauranteById(restauranteId).get();
		Iterable<Producto> productos = productoService.findProductosByRestauranteId(restauranteId);
		mav.addObject("productos",productos);
		mav.addObject("restaurante",restaurante);
		mav.addObject("gerente",restaurante.getGerente());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username
	    mav.addObject("username", name);
	      
		log.info("Mostrar restaurante indicado");
		return mav;
	}
}
