package com.scri.workshopspring.service.validation;

import com.scri.workshopspring.domain.Cliente;
import com.scri.workshopspring.domain.DTO.ClienteNewDTO;
import com.scri.workshopspring.domain.enums.TipoCliente;
import com.scri.workshopspring.repositories.ClienteRepository;
import com.scri.workshopspring.resources.exceptions.FieldMessage;
import com.scri.workshopspring.service.validation.util.BR;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Autowired
    private ClienteRepository repository;

    @Override
    public void initialize(ClienteInsert ann) {
    }
    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())){
           list.add(new FieldMessage("CpfOuCnpj", "CPF inválido"));
        }

        if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())){
            list.add(new FieldMessage("CpfOuCnpj", "CNPJ inválido"));
        }

        Cliente cliente = repository.findByEmail(objDto.getEmail());

        if(cliente != null){
            list.add(new FieldMessage("Email", "Email já existe."));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}