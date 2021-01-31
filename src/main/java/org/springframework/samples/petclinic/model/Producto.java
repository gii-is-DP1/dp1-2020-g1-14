package org.springframework.samples.petclinic.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="producto")
public class Producto extends NamedEntity {
	
	@NotNull
    @Min(value = 1,message="El precio debe de ser mayor que 0")
	private Double precio;
	@Pattern(regexp="^[a-zA-Z,.!? ]*$", message="Solo se permiten espacios, símbolos y letras")
	private String alergenos;
	
	@ManyToMany
	private Set<Ingrediente> ingredientes;
	/*
	@OneToOne
	@JoinColumn(name="linea_pedido_id")
	private LineaPedido lineaPedido;
	*/
	@OneToMany(mappedBy="producto",cascade = CascadeType.ALL, fetch= FetchType.EAGER)
	private List<LineaPedido> lineaPedido;
	/*
	@OneToOne(mappedBy="producto")
	private Oferta oferta;
	*/
}
