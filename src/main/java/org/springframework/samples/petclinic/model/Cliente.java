package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Cliente extends BaseEntity {
	
	@Version
	private Integer version;

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
    @NotNull
    private Boolean esSocio;
    
    @NotEmpty(message="El número de teléfono es obligatorio.")
    //@Pattern(regexp="^([0-9]|7[1-9])[0-9]{8}$", message="El número de teléfono no es válido. Debe introducir un número de teléfono válido p.ej: '954678970' o en caso de teléfono móvil: '657908756'.")
    //@TelephoneNumberConstraint
    private String tlf;
    
    private int numPedidos;
    
    @Min(value = 0,message="no se puede tener dinero negativo")
    private int monedero;
    
    @OneToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;
    
    public void addMonedero(int cantidad) {
    	this.monedero= this.monedero + cantidad;
    }
    
    public void subMonedero(int cantidad) {
    	this.monedero= this.monedero - cantidad;
    }

}