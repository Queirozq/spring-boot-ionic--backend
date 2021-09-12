package com.scri.workshopspring.resources;


import com.scri.workshopspring.domain.Categoria;
import com.scri.workshopspring.domain.DTO.CategoriaDTO;
import com.scri.workshopspring.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

        @Autowired
        private CategoriaService service;


        @GetMapping
        public ResponseEntity<List<CategoriaDTO>> findAll(){
            List<Categoria> list = service.findAll();
            List<CategoriaDTO> listDTO = list.stream().map(CategoriaDTO::new).collect(Collectors.toList());
            return ResponseEntity.ok().body(listDTO);
        }

        @GetMapping(value = "/{id}")
        public ResponseEntity<Categoria> findById(@PathVariable Integer id){
            Categoria cat = service.findById(id);
            return ResponseEntity.ok().body(cat);
        }

        @PreAuthorize("hasAnyRole('ADMIN')")
        @PostMapping
        public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDTO){
            Categoria obj =  service.fromDTO(objDTO);
            obj = service.insert(obj);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
            return ResponseEntity.created(uri).build();
        }

        @PreAuthorize("hasAnyRole('ADMIN')")
        @PutMapping(value = "/{id}")
        public ResponseEntity<Categoria> update(@PathVariable Integer id, @Valid @RequestBody CategoriaDTO objDTO){
            Categoria obj = service.fromDTO(objDTO);
            service.update(id, obj);
            return ResponseEntity.noContent().build();
        }

        @PreAuthorize("hasAnyRole('ADMIN')")
        @DeleteMapping(value = "/{id}")
        public ResponseEntity<Void> delete(@PathVariable Integer id){
            service.delete(id);
            return ResponseEntity.noContent().build();
        }

        @GetMapping(value = "/page")
        public ResponseEntity<Page<CategoriaDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,@RequestParam(value = "direction", defaultValue = "ASC") String direction){
           Page<Categoria> list = service.findPage(page, linesPerPage, orderBy, direction);
           Page<CategoriaDTO> listDTO = list.map(x -> new CategoriaDTO(x));
            return ResponseEntity.ok().body(listDTO);
        }
}
