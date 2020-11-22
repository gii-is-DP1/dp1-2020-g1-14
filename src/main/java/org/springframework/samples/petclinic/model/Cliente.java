package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Entity
public class Cliente extends Usuario {
    @NotNull
    @NotEmpty
    private Boolean esSocio;
    private String tlf;
    @NotNull
    @NotEmpty
    private Integer numPedidos;
}