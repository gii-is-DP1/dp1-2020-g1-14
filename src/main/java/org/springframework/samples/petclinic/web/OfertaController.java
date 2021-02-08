package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Oferta;
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
@RequestMapping("/restaurantes/{restauranteId}/ofertas")
public class OfertaController {
	
	private static final Logger log = (Logger) LoggerFactory.getLogger(OfertaController.class);
	
	public static final String VIEWS_OFERTAS_CREATE_OR_UPDATE_FORM = "ofertas/editOferta";
	
	@Autowired
	private OfertaService ofertaService;
	@Autowired
	private RestauranteService resService;
	
	
	//Obtener la lista de ofertas
	@GetMapping()
	public String listadoOfertas(ModelMap modelMap, @PathVariable("restauranteId") int restauranteId) {
		String vista = "ofertas/listadoOfertas";
		Iterable<Oferta> ofertas = ofertaService.findOfertasByRestauranteId(restauranteId);
		modelMap.addAttribute("ofertas", ofertas);
		modelMap.addAttribute("restauranteId", restauranteId);
		
		log.info("Mostrando lista de ofertas");
		
		return vista;
	}
	
	//Crear una nueva oferta.
	@GetMapping(path="/new")
	public String crearOferta(ModelMap modelMap, @PathVariable("restauranteId") int restauranteId) {
		String view ="ofertas/editOferta";
		Restaurante restaurante= this.resService.findRestauranteById(restauranteId).get();
		modelMap.addAttribute("restauranteId", restauranteId);
		modelMap.addAttribute("restaurante", restaurante);
		modelMap.addAttribute("oferta", new Oferta());
		
		log.info("Operación para añadir oferta en ejecucion con id");
		
		return view;
	}
	
	//Guardar una nueva oferta.
	@PostMapping(path="/save/{ofertaId}")
	public String salvarOferta(@Valid Oferta oferta, BindingResult result, ModelMap modelMap, @PathVariable("restauranteId") int restauranteId, 
								@RequestParam(value = "version", required = false) Integer version, @PathVariable("ofertaId") int ofertaId) {
	if(result.hasErrors())
	{
		Restaurante restaurante= this.resService.findRestauranteById(restauranteId).get();
		modelMap.addAttribute("restauranteId", restauranteId);
		modelMap.addAttribute("oferta", oferta);
		modelMap.addAttribute("restaurante", restaurante);
		
		log.error("Los datos introducidos no cumplen ciertas condiciones, revisar los campos");
		
		return "ofertas/editOferta";
	}else {
		Oferta ofertaToUpdate = ofertaService.findOfertaById(ofertaId).get();
		if(ofertaToUpdate.getVersion() != version) {
			log.error("Las versiones de oferta no coinciden: ofertaToUpdate version " + ofertaToUpdate.getVersion() + " oferta version "+version);
			Restaurante restaurante= this.resService.findRestauranteById(restauranteId).get();
			modelMap.addAttribute("message", "Ha ocurrido un error inesperado por favor intentalo de nuevo");
			return listadoOfertas(modelMap, restauranteId);
		}
		ofertaService.save(oferta);
		modelMap.addAttribute("message", "Offer successfully saved!");
		
		log.info("Oferta creada con éxito con id "+ofertaId);
		return "redirect:/restaurantes/{restauranteId}/ofertas";
	}
	}
	
	@PostMapping(path="/save")
	public String salvarOferta(@Valid Oferta oferta, BindingResult result, ModelMap modelMap, @PathVariable("restauranteId") int restauranteId) {
	if(result.hasErrors())
	{
		Restaurante restaurante= this.resService.findRestauranteById(restauranteId).get();
		modelMap.addAttribute("restauranteId", restauranteId);
		modelMap.addAttribute("oferta", oferta);
		modelMap.addAttribute("restaurante", restaurante);
		
		log.error("Los datos introducidos no cumplen ciertas condiciones, revisar los campos");
		
		return "ofertas/editOferta";
	}else {
		ofertaService.save(oferta);	
		log.info("Oferta creada con éxito");
		return "redirect:/restaurantes/{restauranteId}/ofertas";
	}
	}
	
	//Eliminar una oferta ya existente
	@GetMapping(path="delete/{ofertaId}")
	public String borrarOferta(@PathVariable("ofertaId") int ofertaId, ModelMap modelMap, @PathVariable("restauranteId") int restauranteId) {
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
	
	//Editar una oferta ya existente.
	@GetMapping(path="/edit/{ofertaId}") 
	public String initUpdateForm(@PathVariable("ofertaId") int ofertaId, ModelMap model, @PathVariable("restauranteId") int restauranteId) {
		Oferta oferta= this.ofertaService.findOfertaById(ofertaId).get();
		Restaurante restaurante= this.resService.findRestauranteById(restauranteId).get();
		model.addAttribute("oferta", oferta);
		model.addAttribute("restaurante", restaurante);
		
		log.info("Operación para editar oferta en ejecucion");
		
		return VIEWS_OFERTAS_CREATE_OR_UPDATE_FORM;
	}
	

}
