package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
//@ReservaConstraint(Event = "evento", nPersons = "nPersonas")
public class Reserva extends BaseEntity{
	
	/*@NotNull*/
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate fecha;
	/*@NotNull*/
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime horaInicio;
	/*@NotNull*/
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime horaFin;
	//@NotNull
	private boolean evento;
	/*@NotNull
	@DecimalMin(value="1", inclusive=true)*/
	private Integer nPersonas;
	
	@ManyToOne
	private Restaurante restaurante;
	
	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

	public Reserva() {
		super();
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	public LocalTime getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(LocalTime horaFin) {
		this.horaFin = horaFin;
	}

	public Boolean getEvento() {
		return evento;
	}

	public void setEvento(Boolean evento) {
		this.evento = evento;
	}

	public Integer getnPersonas() {
		return nPersonas;
	}

	public void setnPersonas(Integer nPersonas) {
		this.nPersonas = nPersonas;
	}
	
	
	
	
	
}
