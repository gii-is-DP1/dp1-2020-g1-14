package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User{
	
	@Id
	String username;
	
	@NotEmpty(message="El campo de la contraseña es obligatorio.")
    @Pattern(regexp="^.*(?=.{6,})(?=.*\\d)(?=.*[a-zA-Z]).*$", message="La contraseña debe tener 6 carácteres y contener al menos un número. ")
	String password;
	
	boolean enabled;
	
	@DateTimeFormat (pattern = "yyyy/MM/dd")
	protected LocalDate rDate;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
	private Authorities authorities;
	
	public LocalDate getrDate() {
        return rDate;
    }

    public void setrDate(LocalDate rDate) {
        this.rDate = rDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public User() {
    	this.rDate = LocalDate.now();
    }
}
