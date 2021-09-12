package com.scri.workshopspring.resources;


import com.scri.workshopspring.domain.Cidade;
import com.scri.workshopspring.domain.DTO.CidadeDTO;
import com.scri.workshopspring.domain.DTO.EstadoDTO;
import com.scri.workshopspring.domain.Estado;
import com.scri.workshopspring.service.CidadeService;
import com.scri.workshopspring.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {

    @Autowired
    private EstadoService service;

    @Autowired
    private CidadeService cidadeService;

    @GetMapping
    public ResponseEntity<List<EstadoDTO>> findAllByOrderName(){
        List<Estado> list = service.findAllByOrderByName();
        List<EstadoDTO> listDTO = list.stream().map(x-> new EstadoDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @GetMapping(value = "/{id}/cidades")
    public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer id){
           List<Cidade> list = cidadeService.findAll(id);
           List<CidadeDTO> listDTO = list.stream().map(x-> new CidadeDTO(x)).collect(Collectors.toList());
           return ResponseEntity.ok().body(listDTO);
    }


}
