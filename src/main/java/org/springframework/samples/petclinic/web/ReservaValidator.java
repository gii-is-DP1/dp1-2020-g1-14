package org.springframework.samples.petclinic.web;


import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.samples.petclinic.model.Reserva;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;



public class ReservaValidator implements Validator {

	private static final String REQUIRED = "required";
	
	@Override
	public void validate(Object target, Errors errors) {
		Reserva reserva = (Reserva) target;
		Boolean evento = reserva.getEvento();
		Integer nPersonas = reserva.getnPersonas();
		
		//fecha validator
		LocalDate fecha = reserva.getFecha();
		if(fecha==null || fecha.isBefore(LocalDate.now())) {
			errors.rejectValue("fecha", REQUIRED+"  Introduzca una fecha válida",REQUIRED+"  Introduzca una fecha válida");
		}
		
		//nPersonas validator
		if(nPersonas==null) {
			errors.rejectValue("nPersonas", REQUIRED+"  es necesario indicar el número de personas", REQUIRED+"  es necesario indicar el número de personas");
		}
		
		// evento validation
		if(evento==null) {
			errors.rejectValue("evento", REQUIRED+"es necesario indicar si es evento", REQUIRED+"es necesario indicar si es evento");
		}
		if((evento && nPersonas<=10)) {
			errors.rejectValue("nPersonas", REQUIRED+"   Si es un evento, debe haber 10 personas o más", REQUIRED+"   Si es un evento, debe haber 10 personas o más");
		}
		
		// hora validation
		LocalTime HI = reserva.getHoraInicio();
		LocalTime HF = reserva.getHoraFin();
		
		if(HI==null || HI.isBefore(LocalTime.now()) && fecha.isEqual(LocalDate.now())) {
			errors.rejectValue("horaInicio", REQUIRED+"  Introduzca una hora válida",REQUIRED+"  Introduzca una hora válida");
		}
		
		if(HF==null || HF.isBefore(LocalTime.now()) && fecha.isEqual(LocalDate.now())) {
			errors.rejectValue("horaFin", REQUIRED+"  Introduzca una hora válida",REQUIRED+"  Introduzca una hora válida");
		}
		
		if(HF.isBefore(HI)) {
			errors.rejectValue("horaFin", REQUIRED+"  La hora de inicio debe ser anterior a la hora de fin",REQUIRED+"  La hora de inicio debe ser anterior a la hora de fin");
		}
		// Aforo validation
		Restaurante res = reserva.getRestaurante();
		if(res == null) {
			
		}else if (nPersonas>res.getAforores()) {
			errors.rejectValue("nPersonas", REQUIRED+"  No hay aforo suficiente", REQUIRED+"  No hay aforo suficiente");
		}

			
	}
	
	/**
	 * This Validator validates *just* Reserva instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Reserva.class.isAssignableFrom(clazz);
	}

}
