package org.springframework.samples.petclinic.model;


import javax.persistence.Entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;



@Entity
public class Propietario extends Usuario{
	
	@NotBlank(message="El número DNI es obligatorio.")
	@Pattern(regexp="^[0-9]{8}[a-Z]{1}$", message="Debe introducir DNI válido p.ej: '95467897E'.")
	private String dni;


	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}


	

}
