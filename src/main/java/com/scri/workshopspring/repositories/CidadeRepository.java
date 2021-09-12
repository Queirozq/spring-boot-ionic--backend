package com.scri.workshopspring.repositories;

import com.scri.workshopspring.domain.Cidade;
import com.scri.workshopspring.domain.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

    @Transactional(readOnly = true)
    List<Cidade> findAllByOrderByName();
}
