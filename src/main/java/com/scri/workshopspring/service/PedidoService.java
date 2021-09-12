package com.scri.workshopspring.service;


import com.scri.workshopspring.domain.*;
import com.scri.workshopspring.domain.enums.EstadoPagamento;
import com.scri.workshopspring.repositories.*;
import com.scri.workshopspring.security.UserSpringSecurity;
import com.scri.workshopspring.service.exceptions.AuthorizationException;
import com.scri.workshopspring.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private HttpServletRequest request;


    public Pedido findById (Integer id){
        Optional<Pedido> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }


   public Pedido insert(Pedido obj){
        obj.setId(null);
        obj.setInstante(new Date());
        obj.setCliente(clienteService.findById(obj.getCliente().getId()));
        obj.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        if (obj.getPagamento() instanceof PagamentoComBoleto){
            PagamentoComBoleto pgto = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preencherPagamentoComBoleto(pgto, obj.getInstante());
        }
        obj = repository.save(obj);
        pagamentoRepository.save(obj.getPagamento());
        for(ItemPedido ip : obj.getItems()){
            ip.setDesconto(0.0);
            ip.setProduto(produtoService.findById(ip.getProduto().getId()));
            ip.setPreco(ip.getProduto().getPrice());
            ip.setPedido(obj);
        }
        itemPedidoRepository.saveAll(obj.getItems());
        emailService.sendOrderConfirmationHtmlEmail(obj);
        return obj;
   }

    public Page<Pedido> findCliente(Integer page, Integer linesPerPage, String orderBy, String direction) {
        UserSpringSecurity user = UserService.authenticated();
        if(user == null){
            throw new AuthorizationException("Acesso negado");
        }
        Cliente cliente = clienteService.findById(user.getId());
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repository.findByCliente(cliente, pageRequest);
    }

}
