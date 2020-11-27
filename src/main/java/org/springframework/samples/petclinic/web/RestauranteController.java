package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Propietario;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.service.PropietarioService;
import org.springframework.samples.petclinic.service.RestauranteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	private static final String VIEWS_RESTAURANTES_CREATE_OR_UPDATE_FORM = "restaurantes/editRestaurantes";
	
	@Autowired
	private RestauranteService restauranteService;
	private PropietarioService propietarioService;
	
	@GetMapping()
	public String listadoRestaurantes(ModelMap modelMap) {
		String vista = "restaurantes/listadoRestaurantes";
		Iterable<Restaurante> restaurantes= restauranteService.findAll();
		modelMap.addAttribute("restaurantes", restaurantes);
		return vista;
	}
	
	@GetMapping(path = "/new")
	public String crearRestaurantes(ModelMap modelMap) {
		String view = "restaurantes/editRestaurantes";
		modelMap.addAttribute("restaurantes", new Restaurante());
		return view;
	}
	
	/* METODO TODAVÍA SIN IMPLEMENTAR
	 * @GetMapping(path = "/restuarantes/new")
	public String initCreationForm(Propietario propietario, ModelMap model) {
		Restaurante restaurante
	}*/
	
	@PostMapping(path="/save")
	public String salvarRestaurantes(@Valid Restaurante restaurante, BindingResult res, ModelMap modelMap) {
		String vista = "restaurantes/listadoRestaurantes";
		if(res.hasErrors()) {
			modelMap.addAttribute("restaurante", restaurante);
			return "restaurantes/editRestaurantes";
		}else {
			restauranteService.save(restaurante);
			modelMap.addAttribute("message", "Restaurante guardado con exito");
			vista=listadoRestaurantes(modelMap);
		}
		return vista;
	}
	
	@GetMapping(path="/delete/{restaurantesId}")
	public String borrarRestaurante(@PathVariable("restaurantesId") int restaurantesId, ModelMap modelMap) {
		String vista = "restaurantes/listadoRestaurantes";
		Optional<Restaurante> restaurante = restauranteService.findRestauranteById(restaurantesId);
		if(restaurante.isPresent()) {
			restauranteService.delete(restaurante.get());
			modelMap.addAttribute("message","Restaurante borrado con exito");
			vista= listadoRestaurantes(modelMap);
		}else {
			modelMap.addAttribute("message","Restaurante no encontrado");
			vista= listadoRestaurantes(modelMap);
		}		
		return vista;
	}
}
