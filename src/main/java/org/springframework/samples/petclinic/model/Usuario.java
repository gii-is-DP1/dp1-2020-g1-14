package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import javax.persistence.MappedSuperclass;

import org.springframework.format.annotation.DateTimeFormat;


@MappedSuperclass
public class Usuario extends NamedEntity {
    @DateTimeFormat (pattern = "yyyy/MM/dd")
    private LocalDate rDate;
    
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