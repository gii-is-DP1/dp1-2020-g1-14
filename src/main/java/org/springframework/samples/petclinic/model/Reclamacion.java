package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;


import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Reclamacion extends BaseEntity{

	@NotBlank(message="El campo no puede ser nulo o estar vacío")
	private String descripcion;
	
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate fecha;
	
	@ManyToOne
	//@JoinColumn(name = "restaurante_id")
	private Restaurante restaurante;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

	public Reclamacion() {
		super();
		this.fecha = LocalDate.now();
	}
	
	
}
