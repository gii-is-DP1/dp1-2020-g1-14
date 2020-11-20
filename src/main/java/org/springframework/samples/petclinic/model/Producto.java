package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
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

}
