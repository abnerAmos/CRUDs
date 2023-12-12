package com.study.security.controller;

import com.study.security.security.AuthenticationRequest;
import com.study.security.model.User;
import com.study.security.security.TokenResponse;
import com.study.security.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(request.login(), request.pass());
        var authentication =  authenticationManager.authenticate(authenticationToken);
        var token = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenResponse(token));
    }

}
