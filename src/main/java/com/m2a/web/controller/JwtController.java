package com.m2a.web.controller;

import com.m2a.common.config.Constant;
import com.m2a.web.config.JwtConfig;
import com.m2a.web.model.SecurityInformationModel;
import com.m2a.web.service.SecurityInformationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/jwt")
@RequiredArgsConstructor
public class JwtController {

    private final AuthenticationManager authenticationManager;
    private final SecurityInformationService securityInformationService;
    private JwtConfig jwtConfig;

    @Autowired
    public void setJwtConfig(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @PostMapping(value = "/token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getToken(@RequestBody SecurityInformationModel model,
                                      HttpServletResponse response,
                                      HttpServletRequest request) {
        if (model == null || model.getUsername() == null || model.getPassword() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(model.getUsername(), model.getPassword()));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String token = jwtConfig.generateToken(model.getUsername(), request);
        response.addHeader(Constant.AUTHORIZATION, token);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody SecurityInformationModel model) {
        return new ResponseEntity<>(securityInformationService.create(model), HttpStatus.CREATED);
    }
}