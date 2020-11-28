package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.samples.petclinic.service.ClienteService.TelephoneNumberConstraint;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
@Entity
public class Cliente extends Usuario {
	
    @NotNull
    private Boolean esSocio;
    
    @TelephoneNumberConstraint
    private String tlf;
    
    private int numPedidos;
}