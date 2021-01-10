package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RestauranteValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Restaurante.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Restaurante restaurante = (Restaurante) target;
		Integer max = restaurante.getAforomax();
		//Integer res = restaurante.getAforores();
		String tipo = restaurante.getTipo();
		String name = restaurante.getName();
		String loc = restaurante.getLocalizacion();
		
		/*if(res > max) {
			errors.rejectValue("aforoRes", "El aforo restante debe ser menor que el maximo");
		}*/
		/*if(tipo.contains("1") || tipo.contains("2") ||tipo.contains("3") ||tipo.contains("4") ||tipo.contains("5") ||tipo.contains("6") ||tipo.contains("7") ||tipo.contains("8") ||tipo.contains("9") ||tipo.contains("0")) {
			errors.rejectValue("tipo", "El tipo no puede contener numeros");
		}
		if(tipo.trim().isEmpty()) {
			errors.rejectValue("tipo", "No puede estar vacio");
		}
		if(res < 0) {
			errors.rejectValue("aforoRes", "El aforo restante debe ser positivo");
		}
		if(max <= 0) {
			errors.rejectValue("aforoMax", "El aforo maximo debe ser mayor que 0");
		}
		if(name.trim().isEmpty()) {
			errors.rejectValue("name", "Debe introducir un nombre");
		}
		if(!loc.startsWith("/C")) {
			errors.rejectValue("localizacion", "La localizacion debe empezar por /C");
		}*/
		
		
		
	}

}
