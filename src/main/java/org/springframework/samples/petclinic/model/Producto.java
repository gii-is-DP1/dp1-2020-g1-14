package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Entity
public class Producto extends NamedEntity {
	
	@NotNull
    @Min(value = 1,message="El precio debe de ser mayor que 0")
	private Double precio;
	@Pattern(regexp="^[a-zA-Z,.!? ]*$", message="Solo se permiten espacios, símbolos y letras")
	private String alergenos;
	
	@ManyToMany
	private Set<Ingrediente> ingredientes;

}
