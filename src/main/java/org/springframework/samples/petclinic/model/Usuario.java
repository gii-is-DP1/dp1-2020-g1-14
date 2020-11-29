package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import com.sun.istack.NotNull;


@MappedSuperclass
public class Usuario extends NamedEntity {
	
    @DateTimeFormat (pattern = "yyyy/MM/dd")
	protected LocalDate rDate;
    
    @NotEmpty(message="El campo de la contraseña es obligatorio.")
    @Pattern(regexp="^.*(?=.{6,})(?=.*\\d)(?=.*[a-zA-Z]).*$", message="La contraseña debe tener 6 carácteres y contener al menos un número. ")
    private String password;

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

}