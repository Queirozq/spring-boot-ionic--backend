package com.scri.workshopspring.service.exceptions;

public class ResourceNotFoundException extends RuntimeException{


    public ResourceNotFoundException(Integer id) {
       super("Id de número " + id + " não encontrado.");
    }

    public ResourceNotFoundException(String msg){
        super(msg);
    }
}
