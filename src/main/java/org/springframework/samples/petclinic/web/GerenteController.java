package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Gerente;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.service.GerenteService;
import org.springframework.samples.petclinic.service.RestauranteService;
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

import antlr.collections.List;

@Controller
@RequestMapping("/gerentes")
public class GerenteController {

	@Autowired
	private GerenteService gerenteService;
	@Autowired
	private RestauranteService restauranteService;
	
	private static final String VIEWS_GERENTES_CREATE_OR_UPDATE_FORM = "gerentes/editarGerente";
	
	@ModelAttribute("nombres")
	public Iterable<Restaurante> populateRestaurantes() {
		return this.restauranteService.findAll();
	}	
	

	@InitBinder("restaurante")
	public void initRestauranteBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new RestauranteValidator());
	}
	
	@GetMapping()
	public String listadoGerentes(ModelMap modelMap) {
		String vista ="gerentes/listadoGerentes";
		Iterable<Gerente> gerentes = gerenteService.findAll();
		modelMap.addAttribute("gerentes",gerentes);
		return vista;
	}
	
	@GetMapping(path="/new")
	public String crearGerente(ModelMap modelMap) {
		String view ="gerentes/editarGerente";
		Iterable<Restaurante> restaurantesIt = restauranteService.findAll();
		ArrayList<Integer> restaurantes = new ArrayList<>();
		for(Restaurante restaurante: restaurantesIt) {
			restaurantes.add(restaurante.getId());
		}
		modelMap.addAttribute("restaurantes", restaurantes);
		modelMap.addAttribute("gerente", new Gerente());
		return view;
	}
	
	@PostMapping(path="/save")
	public String salvarGerente(@Valid Gerente gerente, BindingResult result, ModelMap modelMap) {
	String view="gerentes/listadoGerentes";
	if(result.hasErrors())
	{
		Iterable<Restaurante> restaurantesIt = restauranteService.findAll();
		ArrayList<Integer> restaurantes = new ArrayList<>();
		for(Restaurante restaurante: restaurantesIt) {
			restaurantes.add(restaurante.getId());
		}
		modelMap.addAttribute("restaurantes", restaurantes);
		modelMap.addAttribute("gerentes", gerente);
		return "gerentes/editarGerente";
	}else {
		gerenteService.save(gerente);
		modelMap.addAttribute("message", "Event successfully saved!");
		view=listadoGerentes(modelMap);
	}
	return view;
	}
	
	@GetMapping(path = "/{gerenteId}/edit")
	public String initUpdateForm(@PathVariable("gerenteId") int gerenteId, ModelMap model) {
		Gerente gerente = this.gerenteService.findGerenteById(gerenteId).get();
		model.addAttribute(gerente);
		return VIEWS_GERENTES_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/{gerenteId}/edit")
	public String processUpdateOwnerForm(@Valid Gerente gerente, BindingResult result,
			@PathVariable("gerenteId") int gerenteId) {
		if (result.hasErrors()) {
			return VIEWS_GERENTES_CREATE_OR_UPDATE_FORM;
		}
		else {
			gerente.setId(gerenteId);
			this.gerenteService.save(gerente);
			return "redirect:/gerentes/{gerenteId}";
		}
	}
	
	@GetMapping(path="delete/{gerenteId}")
	public String borrarGerente(@PathVariable("gerenteId") int gerenteId, ModelMap modelMap) {
	String view="gerentes/listadoGerentes";
	Optional<Gerente> gerente=gerenteService.findGerenteById(gerenteId);
	if(gerente.isPresent()) {
		gerenteService.delete(gerente.get());
		modelMap.addAttribute("message","Event succesfully deleted!");
		view=listadoGerentes(modelMap);
	}else {
		modelMap.addAttribute("message","Event not found!");
		view=listadoGerentes(modelMap);
	}
	return view;
}	
	
}
