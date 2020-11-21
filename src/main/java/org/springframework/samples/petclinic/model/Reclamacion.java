package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
public class Reclamacion extends NamedEntity{

	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate fecha;
	
	private String descripcion;
	
}
