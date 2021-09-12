package com.scri.workshopspring.resources;

import com.scri.workshopspring.domain.Cliente;
import com.scri.workshopspring.domain.DTO.EmailDTO;
import com.scri.workshopspring.repositories.ClienteRepository;
import com.scri.workshopspring.security.JWTUtil;
import com.scri.workshopspring.security.UserSpringSecurity;
import com.scri.workshopspring.service.AuthService;
import com.scri.workshopspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/refresh_token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response)throws NullPointerException {
        UserSpringSecurity user = UserService.authenticated();
        String token = jwtUtil.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/forgot")
    public ResponseEntity<Void> forgotPassword(@Valid @RequestBody EmailDTO emailDTO)throws NullPointerException {
        authService.sendNewPassword(emailDTO.getEmail());
        return ResponseEntity.noContent().build();
    }
}
