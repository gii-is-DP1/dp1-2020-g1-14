package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
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
	
	@Version
	private Integer version;

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	@NotNull
    @Min(value = 1,message="El precio debe de ser mayor que 0")
	private Double precio;
	
	@Pattern(regexp="^[a-zA-Z,.!? ]*$", message="Solo se permiten espacios, símbolos y letras")
	private String alergenos;
	
	@ManyToMany
	private Set<Ingrediente> ingredientes;
	
	@ManyToOne
    private Restaurante restaurante;
	
}
