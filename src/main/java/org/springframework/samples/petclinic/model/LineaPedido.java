package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name="lineaPedido")
public class LineaPedido extends BaseEntity {
	
	@Version
	private Integer version;

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	@NotNull(message="Introduce una cantidad mayor o igual a 1")
	@Min(value=1,message="La cantidad debe de ser mayor o igual a 1")
	private Integer cantidad;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="pedido_id")
	private Pedido pedido;
	
	@ManyToOne
	@NotNull(message="Selecciona un producto")
	private Producto producto;	
}
