package com.scri.workshopspring.service;

import com.scri.workshopspring.domain.Cliente;
import com.scri.workshopspring.repositories.ClienteRepository;
import com.scri.workshopspring.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {


    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailService emailService;

    private Random random = new Random();

    public void sendNewPassword(String email){
        Cliente cliente = clienteRepository.findByEmail(email);
        if(cliente == null){
            throw new ResourceNotFoundException("Cliente não existe.");
        }
        String newPass = newPassword();
        cliente.setSenha(bCryptPasswordEncoder.encode(newPass));
        clienteRepository.save(cliente);
        emailService.sendNewPasswordEmail(cliente, newPass);
    }

    private String newPassword() {
        char[] vet = new char[10];
        for(int i = 0; i < 10; i++){
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    private char randomChar() {
        int opt = random.nextInt(3);
        if(opt == 0){ //gera um dígito
           return (char) (random.nextInt(10) + 48);
        }
        else if (opt == 1){ //gera uma letra maiúscula
           return (char) (random.nextInt(26) + 65);
        }
        else{ // gera uma letra minúscula
            return (char) (random.nextInt(26) + 97);
        }
    }
}