package org.springframework.samples.petclinic.model;

import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class Gerente {

	@NotBlank(message="El número DNI es obligatorio.")
	@Pattern(regexp="^[0-9]{8}[a-Z]$", message="Debe introducir DNI válido p.ej: '95467897E'.")
	private String dni;
	
	@OneToOne
	private Restaurante restaurante;
	
	
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

}
