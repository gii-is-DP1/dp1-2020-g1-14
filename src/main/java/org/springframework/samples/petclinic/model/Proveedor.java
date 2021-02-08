package org.springframework.samples.petclinic.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity
public class Proveedor extends NamedEntity{
	
	@Version
	private Integer version;

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
   
	@NotBlank
	@NotEmpty(message="El número de teléfono es obligatorio.")
    @Pattern(regexp="^([0-9]|7[1-9])[0-9]{8}$", message="El número de teléfono no es válido. Debe introducir un número de teléfono válido p.ej: '954678970' o en caso de teléfono móvil: '657908756'.")
	private String tlf;
    
	@ManyToMany
	private Set<Ingrediente> ingredientes;

	@ManyToMany(mappedBy = "proveedores")
	private Set<Restaurante> restaurantes;
	
	public String getTlf() {
		return tlf;
	}

	public void setTlf(String tlf) {
		this.tlf = tlf;
	}

	public Set<Ingrediente> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(Set<Ingrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public Set<Restaurante> getRestaurantes() {
		return restaurantes;
	}

	public void setRestaurantes(Set<Restaurante> restaurantes) {
		this.restaurantes = restaurantes;
	}

	public Proveedor() {
		super();
		this.ingredientes = new HashSet<>();
		this.restaurantes = new HashSet<>();
	}
	
	
}
