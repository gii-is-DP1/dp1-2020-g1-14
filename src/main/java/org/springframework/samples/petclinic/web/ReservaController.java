package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.samples.petclinic.model.Reserva;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.service.ClienteService;
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
@RequestMapping("restaurantes/{restauranteId}/reservas/{userName}")
public class ReservaController {
	
private static final String VIEWS_RESERVAS_CREATE_OR_UPDATE_FORM = "reservas/editReservas";
	
	@Autowired
	private ReservaService reservaService;
	@Autowired
	private RestauranteService restauranteService;
	@Autowired
	private ClienteService clienteService;
	
	@InitBinder("reserva")
	public void initReservaBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new ReservaValidator());
		log.info("inicializando DataBinder");
	}
	
	@GetMapping()
	public String listadoReservas(@PathVariable("restauranteId") int restauranteId, @PathVariable("userName") String usuario, ModelMap modelMap) {
		String vista = "reservas/listadoReservas";
		Restaurante restaurante = restauranteService.findRestauranteById(restauranteId).get();
		modelMap.addAttribute("restaurante", restaurante);
		modelMap.addAttribute("name", usuario);
		log.info("listando reserva de un restaurante indicado del usuario actual");
		return vista;
	}
	
	@GetMapping(value = "/new")
	public String initCreationForm(@PathVariable("restauranteId") int restauranteId, @PathVariable("userName") String usuario, ModelMap modelMap){
		modelMap.addAttribute("reserva", new Reserva());
		modelMap.addAttribute("restaurante", restauranteService.findRestauranteById(restauranteId).get());
		modelMap.addAttribute("username", usuario);
		log.info("inicializando creación de reserva en un restaurante");
		return VIEWS_RESERVAS_CREATE_OR_UPDATE_FORM;
	}
	
//	@GetMapping(path = "/{reservaId}/edit")
//	public String initUpdateForm(@PathVariable("reservaId") int reservaId, @PathVariable("restauranteId") int restauranteId, @PathVariable("userName") String usuario, ModelMap modelMap) {
//		Reserva reserva = this.reservaService.findReservaById(reservaId).get();
//		modelMap.addAttribute("restaurante", restauranteService.findRestauranteById(restauranteId).get());
//		modelMap.addAttribute("reserva",reserva);
//		modelMap.addAttribute("username", usuario);
//		log.info("inicializando edición de una reserva");
//		return VIEWS_RESERVAS_CREATE_OR_UPDATE_FORM;
//	}
	
	@PostMapping(path="/save")
	public String salvarReservas(@PathVariable("restauranteId") int restauranteId, @PathVariable("userName") String usuario, @Valid Reserva reserva, BindingResult res, ModelMap modelMap){
		String vista = "reservas/listadoReservas";
		if(res.hasErrors()) {
			modelMap.addAttribute("reserva", reserva);
			modelMap.addAttribute("restaurante", restauranteService.findRestauranteById(restauranteId).get());
			modelMap.addAttribute("username", usuario);
			log.warn("error de validacion");
			return VIEWS_RESERVAS_CREATE_OR_UPDATE_FORM;
		}else {
			if(clienteService.findClienteByUsuario(usuario).get().getMonedero()<restauranteService.findRestauranteById(restauranteId).get().getSenial()) {
				modelMap.addAttribute("message","No tiene dinero suficiente en la cuenta");
			}else {
				reserva.setCliente(clienteService.findClienteByUsuario(usuario).get());
				reservaService.save(reserva);
				modelMap.addAttribute("message", "Reserva guardado con exito");
				vista=listadoReservas(restauranteId, usuario, modelMap);
				log.info("Reserva guardada");
				
			}
			return vista;
		}
	}
	
	@GetMapping(path="/delete/{reservaId}")
	public String borrarReserva(@PathVariable("reservaId") int reservaId, @PathVariable("restauranteId") int restauranteId, @PathVariable("userName") String usuario, ModelMap modelMap) {
		String vista = "reservas/listadoReservas";
		Optional<Reserva> reserva = reservaService.findReservaById(reservaId);
		if(reserva.isPresent()) {
			if(reserva.get().getFecha().equals(LocalDate.now())) {
				if(reserva.get().getHoraInicio().isBefore(LocalTime.now().plusHours(1))) {
					reservaService.delete(reserva.get());
					clienteService.findClienteByUsuario(usuario).get().addMonedero(20);
					modelMap.addAttribute("message","Reserva borrada con exito");
					vista= listadoReservas(restauranteId, usuario, modelMap);
					log.info("reserva eliminada y se devuelve la señal");
				}else {
					reservaService.delete(reserva.get());
					modelMap.addAttribute("message","Reserva borrada con exito");
					vista= listadoReservas(restauranteId, usuario, modelMap);
					log.info("reserva eliminada y no se devuelve la señal");
				}
			}else if(reserva.get().getFecha().isBefore(LocalDate.now())){
				reservaService.delete(reserva.get());
				clienteService.findClienteByUsuario(usuario).get().addMonedero(20);
				modelMap.addAttribute("message","Reserva borrada con exito");
				vista= listadoReservas(restauranteId, usuario, modelMap);
				log.info("reserva eliminada y se devuelve la señal");
			}else {
				reservaService.delete(reserva.get());
				modelMap.addAttribute("message","Reserva borrada con exito");
				vista= listadoReservas(restauranteId, usuario, modelMap);
				log.info("reserva eliminada y no se devuelve la señal");
			}
			
		}else {
			modelMap.addAttribute("message","Reserva no encontrada");
			vista= listadoReservas(restauranteId, usuario, modelMap);
			log.warn("reserva no encontrada");
		}		
		return vista;
	}

}

