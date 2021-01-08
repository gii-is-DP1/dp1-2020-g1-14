package org.springframework.samples.petclinic.web;

import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.samples.petclinic.model.Ingrediente;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;

import org.springframework.samples.petclinic.model.Reserva;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.service.ReservaService;
import org.springframework.samples.petclinic.service.RestauranteService;

import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

@RequestMapping("restaurantes/{restauranteId}/reservas")


public class ReservaController {
	
private static final String VIEWS_RESERVAS_CREATE_OR_UPDATE_FORM = "/restaurantes/{restaurantesId}/reservas/editReservas";
	
	@Autowired
	private ReservaService reservaService;
	@Autowired

	private RestauranteService restauranteService;

	
	@InitBinder("reserva")
	public void initReservaBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new ReservaValidator());
	}
	
	@GetMapping()
	public String listadoReservas(@PathVariable("restauranteId") int restauranteId, ModelMap modelMap) {
		String vista = "reservas/listadoReservas";
		Restaurante restaurante = restauranteService.findRestauranteById(restauranteId).get();
		//Iterable<Reserva> reservas= restaurante.getReservas();
		modelMap.addAttribute("restaurante", restaurante);
		//modelMap.addAttribute("reservas", reservas);

		return vista;
	}
	
	@GetMapping(value = "/new")
	public String initCreationForm(@PathVariable("restauranteId") int restauranteId, ModelMap modelMap) {
		modelMap.addAttribute("reserva", new Reserva());
		modelMap.addAttribute("restaurante", restauranteService.findRestauranteById(restauranteId).get());
		return VIEWS_RESTAURANTES_CREATE_OR_UPDATE_FORM;
	}
	
	@GetMapping(path = "/{reservaId}/edit")
	public String initUpdateForm(@PathVariable("reservaId") int reservaId, @PathVariable("restauranteId") int restauranteId, ModelMap modelMap) {
		Reserva reserva = this.reservaService.findReservaById(reservaId).get();
		modelMap.addAttribute("restaurante", restauranteService.findRestauranteById(restauranteId).get());
		modelMap.addAttribute(reserva);
		return VIEWS_RESTAURANTES_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(path="/save")
	public String salvarReservas(@PathVariable("restauranteId") int restauranteId, @Valid Reserva reserva, BindingResult res, ModelMap modelMap) {

		String vista = "reservas/listadoReservas";
		if(res.hasErrors()) {
			modelMap.addAttribute("restauranteId", restauranteId);
			modelMap.addAttribute("reserva", reserva);
			modelMap.addAttribute("restaurante", restauranteService.findRestauranteById(restauranteId).get());
			return VIEWS_RESTAURANTES_CREATE_OR_UPDATE_FORM;
		}else {
			reservaService.save(reserva);
			modelMap.addAttribute("message", "Reserva guardado con exito");
			vista=listadoReservas(restauranteId, modelMap);
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
		}else {
			modelMap.addAttribute("message","Reserva no encontrada");
			vista= listadoReservas(restauranteId, modelMap);
		}		
		return vista;
	}

}
