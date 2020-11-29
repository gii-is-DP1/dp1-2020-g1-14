package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
public class Gerente extends Usuario{

//	@NotBlank(message="El número DNI es obligatorio.")
//	@Pattern(regexp="^[0-9]{8}[a-Z]$", message="Debe introducir DNI válido p.ej: '95467897E'.")
	private String dni;
	
	public Gerente() {
	super();
	this.rDate = LocalDate.now();
	}

	@OneToOne
	@JoinColumn(name = "restaurante_id")
	private Restaurante restaurante;
	
	
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}
	
}
