package org.springframework.samples.petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name="lineaPedido")
public class LineaPedido extends BaseEntity {
	
	private Integer cantidad;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="pedido_id")
	private Pedido pedido;
	/*
	@OneToOne(mappedBy="lineaPedido", cascade= CascadeType.ALL)
	private Producto producto;
	*/
	
	@ManyToOne
	@JoinColumn(name="producto_id")
	private Producto producto;
	
	@ManyToOne
	@JoinColumn(name="restaurante_id")
	private Restaurante restaurante;
	
}
