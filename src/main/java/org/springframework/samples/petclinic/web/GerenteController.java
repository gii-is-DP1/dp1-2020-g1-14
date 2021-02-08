package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Gerente;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.service.GerenteService;
import org.springframework.samples.petclinic.service.ProductoService;
import org.springframework.samples.petclinic.service.RestauranteService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/restaurantes/{restauranteId}/gerentes")
public class GerenteController {

	@Autowired
	private GerenteService gerenteService;
	@Autowired
	private RestauranteService restauranteService;
	@Autowired
	private GerenteValidator gerenteValidator;
	@Autowired
	private ProductoService productoService;

	private static final String VIEWS_GERENTES_CREATE_OR_UPDATE_FORM = "gerentes/editarGerente";

	@InitBinder("gerente")
	public void initGerenteBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(gerenteValidator);
		log.info("inicializando DataBinder");
	}

	@GetMapping()
	public String listadoGerentes(ModelMap modelMap, @PathVariable("restauranteId") int restauranteId) {
		String vista ="gerentes/listadoGerentes";
		Iterable<Gerente> gerentes = gerenteService.findAll();
		modelMap.addAttribute("gerentes",gerentes);
		Restaurante restaurante = restauranteService.findRestauranteById(restauranteId).get();
		modelMap.addAttribute("restaurante",restaurante);

		log.info("Mostrando listado gerentes");
		return vista;
	}

	@GetMapping(path="/new")
	public String crearGerente(ModelMap modelMap, @PathVariable("restauranteId") int restauranteId) {
		Restaurante restaurante = restauranteService.findRestauranteById(restauranteId).get();
		if(restaurante.getGerente()!=null) {
			modelMap.addAttribute("message","Para añadir un nuevo gerente primero elimina el que ya existe");
			Iterable<Producto> productos = productoService.findProductosByRestauranteId(restauranteId);
			modelMap.addAttribute("productos",productos);
			modelMap.addAttribute("restaurante",restaurante);
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String name = auth.getName();
			modelMap.addAttribute("username", name);
			return "restaurantes/restauranteDetails";
		}
		String view ="gerentes/editarGerente";
		modelMap.addAttribute("gerente", new Gerente());
		modelMap.addAttribute("restauranteId", restauranteId);
		log.info("Inicialización creación de gerente");
		return view;
	}



	@PostMapping(path="/save")
	public String salvarGerente(@Valid Gerente gerente, BindingResult result, ModelMap modelMap, @PathVariable("restauranteId") int restauranteId) {
		String view="gerentes/listadoGerentes";
		if(result.hasErrors())
		{
			modelMap.addAttribute("gerente", gerente);
			modelMap.addAttribute("restauranteId", restauranteId);
			log.warn("Error de validacion");
			return "gerentes/editarGerente";
		}else {
			Restaurante restaurante = restauranteService.findRestauranteById(restauranteId).get();
			modelMap.addAttribute("restaurante", restaurante);
			Iterable<Producto> productos = productoService.findProductosByRestauranteId(restauranteId);
			modelMap.addAttribute("productos",productos);
			gerenteService.save(gerente);
			restaurante.setGerente(gerente);
			restauranteService.save(restaurante);
			modelMap.addAttribute("message", "gerente successfully saved!");
			log.info("Gerente creado");
			view="restaurantes/restauranteDetails";
		}
		return view;
	}

	@GetMapping(path = "/{gerenteId}/edit")
	public String initUpdateForm(@PathVariable("gerenteId") int gerenteId, ModelMap model, @PathVariable("restauranteId") int restauranteId) {
		Gerente gerente = this.gerenteService.findGerenteById(gerenteId).get();
		model.addAttribute("gerente", gerente);
		log.info("Inicialización de la edición de gerente");
		return VIEWS_GERENTES_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/{gerenteId}/edit")
	public String processUpdateOwnerForm(@Valid Gerente gerente, BindingResult result,
			@PathVariable("gerenteId") int gerenteId) {
		if (result.hasErrors()) {
			log.warn("error de validación");
			return VIEWS_GERENTES_CREATE_OR_UPDATE_FORM;
		}
		else {
			gerente.setId(gerenteId);
			this.gerenteService.save(gerente);
			log.info("Guardado de cambios realizados");
			return "redirect:/gerentes/{gerenteId}";
		}
	}

	@GetMapping(path="delete/{gerenteId}")
	public String borrarGerente(@PathVariable("gerenteId") int gerenteId, ModelMap modelMap, @PathVariable("restauranteId") int restauranteId) {
		log.info("asjklgadhlskjghkldjfghfilsg");
		String view="restaurantes/restauranteDetails";
		Optional<Gerente> gerente=gerenteService.findGerenteById(gerenteId);

		Restaurante restaurante = restauranteService.findRestauranteById(restauranteId).get();
		Iterable<Producto> productos = productoService.findProductosByRestauranteId(restauranteId);
		modelMap.addAttribute("productos",productos);
		modelMap.addAttribute("restaurante",restaurante);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		modelMap.addAttribute("username", name);

		if(gerente.isPresent()) {
			gerenteService.delete(gerente.get());
			modelMap.addAttribute("message","Gerente borrado");
			log.info("Gerente borrado");
		}else {
			modelMap.addAttribute("message","Gerente no encontrado");
			log.warn("Gerente no encontrado");
		}
		return view;
	}	

}
