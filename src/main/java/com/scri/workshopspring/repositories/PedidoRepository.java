package com.scri.workshopspring.repositories;

import com.scri.workshopspring.domain.Cliente;
import com.scri.workshopspring.domain.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    @Transactional(readOnly = true)
    Page<Pedido>findByCliente(Cliente cliente, Pageable pageRequest);
}
