package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.PositiveOrZero;

@Entity
public class Ingrediente extends NamedEntity {
	
	@Version
	private Integer version;

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@PositiveOrZero
	private double stock;
	
	@Enumerated(EnumType.STRING)
	private Medida medida;

	@ManyToOne
	private Restaurante restaurante;
	
	@ManyToMany()
	@JoinColumn(name = "producto_id")
	private Set<Producto> productos;
	
	@ManyToMany(mappedBy = "ingredientes")
	private Set<Proveedor> proveedores;
	
	public double getStock() {
		return stock;
	}

	public void setStock(double stock) {
		this.stock = stock;
	}

	public Medida getMedida() {
		return medida;
	}

	public void setMedida(Medida medida) {
		this.medida = medida;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

	public Set<Producto> getProductos() {
		return productos;
	}

	public void setProductos(Set<Producto> productos) {
		this.productos = productos;
	}

	public Set<Proveedor> getProveedores() {
		return proveedores;
	}

	public void setProveedores(Set<Proveedor> proveedores) {
		this.proveedores = proveedores;
	}
	
	
	
}
