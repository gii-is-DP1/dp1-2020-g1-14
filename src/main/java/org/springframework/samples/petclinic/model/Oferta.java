package org.springframework.samples.petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Oferta extends BaseEntity {
	@NotNull
	private String descripcion;
	/*
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="productoId")
	private Producto producto;
	*/
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
