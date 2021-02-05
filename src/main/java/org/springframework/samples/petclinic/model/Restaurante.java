package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
//@AforoResConstraint(afRes = "aforores", afMax = "aforomax")
public class Restaurante extends NamedEntity {
	
	@Version
	private Integer version;

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@NotBlank	
	private String tipo;
    @NotBlank
    private String localizacion;
    @Positive
    private int aforomax;
    @NotNull
    private int senial;
   
    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL)
    private Set<Reserva> reservas;
     
	@OneToOne(cascade = CascadeType.ALL, optional=true)
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
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}
	
	public void setAforomax(int aforomax) {
		this.aforomax = aforomax;
	}
	
	public int getSenial() {
		return senial;
	}

	public void setSenial(int senial) {
		this.senial = senial;
	}
	
	public Restaurante() {
		super();
	}
	
	public Set<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(Set<Reserva> reservas) {
		this.reservas = reservas;
	}
	
	public void addReserva(Reserva reserva) {
		this.reservas.add(reserva);
	}

	public Gerente getGerente() {
		return gerente;
	}

	public void setGerente(Gerente gerente) {
		this.gerente = gerente;
	}
}