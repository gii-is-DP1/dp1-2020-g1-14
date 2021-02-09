package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ClienteValidator implements Validator{
	@Autowired
	private UserService userService;

	@Override
	public void validate(Object target, Errors errors) {
		Cliente cliente = (Cliente) target;
		User user = cliente.getUser();
		String username = user.getUsername();
		Boolean esSocio = cliente.getEsSocio();
		String password = user.getPassword();
		String tlf = cliente.getTlf();
		Double monedero = cliente.getMonedero();

		//password validator
		if(!password.matches("^.*(?=.{6,})(?=.*\\d)(?=.*[a-zA-Z]).*$")) {
			errors.rejectValue("user.password", "La contraseña debe tener 6 carácteres y contener al menos un número","La contraseña debe tener 6 carácteres y contener al menos un número");
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
		//esSocio validator
		if(esSocio == null) {
			errors.rejectValue("esSocio", "Este campo es obligatorio", "Este campo es obligatorio");
		}
		//monedero validator
		if(monedero < 0) {
			errors.rejectValue("monedero", "No se admiten valores negativos", "No se admiten valores negativos");
		}
		//tlf validator
		if(!tlf.matches("^[0-9]{9}")) {
			errors.rejectValue("tlf", "Introduzca un numero de telefono valido", "Introduzca un numero de telefono valido");
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Cliente.class.isAssignableFrom(clazz);
	}
}
