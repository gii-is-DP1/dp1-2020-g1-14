package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Entity


public class Proveedor extends NamedEntity{
    @NotNull
    @NotEmpty
    @Pattern(regexp="^([0-9]|7[1-9])[0-9]{8}$")
	private String tlf;
    
}
