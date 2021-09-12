package com.scri.workshopspring.domain.enums;

public enum Perfil {

    ADM(1, "ROLE_ADMIN"),
    CLIENTE(2, "ROLE_CLIENTE");

    private int cod;
    private String descricao;

    Perfil(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public static Perfil toEnum(Integer id){
        for(Perfil x : Perfil.values()){
            if(x.getCod() == id){
                return x;
            }
        }
        throw new IllegalArgumentException("Id inválido" + id);
    }
}
