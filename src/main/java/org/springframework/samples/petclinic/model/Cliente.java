package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Entity
public class Cliente extends Usuario {
    @NotNull
    @NotEmpty
    private Boolean esSocio;
    
    @NotNull
    @NotEmpty(message="El número de teléfono es obligatorio.")
    @Pattern(regexp="^([0-9]|7[1-9])[0-9]{8}$", message="Debe introducir un número de teléfono válido p.ej: '954678970', en caso de teléfono móvil: '657908756'.")
    private String tlf;
    
    @NotNull
    @NotEmpty
    private Integer numPedidos;
}