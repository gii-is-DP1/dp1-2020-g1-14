package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class LineaPedido extends BaseEntity {
	
	private Integer cantidad;
}
