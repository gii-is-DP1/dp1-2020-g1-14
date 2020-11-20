package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Entity
public class Cliente extends Usuario {
    @NotNull
    private Boolean esSocio;
    @NotNull
    private String tlf;
    @NotNull
    private Integer numPedidos;
}