package com.scri.workshopspring.resources;

import com.scri.workshopspring.domain.Produto;
import com.scri.workshopspring.domain.DTO.ProdutoDTO;
import com.scri.workshopspring.domain.Produto;
import com.scri.workshopspring.resources.utils.URL;
import com.scri.workshopspring.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Produto> findById(@PathVariable Integer id){
         Produto Produto = service.findById(id);
         return ResponseEntity.ok().body(Produto);
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> findPage(@RequestParam(value = "name", defaultValue = "") String name, @RequestParam(value = "categorias", defaultValue = "") String categorias ,@RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, @RequestParam(value = "orderBy", defaultValue = "name") String orderBy, @RequestParam(value = "direction", defaultValue = "ASC") String direction){
        String nameDecoded = URL.decodeParam(name);
        List<Integer> ids = URL.decodeIntList(categorias);
        Page<Produto> list = service.search(nameDecoded, ids, page, linesPerPage, orderBy, direction);
        Page<ProdutoDTO> listDTO = list.map(x -> new ProdutoDTO(x));
        return ResponseEntity.ok().body(listDTO);
    }
}
