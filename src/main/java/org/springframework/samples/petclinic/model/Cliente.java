package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Cliente extends BaseEntity {
	

	
    private Boolean esSocio;
    
    private String tlf;
    
    private int numPedidos;
    
    private Double monedero;
    
    @OneToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;
    
    public void addMonedero(int cantidad) {
    	this.monedero= this.monedero + cantidad;
    }
    
    public void subMonedero(int cantidad) {
    	this.monedero= this.monedero - cantidad;
    }

	public Cliente() {
		super();
		this.esSocio = false;
		this.numPedidos = 0;
		this.monedero = 0.;
	}
    
    

}