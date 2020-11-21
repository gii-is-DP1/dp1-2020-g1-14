package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Data;

@Data
@Entity
public class Restaurante extends NamedEntity {
    @NotNull
	private String tipo;
    @NotNull
	private String localizacion;
    @Positive
    private Integer aforomax;
    @Positive
    private Integer aforores;

}