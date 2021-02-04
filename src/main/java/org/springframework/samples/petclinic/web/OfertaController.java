package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Oferta;
import org.springframework.samples.petclinic.model.Proveedor;
import org.springframework.samples.petclinic.service.OfertaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("/ofertas")
public class OfertaController {
	
	private static final Logger log = (Logger) LoggerFactory.getLogger(OfertaController.class);
	
	public static final String VIEWS_OFERTAS_CREATE_OR_UPDATE_FORM = "ofertas/editOferta";
	
	@Autowired
	private OfertaService ofertaService;
	
	@GetMapping()
	public String listadoOfertas(ModelMap modelMap) {
		String vista = "ofertas/listadoOfertas";
		Iterable<Oferta> ofertas = ofertaService.findAll();
		modelMap.addAttribute("ofertas", ofertas);
		
		log.info("Mostrando lista de ofertas");
		
		return vista;
	}
	
	@GetMapping(path="/new")
	public String crearOferta(ModelMap modelMap) {
		String view ="ofertas/editOferta";
		modelMap.addAttribute("oferta", new Oferta());
		
		log.info("Operación para añadir oferta en ejecucion");
		
		return view;
	}
	
	@PostMapping(path="/save")
	public String salvarOferta(@Valid Oferta oferta, BindingResult result, ModelMap modelMap) {
	String view="ofertas/listadoOferta";
	if(result.hasErrors())
	{
		modelMap.addAttribute("oferta", oferta);
		
		log.error("Los datos introducidos no cumplen ciertas condiciones, revisar los campos");
		
		return "ofertas/editOferta";
	}else {
		ofertaService.save(oferta);
		modelMap.addAttribute("message", "Ofert successfully saved!");
		view=listadoOfertas(modelMap);
		
		log.info("Oferta creada con éxito");
	}
	return view;
	}
	
	@GetMapping(path="delete/{ofertaId}")
	public String borrarOferta(@PathVariable("ofertaId") int ofertaId, ModelMap modelMap) {
	String view="ofertas/listadoOferta";
	Optional<Oferta> oferta= ofertaService.findOfertaById(ofertaId);
	if(oferta.isPresent()) {
		ofertaService.delete(oferta.get());
		modelMap.addAttribute("message","Event succesfully deleted!");
		view=listadoOfertas(modelMap);
		
		log.info("Oferta eliminada con éxito");
	}else {
		modelMap.addAttribute("message","Event not found!");
		view=listadoOfertas(modelMap);
		
		log.error("No se ha encontrado la oferta para eliminar");
	}
	return view;
}
	
	@GetMapping(path="/{ofertaId}/edit") 
	public String initUpdateForm(@PathVariable("ofertaId") int ofertaId, ModelMap model) {
		Oferta oferta= this.ofertaService.findOfertaById(ofertaId).get();
		model.addAttribute(oferta);
		
		log.info("Operación para editar oferta en ejecucion");
		
		return VIEWS_OFERTAS_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value="/{ofertaId}/edit")
	public String processUpdateOfertaForm(@Valid Oferta oferta, BindingResult result, @PathVariable("ofertaId") int ofertaId) {
		if(result.hasErrors()) {
			
			log.error("Los datos introducidos no cumplen ciertas condiciones, revisar los campos");
			
			return VIEWS_OFERTAS_CREATE_OR_UPDATE_FORM;
		}
		else {
			oferta.setId(ofertaId);
			this.ofertaService.save(oferta);
			
			log.info("Oferta editada satisfactoriamente");
		}
		return "redirect:/oferta/{ofertaId}";
	}
}
