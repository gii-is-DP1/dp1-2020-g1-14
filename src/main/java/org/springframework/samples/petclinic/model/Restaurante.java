package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SecondaryTable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Entity
public class Restaurante extends NamedEntity {
    @NotBlank
	private String tipo;
    @NotBlank
	private String localizacion;
    @Positive
    private int aforomax;
    @Positive
    private int aforores;
    @ManyToOne
    @JoinColumn(name = "propietario_id")
    private Propietario propietario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurante")
    private Set<Ingrediente> ingredientes;
	
    public String getTipo() {
		return tipo;
	}
    
	public String getLocalizacion() {
		return localizacion;
	}
	
	public int getAforomax() {
		return aforomax;
	}
	
	public int getAforores() {
		return aforores;
	}
	
	public Propietario getPropietario() {
		return propietario;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}
	
	public void setAforomax(int aforomax) {
		this.aforomax = aforomax;
	}
	
	public void setAforores(int aforores) {
		this.aforores = aforores;
		//aqui se podria hacer una resta entre el maximo y el numero de clientes con reserva
	}
	
	public void setPropietario(Propietario propietario) {
		this.propietario = propietario;
	}
	
	public Restaurante() {
		super();
	}
	
	public Restaurante(Propietario propietario) {
		super();
		this.propietario = propietario;
	}

	public Set<Ingrediente> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(Set<Ingrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}
    

}