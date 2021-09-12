package com.scri.workshopspring.service;

import com.scri.workshopspring.domain.Estado;
import com.scri.workshopspring.repositories.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EstadoService {


    @Autowired
    private EstadoRepository repository;

    public List<Estado> findAllByOrderByName(){
        return repository.findAllByOrderByName();
    }
}
