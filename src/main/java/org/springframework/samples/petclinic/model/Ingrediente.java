package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Data;

@Data
@Entity
public class Ingrediente extends NamedEntity {

	private Double stock;
	
	@Enumerated(EnumType.STRING)
	private Medida medida;
}
