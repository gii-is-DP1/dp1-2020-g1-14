package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Reserva;
import org.springframework.samples.petclinic.service.ReservaService;
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
@RequestMapping("/reservas")
public class ReservaController {
	
private static final String VIEWS_RESTAURANTES_CREATE_OR_UPDATE_FORM = "reservas/editReservas";
	
	@Autowired
	private ReservaService reservaService;
	
	@InitBinder("reserva")
	public void initReservaBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new ReservaValidator());
	}
	
	@GetMapping()
	public String listadoReservas(ModelMap modelMap) {
		String vista = "reservas/listadoReservas";
		Iterable<Reserva> reservas= reservaService.findAll();
		modelMap.addAttribute("reservas", reservas);
		return vista;
	}
	
	/*@GetMapping(path = "/new")
	public String crearReservas(@Valid Reserva reserva, BindingResult result, ModelMap modelMap) {
		String view = "reservas/editReservas";
		modelMap.addAttribute("reserva", new Reserva());
		return view;
		if (result.hasErrors()) {
			modelMap.put("reserva", reserva);
			return VIEWS_RESTAURANTES_CREATE_OR_UPDATE_FORM;
		}
		else {
			return "redirect:/reservas/editReservas";
		}
	}*/
	
	@GetMapping(value = "/new")
	public String initCreationForm(ModelMap modelMap) {
		Reserva reserva = new Reserva();
		modelMap.put("reserva", reserva);
		//modelMap.addAttribute("reserva", new Reserva());
		return VIEWS_RESTAURANTES_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/new")
	public String processCreationForm(@Valid Reserva reserva, BindingResult result, ModelMap modelMap) {		
		if (result.hasErrors()) {
			modelMap.put("reserva", reserva);
			return VIEWS_RESTAURANTES_CREATE_OR_UPDATE_FORM;
		}else {
        	this.reservaService.save(reserva);
			return "redirect:/reservas/{reservaId}";
		}
	}
	
	@GetMapping(path = "/{reservasId}/edit")
	public String initUpdateForm(@PathVariable("reservasId") int reservaId, ModelMap model) {
		Reserva reserva = this.reservaService.findReservaById(reservaId).get();
		model.addAttribute(reserva);
		return VIEWS_RESTAURANTES_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/{reservasId}/edit")
	public String processUpdateReservaForm(@Valid Reserva reserva, BindingResult result, @PathVariable("reservasId") int reservaId,ModelMap modelMap) {
		if (result.hasErrors()) {
			modelMap.put("reserva", reserva);
			return VIEWS_RESTAURANTES_CREATE_OR_UPDATE_FORM;
		}
		else {
			reserva.setId(reservaId);
			this.reservaService.save(reserva);
			return "redirect:/reservas/{reservaId}";
		}
	}
	
	@PostMapping(path="/save")
	public String salvarReservas(@Valid Reserva reserva, BindingResult res, ModelMap modelMap) {
		String vista = "reservas/listadoReservas";
		if(res.hasErrors()) {
			modelMap.addAttribute("reserva", reserva);
			return "reservas/editReservas";
		}else {
			reservaService.save(reserva);
			modelMap.addAttribute("message", "Reserva guardado con exito");
			vista=listadoReservas(modelMap);
		}
		return vista;
	}
	
	@GetMapping(path="/delete/{reservasId}")
	public String borrarReserva(@PathVariable("reservasId") int reservasId, ModelMap modelMap) {
		String vista = "reservas/listadoReservas";
		Optional<Reserva> reserva = reservaService.findReservaById(reservasId);
		if(reserva.isPresent()) {
			reservaService.delete(reserva.get());
			modelMap.addAttribute("message","Reserva borrada con exito");
			vista= listadoReservas(modelMap);
		}else {
			modelMap.addAttribute("message","Reserva no encontrada");
			vista= listadoReservas(modelMap);
		}		
		return vista;
	}

}
