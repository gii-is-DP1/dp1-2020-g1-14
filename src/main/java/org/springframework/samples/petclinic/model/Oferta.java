package org.springframework.samples.petclinic.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Oferta extends BaseEntity {
	
	@Version
	private Integer version;

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	@NotNull(message="El campo no puede estar vacío")
	private String descripcion;
	
	@NotNull(message="Agrega un descuento")
	@Min(value=1)
	private Double descuento;
	
	@NotNull(message="Agrega un precio mínimo")
	@Min(value=1)
	private Double minPrice;
	
	@NotNull(message="Marca alguna de las siguientes opciones")
	private Boolean exclusivo;
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@OneToMany(mappedBy="oferta")
	private List<Pedido> pedidos;
	
	@ManyToOne
	private Restaurante restaurante;
}
