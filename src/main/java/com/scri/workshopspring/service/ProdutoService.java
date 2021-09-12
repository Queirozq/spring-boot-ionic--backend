package com.scri.workshopspring.service;


import com.scri.workshopspring.domain.Categoria;
import com.scri.workshopspring.domain.Produto;
import com.scri.workshopspring.repositories.CategoriaRepository;
import com.scri.workshopspring.repositories.ProdutoRepository;
import com.scri.workshopspring.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto findById (Integer id){
        Optional<Produto> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = categoriaRepository.findAllById(ids);
        return repository.findDistinctByNameContainingAndCategoriasIn(nome, categorias, pageRequest);
    }
}
