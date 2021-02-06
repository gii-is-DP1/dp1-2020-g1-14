package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name="lineaPedido")
public class LineaPedido extends BaseEntity {
	
	@NotNull(message="Introduce una cantidad mayor o igual a 1")
	@Min(value=1,message="La cantidad debe de ser mayor o igual a 1")
	private Integer cantidad;
	
	@ManyToOne(optional=true)
	private Pedido pedido;
	
	@ManyToOne
	@NotNull(message="Selecciona un producto")
	private Producto producto;	
}
