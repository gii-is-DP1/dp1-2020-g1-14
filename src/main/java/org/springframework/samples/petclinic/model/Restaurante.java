package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.samples.petclinic.service.RestauranteService.AforoResConstraint;

@Entity
@AforoResConstraint(afRes = "aforores", afMax = "aforomax")
public class Restaurante extends NamedEntity {

	@NotBlank	
	private String tipo;
    @NotBlank
    private String localizacion;
    @Positive
    private int aforomax;
    /*@PositiveOrZero
    private int aforores;*/
    /*@ManyToOne
    @JoinColumn(name = "propietario_id")
    private Propietario propietario;*/
    
    @OneToMany(mappedBy = "restaurante", fetch = FetchType.EAGER)
    private Set<Reserva> reservas;
    
    @OneToMany(mappedBy = "restaurante", fetch = FetchType.EAGER)
    private Set<Producto> productos;

	@OneToOne(cascade = CascadeType.ALL)
    private Gerente gerente;
	
	
	
    public String getTipo() {
		return tipo;
	}
    
	public String getLocalizacion() {
		return localizacion;
	}
	
	public int getAforomax() {
		return aforomax;
	}
	
	/*public int getAforores() {
		return aforores;
	}*/
	
	/*public Propietario getPropietario() {
		return propietario;
	}*/
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}
	
	public void setAforomax(int aforomax) {
		this.aforomax = aforomax;
	}
	
	/*public void setAforores(int aforores) {
		this.aforores = aforores;
		//aqui se podria hacer una resta entre el maximo y el numero de clientes con reserva
	}*/
	
	/*public void setPropietario(Propietario propietario) {
		this.propietario = propietario;
	}*/
	
	public Restaurante() {
		super();
	}
	
	/*public Restaurante(Propietario propietario) {
		super();
		this.propietario = propietario;
	}*/
	
	public Set<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(Set<Reserva> reservas) {
		this.reservas = reservas;
	}
	
	public void addReserva(Reserva reserva) {
		this.reservas.add(reserva);
	}
	
	public Set<Producto> getProductos() {
		return productos;
	}

	public void setProductos(Set<Producto> productos) {
		this.productos = productos;
	}

	public Gerente getGerente() {
		return gerente;
	}

	public void setGerente(Gerente gerente) {
		this.gerente = gerente;
	}
	
}