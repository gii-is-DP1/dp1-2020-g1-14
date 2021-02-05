package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Oferta;
import org.springframework.samples.petclinic.model.Proveedor;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.service.OfertaService;
import org.springframework.samples.petclinic.service.RestauranteService;
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
@RequestMapping("/restaurantes/{restaurantesId}/ofertas")
public class OfertaController {
	
	private static final Logger log = (Logger) LoggerFactory.getLogger(OfertaController.class);
	
	public static final String VIEWS_OFERTAS_CREATE_OR_UPDATE_FORM = "ofertas/editOferta";
	
	@Autowired
	private OfertaService ofertaService;
	@Autowired
	private RestauranteService resService;
	
	@GetMapping()
	public String listadoOfertas(ModelMap modelMap, @PathVariable("restaurantesId") int restauranteId) {
		String vista = "ofertas/listadoOfertas";
		Restaurante restaurante= this.resService.findRestauranteById(restauranteId).get();
//		Iterable<Oferta> ofertas = ofertaService.findAll();
		modelMap.addAttribute("restaurante", restaurante);
		
		log.info("Mostrando lista de ofertas");
		
		return vista;
	}
	
	@GetMapping(path="/new")
	public String crearOferta(ModelMap modelMap, @PathVariable("restaurantesId") int restauranteId) {
		String view ="ofertas/editOferta";
		Restaurante restaurante= this.resService.findRestauranteById(restauranteId).get();
		modelMap.addAttribute("restaurante", restaurante);
		modelMap.addAttribute("oferta", new Oferta());
		
		log.info("Operación para añadir oferta en ejecucion con id");
		
		return view;
	}
	
	@PostMapping(path="/save/{ofertaId}")
	public String salvarOferta(@Valid Oferta oferta, BindingResult result, ModelMap modelMap, @PathVariable("restaurantesId") int restauranteId, 
								@RequestParam(value = "version", required = false) Integer version, @PathVariable("ofertaId") int ofertaId) {
//	String view;
	if(result.hasErrors())
	{
		Restaurante restaurante= this.resService.findRestauranteById(restauranteId).get();
		modelMap.addAttribute("restaurante", restaurante);
		modelMap.addAttribute("oferta", oferta);
		
		log.error("Los datos introducidos no cumplen ciertas condiciones, revisar los campos");
		
		return "ofertas/editOferta";
	}else {
		Oferta ofertaToUpdate = ofertaService.findOfertaById(ofertaId).get();
		if(ofertaToUpdate.getVersion() != version) {
			log.error("Las versiones de oferta no coinciden: ofertaToUpdate version " + ofertaToUpdate.getVersion() + " oferta version "+version);
			Restaurante restaurante= this.resService.findRestauranteById(restauranteId).get();
			modelMap.addAttribute("restaurante", restaurante);
			modelMap.addAttribute("oferta", oferta);
			modelMap.addAttribute("message", "Ha ocurrido un error inesperado por favor intentalo de nuevo");
			return "ofertas/editOferta";
		}
		ofertaService.save(oferta);
		modelMap.addAttribute("message", "Offer successfully saved!");
//		view=listadoOfertas(modelMap, restauranteId);
		
		log.info("Oferta creada con éxito con id "+ofertaId);
		return "redirect:/restaurantes/{restaurantesId}/ofertas";
	}
//	return view;
	}
	
	@PostMapping(path="/save")
	public String salvarOferta(@Valid Oferta oferta, BindingResult result, ModelMap modelMap, @PathVariable("restaurantesId") int restauranteId) {
//	String view;
	if(result.hasErrors())
	{
		Restaurante restaurante= this.resService.findRestauranteById(restauranteId).get();
		modelMap.addAttribute("restaurante", restaurante);
		modelMap.addAttribute("oferta", oferta);
		
		log.error("Los datos introducidos no cumplen ciertas condiciones, revisar los campos");
		
		return "ofertas/editOferta";
	}else {
		ofertaService.save(oferta);
//		modelMap.addAttribute("message", "Offer successfully saved!");
//		view=listadoOfertas(modelMap, restauranteId);
		
		log.info("Oferta creada con éxito");
		return "redirect:/restaurantes/{restaurantesId}/ofertas";
	}
//	return view;
	}
	
	@GetMapping(path="delete/{ofertaId}")
	public String borrarOferta(@PathVariable("ofertaId") int ofertaId, ModelMap modelMap, @PathVariable("restaurantesId") int restauranteId) {
	String view="ofertas/listadoOferta";
	Optional<Oferta> oferta= ofertaService.findOfertaById(ofertaId);
	if(oferta.isPresent()) {
		ofertaService.delete(oferta.get());
		modelMap.addAttribute("message","Event succesfully deleted!");
		view=listadoOfertas(modelMap, restauranteId);
		
		log.info("Oferta eliminada con éxito");
	}else {
		modelMap.addAttribute("message","Event not found!");
		view=listadoOfertas(modelMap, restauranteId);
		
		log.error("No se ha encontrado la oferta para eliminar");
	}
	return view;
}
	
	@GetMapping(path="/edit/{ofertaId}") 
	public String initUpdateForm(@PathVariable("ofertaId") int ofertaId, ModelMap model, @PathVariable("restaurantesId") int restauranteId) {
		Oferta oferta= this.ofertaService.findOfertaById(ofertaId).get();
		Restaurante restaurante= this.resService.findRestauranteById(restauranteId).get();
		model.addAttribute("oferta", oferta);
		model.addAttribute("restaurante", restaurante);
		
		log.info("Operación para editar oferta en ejecucion");
		
		return VIEWS_OFERTAS_CREATE_OR_UPDATE_FORM;
	}
	
//	@PostMapping(value="/edit/{ofertaId}")
//	public String processUpdateOfertaForm(@Valid Oferta oferta, BindingResult result, @PathVariable("ofertaId") int ofertaId) {
//		if(result.hasErrors()) {
//			
//			log.error("Los datos introducidos no cumplen ciertas condiciones, revisar los campos");
//			
//			return VIEWS_OFERTAS_CREATE_OR_UPDATE_FORM;
//		}
//		else {
//			this.ofertaService.save(oferta);
//			
//			log.info("Oferta editada satisfactoriamente");
//		}
//		return "redirect:/oferta/{ofertaId}";
//	}
}
