package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Gerente;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class GerenteValidator implements Validator{
	
	@Autowired
	private UserService userService;
	
	@Override
	public void validate(Object target, Errors errors) {
		Gerente gerente = (Gerente) target;
		User user = gerente.getUser();
		String username = user.getUsername();
		String name = gerente.getName();
		String password = user.getPassword();
		String dni = gerente.getDni();
		
		//password validator
		if(!password.matches("^.*(?=.{6,})(?=.*\\d)(?=.*[a-zA-Z]).*$")) {
			errors.rejectValue("user.password", "required  La contraseña debe tener 6 carácteres y contener al menos un número","required  La contraseña debe tener 6 carácteres y contener al menos un número");
		}
		//dni validator
		if(!dni.matches("^[0-9]{8}[a-zA-Z]?$")) {
			errors.rejectValue("dni", "required  Debe introducir DNI válido p.ej: '95467897E'","required  Debe introducir DNI válido p.ej: '95467897E'");
		}
		//username validator
		if(username.isEmpty()) {
			errors.rejectValue("user.username", "Por favor introduzca un nombre de usuario", "Debe de introducir un nombre de usuario");
		}else if(username.length()<5 || username.length()>15) {
			errors.rejectValue("user.username", "El nombre de usuario debe de tener una longitud comprendida entre 5 y 15 caracteres", "El nombre de usuario debe de tener una longitud comprendida entre 5 y 15 caracteres");
		}else {
			Iterable<User> users = userService.findAll();
			for(User usuario:users) {
				if(usuario.getUsername().equals(username)) {
					errors.rejectValue("user.username", "Este nombre de usuario ya existe. Por favor elija uno diferente", "Este nombre de usuario ya existe. Por favor elija uno diferente");
					break;
				}
			}
		}
		//name validator
		if(name.isEmpty()) {
			errors.rejectValue("name", "Por favor introduzca su nombre", "Por favor introduzca su nombre");
		}else if(name.length()<3 || name.length()>15) {
			errors.rejectValue("name", "Debe de introducir un nombre de 3 caracteres como minimo y de 15 como maximo", "Debe de introducir un nombre de 3 caracteres como minimo y de 15 como maximo");
		}else if(!name.matches("^[a-zA-Z]+$")) {
			errors.rejectValue("name", "Solo se permiten letras en este campo", "Solo se permiten letras en este campo");
		}
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Gerente.class.isAssignableFrom(clazz);
	}
}
