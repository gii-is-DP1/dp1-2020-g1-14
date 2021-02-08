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
		Restaurante res = reserva.getRestaurante();
		LocalTime HI = reserva.getHoraInicio();
		LocalTime HF = reserva.getHoraFin();
		
		//fecha validator
		LocalDate fecha = reserva.getFecha();
		if(fecha==null || fecha.isBefore(LocalDate.now())) {
			errors.rejectValue("fecha", REQUIRED+"  Introduzca una fecha válida",REQUIRED+"  Introduzca una fecha válida");
		}
		
		//nPersonas validator
		if(nPersonas==null || nPersonas<=0) {
			errors.rejectValue("nPersonas", REQUIRED+"  indique un número de personas adecuado", REQUIRED+"  indique un número de personas adecuado");
		}
		
		Integer aforores=0;
		for(Reserva r:res.getReservas()) {
			if(HI.isAfter(r.getHoraInicio()) && HI.isBefore(r.getHoraFin()) || HF.isAfter(r.getHoraInicio()) && HF.isBefore(r.getHoraFin())) {
				aforores += r.getnPersonas();
			}
		}
		
		if(aforores+nPersonas>res.getAforomax()) {
			errors.rejectValue("nPersonas", REQUIRED+"  no hay suficiente aforo disponible", REQUIRED+"  no hay suficiente aforo disponible");
		}
		
		// evento validation
		if(evento==null) {
			errors.rejectValue("evento", REQUIRED+"es necesario indicar si es evento", REQUIRED+"es necesario indicar si es evento");
		}
		if((evento && nPersonas<=10)) {
			errors.rejectValue("nPersonas", REQUIRED+"   Si es un evento, debe haber 10 personas o más", REQUIRED+"   Si es un evento, debe haber 10 personas o más");
		}
		
		// hora validation	
		if(HI==null || HI.isBefore(LocalTime.now()) && fecha.isEqual(LocalDate.now())) {
			errors.rejectValue("horaInicio", REQUIRED+"  Introduzca una hora válida",REQUIRED+"  Introduzca una hora válida");
		}
		
		if(HF==null || HF.isBefore(LocalTime.now()) && fecha.isEqual(LocalDate.now())) {
			errors.rejectValue("horaFin", REQUIRED+"  Introduzca una hora válida",REQUIRED+"  Introduzca una hora válida");
		}
		
		if(HF.isBefore(HI)) {
			errors.rejectValue("horaFin", REQUIRED+"  La hora de inicio debe ser anterior a la hora de fin",REQUIRED+"  La hora de inicio debe ser anterior a la hora de fin");
		}

			
	}
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Reserva.class.isAssignableFrom(clazz);
	}

}
