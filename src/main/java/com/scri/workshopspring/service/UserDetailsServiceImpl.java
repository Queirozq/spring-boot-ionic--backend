package com.scri.workshopspring.service;

import com.scri.workshopspring.domain.Cliente;
import com.scri.workshopspring.repositories.ClienteRepository;
import com.scri.workshopspring.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

     @Autowired
     private ClienteRepository repository;


     @Override
     public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      Cliente cliente = repository.findByEmail(email);
      if(cliente == null){
          throw new UsernameNotFoundException(email);
       }

        return new UserSpringSecurity(cliente.getId(), cliente.getEmail(), cliente.getSenha(), cliente.getPerfis());
     }
}
