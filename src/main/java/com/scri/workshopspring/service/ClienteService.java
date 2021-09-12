package com.scri.workshopspring.service;
import com.scri.workshopspring.domain.*;
import com.scri.workshopspring.domain.Cliente;
import com.scri.workshopspring.domain.DTO.ClienteDTO;
import com.scri.workshopspring.domain.DTO.ClienteNewDTO;
import com.scri.workshopspring.domain.enums.Perfil;
import com.scri.workshopspring.domain.enums.TipoCliente;
import com.scri.workshopspring.repositories.CidadeRepository;
import com.scri.workshopspring.repositories.ClienteRepository;
import com.scri.workshopspring.repositories.EnderecoRepository;
import com.scri.workshopspring.security.UserSpringSecurity;
import com.scri.workshopspring.service.exceptions.AuthorizationException;
import com.scri.workshopspring.service.exceptions.DatabaseException;
import com.scri.workshopspring.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;
    @Autowired
    private CidadeRepository repositoryCidade;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private BCryptPasswordEncoder pe;
    @Autowired
    private S3Service s3Service;
    @Autowired
    private ImageService imageService;

    @Value("${img.prefix.client.profile}")
    private String prefix;
    @Value("${img.profile.size}")
    private Integer size;

    public Cliente findById(Integer id){
        UserSpringSecurity userSS = UserService.authenticated();
        if(userSS == null || !userSS.hasRole(Perfil.ADM) && !Objects.equals(userSS.getId(), id)){
            throw new AuthorizationException("Você não tem permissão para fazer essa busca");
        }
        Optional<Cliente> cliente = repository.findById(id);
        return cliente.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public List<Cliente> findAll(){
        return repository.findAll();
    }

    public Cliente insert(Cliente obj){
         obj.setId(null);
         obj = repository.save(obj);
         enderecoRepository.saveAll(obj.getEnderecos());
         return obj;
    }

    public Cliente findByEmail(String email){
        UserSpringSecurity userSS = UserService.authenticated();
        if(userSS == null || !userSS.hasRole(Perfil.ADM) && !Objects.equals(userSS.getUsername(),email)){
            throw new AuthorizationException("Você não tem permissão para fazer essa busca");
        }
        Cliente cliente = repository.findByEmail(email);
        if(cliente == null){
            throw new ResourceNotFoundException("Esse cliente não existe!");
        }
        return cliente;
    }

    public Cliente update(Integer id, Cliente obj) {
        try {
            Cliente cliente = repository.getById(id);
            cliente.setName(obj.getName());
            cliente.setEmail(obj.getEmail());
            return repository.save(cliente);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    public void delete(Integer id) {
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Não é possível deletar um Cliente que possui pedidos em andamento");
        }
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repository.findAll(pageRequest);
    }

    public Cliente fromDTO(ClienteDTO obj){
        Cliente cliente = new Cliente();
        cliente.setName(obj.getName());
        cliente.setEmail(obj.getEmail());
        return cliente;
    }

    public Cliente fromDTO(ClienteNewDTO obj){
        Cliente cliente = new Cliente(null, obj.getName(), obj.getEmail(), obj.getCpfOuCnpj(), TipoCliente.toEnum(obj.getTipo()), pe.encode(obj.getSenha()));
        Optional<Cidade> cidade = repositoryCidade.findById(obj.getCidadeId());
        Endereco endereco = new Endereco(null, obj.getLogradouro(), obj.getNumero(), obj.getComplemento(), obj.getBairro(), obj.getCep(), cidade.get(), cliente);
        cliente.getEnderecos().add(endereco);
        cliente.setTelefones(Arrays.asList(obj.getTelefone1(), obj.getTelefone2(), obj.getTelefone3()));
        return cliente;
    }

    public URI uploadProfilePicture(MultipartFile multipartFile){
        UserSpringSecurity userSS = UserService.authenticated();
        if(userSS == null){
            throw new AuthorizationException("Você precisa estar logado para adicionar foto de perfil!");
        }

        BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
        jpgImage = imageService.cropSquare(jpgImage);
        jpgImage = imageService.resize(jpgImage, size);
        String fileName = prefix + userSS.getId() + ".jpg";

        return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
    }
}
