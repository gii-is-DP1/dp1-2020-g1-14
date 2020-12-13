package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


import lombok.Data;

@Data
@Entity


public class Proveedor extends NamedEntity{
   
	@NotBlank
	@NotEmpty(message="El número de teléfono es obligatorio.")
    @Pattern(regexp="^([0-9]|7[1-9])[0-9]{8}$", message="El número de teléfono no es válido. Debe introducir un número de teléfono válido p.ej: '954678970' o en caso de teléfono móvil: '657908756'.")
	private String tlf;
    
}
