package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Entity
public class Producto extends NamedEntity {
	
	@NotNull
    @Min(value = 1)
	private Double precio;
	
	private String alergenos;
	
	@ManyToMany
	private Set<Ingrediente> ingredientes;

}
