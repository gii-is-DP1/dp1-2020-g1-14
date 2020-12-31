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
@RequestMapping("/restaurantes/{restaurantesId}/reservas")
public class ReservaController {
	
private static final String VIEWS_RESERVAS_CREATE_OR_UPDATE_FORM = "/restaurantes/{restaurantesId}/reservas/editReservas";
	
	@Autowired
	private ReservaService reservaService;
	@Autowired
	private RestauranteService resService;
	
	@InitBinder("reserva")
	public void initReservaBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new ReservaValidator());
	}
	
	@GetMapping()
	public String listadoReservas(@PathVariable("restaurantesId") int restauranteId,ModelMap modelMap) {
		String vista = "reservas/listadoReservas";
		Restaurante restaurante = resService.findRestauranteById(restauranteId).get();
		Set<Reserva> reservas = restaurante.getReservas();
		modelMap.addAttribute("reservas", reservas);
		modelMap.addAttribute("restauranteId", restauranteId);
		return vista;
	}
	
	@GetMapping(value = "/new")
	public String añadirReserva(@PathVariable("restaurantesId") int restauranteId,ModelMap modelMap) {
		String vista = "reservas/editarReserva";
		modelMap.addAttribute("restauranteId", restauranteId);
		modelMap.addAttribute("reserva", new Reserva());
		return vista;
	}

	

	
	@PostMapping(value = "/{reservasId}/edit")
	public String processUpdateReservaForm(@Valid Reserva reserva, BindingResult result, @PathVariable("reservasId") int reservaId,ModelMap modelMap) {
		if (result.hasErrors()) {
			return VIEWS_RESERVAS_CREATE_OR_UPDATE_FORM;
		}
		else {
			reserva.setId(reservaId);
			this.reservaService.save(reserva);
			return "redirect:/reservas/{reservaId}";
		}
	}
	
	@PostMapping(path="/save")
	public String salvarReservas(@PathVariable("restaurantesId") int restauranteId,@Valid Reserva reserva, BindingResult res, ModelMap modelMap) {
		String vista = "reservas/listadoReservas";
		if(res.hasErrors()) {
			modelMap.addAttribute("restauranteId", restauranteId);
			modelMap.addAttribute("reserva", reserva);
			return "restaurantes/"+restauranteId+"/reservas/editReservas";
		}else {
			reservaService.save(reserva);
			modelMap.addAttribute("message", "Reserva guardada");
			vista=listadoReservas(restauranteId,modelMap);
		}
		return vista;
	}
	
	@GetMapping(path="/delete/{reservasId}")
	public String borrarReserva(@PathVariable("reservasId") int reservasId, ModelMap modelMap) {
		String vista = "/restaurantes/{restauranteId}/reservas/listadoReservas";
		Optional<Reserva> reserva = reservaService.findReservaById(reservasId);
		if(reserva.isPresent()) {
			reservaService.delete(reserva.get());
			modelMap.addAttribute("message","Reserva borrada con exito");
		}else {
			modelMap.addAttribute("message","Reserva no encontrada");
		}		
		return vista;
	}

}
