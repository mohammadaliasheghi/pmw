package com.m2a.web.controller;

import com.m2a.common.util.ResponseUtil;
import com.m2a.web.model.SecurityInformationModel;
import com.m2a.web.service.SecurityInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/sec-info")
@RequiredArgsConstructor
public class SecurityInformationController {

    private final SecurityInformationService securityInformationService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody SecurityInformationModel model) {
        return ResponseUtil.created(securityInformationService.create(model));
    }

    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseUtil.success(securityInformationService.delete(id));
    }
}
