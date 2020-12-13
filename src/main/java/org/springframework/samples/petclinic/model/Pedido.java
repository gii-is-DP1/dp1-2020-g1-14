package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
public class Pedido extends BaseEntity {
	
	@NotNull(message="El campo no puede estar vacío")
	@Min(value=10, message="El precio debe de ser mayor a 10")
	private Double price;
	
	@NotNull(message="El campo no puede estar vacío")
	@DateTimeFormat (pattern = "yyyy/MM/dd")
	private LocalDate orderDate;
	
	@NotNull(message="El campo no puede estar vacío")
	private String adress;
	
	@Enumerated(EnumType.STRING)
	private Estado estado;

	@ManyToOne
	@JoinColumn(name="cliente_id")
	private Cliente cliente;
	
	/*@OneToMany
	@JoinColumn(name="producto_id")
	private Collection<Producto> producto;
	
	@OneToMany
	@JoinColumn(name="linea_Pedido_id")
	private Collection<LineaPedido> lineaPedido; */
	
}