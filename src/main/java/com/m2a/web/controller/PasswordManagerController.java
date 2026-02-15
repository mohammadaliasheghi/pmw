package com.m2a.web.controller;

import com.m2a.web.model.PasswordManagerModel;
import com.m2a.web.service.PasswordManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/password-manager")
@RequiredArgsConstructor
public class PasswordManagerController {

    private final PasswordManagerService passwordManagerService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody PasswordManagerModel model) {
        return new ResponseEntity<>(passwordManagerService.create(model), HttpStatus.CREATED);
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody PasswordManagerModel model) {
        return new ResponseEntity<>(passwordManagerService.update(model), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return new ResponseEntity<>(passwordManagerService.delete(id), HttpStatus.OK);
    }

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> get(@PathVariable Long id) {
        return new ResponseEntity<>(passwordManagerService.get(id), HttpStatus.OK);
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getList(@PathVariable Long securityInformationId) {
        return new ResponseEntity<>(passwordManagerService.getList(securityInformationId), HttpStatus.OK);
    }
}
