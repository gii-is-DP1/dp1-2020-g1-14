package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Reserva extends BaseEntity{
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate fecha;
	@NotNull
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime horaInicio;
	@NotNull
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime horaFin;
	@NotNull
	private Boolean evento;
	@NotNull
	@DecimalMin(value="1", inclusive=true)
	private Integer nPersonas;
	
	public Reserva() {
		super();
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		fecha = fecha;
	}

	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(LocalTime horaInicio) {
		horaInicio = horaInicio;
	}

	public LocalTime getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(LocalTime horaFin) {
		horaFin = horaFin;
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
