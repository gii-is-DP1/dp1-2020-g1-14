package org.springframework.samples.petclinic.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Cliente extends Usuario {
	
    @NotNull
    private Boolean esSocio;
    
    @NotEmpty(message="El número de teléfono es obligatorio.")
    //@Pattern(regexp="^([0-9]|7[1-9])[0-9]{8}$", message="El número de teléfono no es válido. Debe introducir un número de teléfono válido p.ej: '954678970' o en caso de teléfono móvil: '657908756'.")
    //@TelephoneNumberConstraint
    private String tlf;
    
    private int numPedidos;
    
    
    @OneToMany(mappedBy="cliente")
    private List<Pedido> pedido;
    
}