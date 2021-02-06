package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="pedido")
public class Pedido extends BaseEntity {
	
	@Version
	private Integer version;

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	private Double price;
	
	@NotNull(message="El campo no puede estar vacío")
	@DateTimeFormat (pattern = "yyyy/MM/dd")
	private LocalDate orderDate;
	
	@NotNull(message="El campo no puede estar vacío")
	@NotBlank
	private String adress;
	
	@Enumerated(EnumType.STRING)
	private Estado estado;
	
	@ManyToOne
	@JoinColumn(name="cliente_id")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="oferta_id")
	private Oferta oferta;
	
	@ManyToOne
	private Restaurante restaurante;
	
	private Boolean checkea;

	public Pedido() {
		super();
		this.orderDate = LocalDate.now();
		this.estado =  estado.SIN_VERIFICAR;
		this.checkea = false;
	}
	
}