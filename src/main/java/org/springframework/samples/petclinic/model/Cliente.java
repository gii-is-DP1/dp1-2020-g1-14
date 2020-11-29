package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.samples.petclinic.service.ClienteService.TelephoneNumberConstraint;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
@Entity
public class Cliente extends Usuario {
	
    @NotNull
    private Boolean esSocio;
    
    @NotEmpty(message="El número de teléfono es obligatorio.")
    //@Pattern(regexp="^([0-9]|7[1-9])[0-9]{8}$", message="El número de teléfono no es válido. Debe introducir un número de teléfono válido p.ej: '954678970' o en caso de teléfono móvil: '657908756'.")
    @TelephoneNumberConstraint
    private String tlf;
    
    private int numPedidos;
}