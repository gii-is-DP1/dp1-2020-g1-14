package org.springframework.samples.petclinic.model;

import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="pedido")
public class Pedido extends BaseEntity {
	
	//@NotNull(message="El campo no puede estar vacío")
	
	private Double price;
	
	@NotNull(message="El campo no puede estar vacío")
	@DateTimeFormat (pattern = "yyyy/MM/dd")
	private LocalDate orderDate;
	
	@NotNull(message="El campo no puede estar vacío")
	private String adress;
	
	@Enumerated(EnumType.STRING)
	private Estado estado;
	
	
	@OneToMany(mappedBy="pedido", cascade= CascadeType.ALL, fetch= FetchType.EAGER)
	private List<LineaPedido> lineaPedido;
	
	@ManyToOne
	@JoinColumn(name="cliente_id")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="oferta_id")
	private Oferta oferta;
	
	private Boolean checkea;

	public Pedido() {
		super();
		this.orderDate = LocalDate.now();
		this.estado =  estado.SIN_VERIFICAR;
		this.checkea = false;
	}
	
}