package org.springframework.samples.petclinic.model;


import javax.persistence.Entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;



@Entity
public class Propietario extends Usuario{
	
	@NotBlank(message="El número DNI es obligatorio.")
	@Pattern(regexp="^[0-9]{8}[a-Z]$", message="Debe introducir DNI válido p.ej: '95467897E'.")
	private String dni;
	
	/*@OneToMany(cascade = CascadeType.ALL, mappedBy = "propietario")
	private Set<Restaurante> restaurantes;*/

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	/*public void setRestaurantes(Set<Restaurante> restaurantes) {
		this.restaurantes = restaurantes;
	}
	
	protected Set<Restaurante> getRestaurantesInternal() {
		if (this.restaurantes == null) {
			this.restaurantes = new HashSet<>();
		}
		return this.restaurantes;
	}

	protected void setRestaurantesInternal(Set<Restaurante> restaurantes) {
		this.restaurantes = restaurantes;
	}
	
	public List<Restaurante> getRestaurantes() {
		List<Restaurante> sortedRestaurantes = new ArrayList<>(getRestaurantesInternal());
		PropertyComparator.sort(sortedRestaurantes, new MutableSortDefinition("name", true, true));
		return Collections.unmodifiableList(sortedRestaurantes);
	}
	
	public void addRestaruante(Restaurante restaurante) {
		getRestaurantesInternal().add(restaurante);
		restaurante.setPropietario(this);
	}
	
	public boolean removeRestaurantes(Restaurante restaurante) {
		return getRestaurantesInternal().remove(restaurante);
	}
	
	public Restaurante getRestaurante(String name) {
		return getRestaurante(name, false);
	}

	private Restaurante getRestaurante(String name, boolean ignoreNew) {
		name = name.toLowerCase();
		for(Restaurante restaurante : getRestaurantesInternal()) {
			if(!ignoreNew || !restaurante.isNew()) {
				String resName = restaurante.getName();
				resName = resName.toLowerCase();
				if(resName.equals(name)) {
					return restaurante;
				}
			}
		}
		return null;
	}*/
	
	

}
