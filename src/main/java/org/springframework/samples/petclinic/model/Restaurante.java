package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class Restaurante extends NamedEntity {
    
	private String tipo;
   
	private String localizacion;
   
    private Integer aforomax;
    
    private Integer aforores;

}