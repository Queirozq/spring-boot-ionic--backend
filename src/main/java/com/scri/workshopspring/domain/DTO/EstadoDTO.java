package com.scri.workshopspring.domain.DTO;

import com.scri.workshopspring.domain.Estado;

import java.io.Serializable;

public class EstadoDTO implements Serializable {

    private Integer id;
    private String name;

    public EstadoDTO() {
    }

    public EstadoDTO(Estado estado) {
        this.id = estado.getId();
        this.name = estado.getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
