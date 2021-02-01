package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.samples.petclinic.model.Reserva;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.service.ReservaService;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("restaurantes/{restauranteId}/reservas")
public class ReservaController {
	
private static final String VIEWS_RESERVAS_CREATE_OR_UPDATE_FORM = "reservas/editReservas";
	
	@Autowired
	private ReservaService reservaService;
	@Autowired
	private RestauranteService restauranteService;


	
	@InitBinder("reserva")
	public void initReservaBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new ReservaValidator());
		log.info("inicializando DataBinder");
	}
	
	@GetMapping()
	public String listadoReservas(@PathVariable("restauranteId") int restauranteId, ModelMap modelMap) {
		String vista = "reservas/listadoReservas";
		Restaurante restaurante = restauranteService.findRestauranteById(restauranteId).get();
		modelMap.addAttribute("restaurante", restaurante);
		log.info("listando reserva de un restaurante");
		return vista;
	}
	
	@GetMapping(value = "/new")
	public String initCreationForm(@PathVariable("restauranteId") int restauranteId, ModelMap modelMap) {
		modelMap.addAttribute("reserva", new Reserva());
		modelMap.addAttribute("restaurante", restauranteService.findRestauranteById(restauranteId).get());
		log.info("inicializando creación de reserva en un restaurante");
		return VIEWS_RESERVAS_CREATE_OR_UPDATE_FORM;
	}
	
	@GetMapping(path = "/{reservaId}/edit")
	public String initUpdateForm(@PathVariable("reservaId") int reservaId, @PathVariable("restauranteId") int restauranteId, ModelMap modelMap) {
		Reserva reserva = this.reservaService.findReservaById(reservaId).get();
		modelMap.addAttribute("restaurante", restauranteService.findRestauranteById(restauranteId).get());
		modelMap.addAttribute(reserva);
		log.info("inicializando edición de una reserva");
		return VIEWS_RESERVAS_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(path="/save")
	public String salvarReservas(@PathVariable("restauranteId") int restauranteId, @Valid Reserva reserva, BindingResult res, ModelMap modelMap) {
		String vista = "reservas/listadoReservas";
		if(res.hasErrors()) {
			modelMap.addAttribute("reserva", reserva);
			modelMap.addAttribute("restaurante", restauranteService.findRestauranteById(restauranteId).get());
			log.warn("error de validacion");
			return VIEWS_RESERVAS_CREATE_OR_UPDATE_FORM;
		}else {
			reservaService.save(reserva);
			modelMap.addAttribute("message", "Reserva guardado con exito");
			vista=listadoReservas(restauranteId, modelMap);
			log.info("Reserva guardada");
			return vista;
		}
	}
	
	@GetMapping(path="/delete/{reservaId}")
	public String borrarReserva(@PathVariable("reservaId") int reservaId, @PathVariable("restauranteId") int restauranteId, ModelMap modelMap) {
		String vista = "reservas/listadoReservas";
		Optional<Reserva> reserva = reservaService.findReservaById(reservaId);
		if(reserva.isPresent()) {
			reservaService.delete(reserva.get());
			modelMap.addAttribute("message","Reserva borrada con exito");
			vista= listadoReservas(restauranteId, modelMap);
			log.info("reserva eliminada");
		}else {
			modelMap.addAttribute("message","Reserva no encontrada");
			vista= listadoReservas(restauranteId, modelMap);
			log.warn("reserva no encontrada");
		}		
		return vista;
	}

}

