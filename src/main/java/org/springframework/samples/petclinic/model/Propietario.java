package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
@Entity
public class Propietario extends Usuario{
	
	@NotBlank(message="El número DNI es obligatorio.")
	@Pattern(regexp="^[0-9]{8}[a-Z]$", message="Debe introducir DNI válido p.ej: '95467897E'.")
	private String dni;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "propietario")
	private Set<Restaurante> restaurantes;

}
