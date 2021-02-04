package org.springframework.samples.petclinic.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Oferta extends BaseEntity {
	@NotNull
	private String descripcion;
	/*
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="productoId")
	private Producto producto;
	*/
	
	private Double descuento;
	
	private Double minPrice;
	
	private Boolean exclusivo;
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@OneToMany(mappedBy="oferta", cascade = CascadeType.ALL, fetch= FetchType.EAGER)
	private List<Pedido> pedidos;
	
	@ManyToOne
	private Restaurante restaurante;
}
