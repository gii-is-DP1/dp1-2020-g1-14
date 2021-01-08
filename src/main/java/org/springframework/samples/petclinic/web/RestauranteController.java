package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.service.PropietarioService;
import org.springframework.samples.petclinic.service.RestauranteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	private static final String VIEWS_RESTAURANTES_CREATE_OR_UPDATE_FORM = "restaurantes/editRestaurantes";
	
	@Autowired
	private RestauranteService restauranteService;
	//private PropietarioService propietarioService;
	
	/*@InitBinder("restaurante")
	public void initRestauranteBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new RestauranteValidator());
	}*/
	
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
		modelMap.addAttribute("restaurante", new Restaurante());
		return view;
	}
	
	@GetMapping(path = "/{restaurantesId}/edit")
	public String initUpdateForm(@PathVariable("restaurantesId") int restauranteId, ModelMap model) {
		Restaurante restaurante = this.restauranteService.findRestauranteById(restauranteId).get();
		model.addAttribute(restaurante);
		return VIEWS_RESTAURANTES_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/{restaurantesId}/edit")
	public String processUpdateOwnerForm(@Valid Restaurante restaurante, BindingResult result,
			@PathVariable("restaurantesId") int restauranteId) {
		if (result.hasErrors()) {
			return VIEWS_RESTAURANTES_CREATE_OR_UPDATE_FORM;
		}
		else {
			restaurante.setId(restauranteId);
			this.restauranteService.save(restaurante);
			return "redirect:/restaurantes/{restauranteId}";
		}
	}
	
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
	
	@GetMapping("/{restaurantesId}")
	public ModelAndView showOwner(@PathVariable("restaurantesId") int restaurantesId) {
		ModelAndView mav = new ModelAndView("restaurantes/restauranteDetails");
		mav.addObject("restaurante",this.restauranteService.findRestauranteById(restaurantesId).get());
		return mav;
	}
}
