package com.scri.workshopspring.service;

import com.scri.workshopspring.domain.Cidade;
import com.scri.workshopspring.domain.DTO.CidadeDTO;
import com.scri.workshopspring.domain.Estado;
import com.scri.workshopspring.repositories.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository repository;


    public List<Cidade> findAll(Integer id){
        List<Cidade> list = repository.findAllByOrderByName();
        return list.stream().filter(x -> x.getEstado().getId().equals(id)).collect(Collectors.toList());
    }
}
