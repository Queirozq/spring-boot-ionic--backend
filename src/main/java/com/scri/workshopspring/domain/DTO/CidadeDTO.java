package com.scri.workshopspring.domain.DTO;

import com.scri.workshopspring.domain.Cidade;

import java.io.Serializable;

public class CidadeDTO implements Serializable {

    private Integer id;
    private String name;

    public CidadeDTO() {
    }

    public CidadeDTO(Cidade obj) {
        this.id = obj.getId();
        this.name = obj.getName();
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
