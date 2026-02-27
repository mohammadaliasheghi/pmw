package com.m2a.web.service;

import com.m2a.common.util.ResourceBundle;
import com.m2a.web.entity.SecurityInformationEntity;
import com.m2a.web.enums.RoleEnum;
import com.m2a.web.mapper.SecurityInformationMapper;
import com.m2a.web.model.SecurityInformationModel;
import com.m2a.web.repository.SecurityInformationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SecurityInformationService {

    private SecurityInformationRepository repository;
    private AuthoritiesService authoritiesService;
    private PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = Exception.class)
    public String create(SecurityInformationModel model) {
        String validate = this.validateCreate(model);
        if (!validate.isEmpty()) return validate;
        model.setPassword(passwordEncoder.encode(model.getPassword()));
        SecurityInformationEntity entity = SecurityInformationMapper.get().modelToEntity(model);
        entity.setEnabled(true);
        try {
            SecurityInformationEntity created = repository.save(entity);
            authoritiesService.create(created.getId(), RoleEnum.ROLE_USER.getId());
            return ResourceBundle.getMessageByKey("YourAccountHasBeenCreated");
        } catch (Exception e) {
            e.fillInStackTrace();
            return ResourceBundle.getMessageByKey("ServerError");
        }
    }

    private String validateCreate(SecurityInformationModel model) {
        if (model.getUsername() == null)
            return ResourceBundle.getMessageByKey("UsernameIsRequired");
        if (model.getPassword() == null)
            return ResourceBundle.getMessageByKey("PasswordIsRequired");
        Optional<SecurityInformationEntity> entity =
                repository.findUsersByUsername(model.getUsername());
        if (entity.isPresent())
            return ResourceBundle.getMessageByKey("UsernameIsAlreadyTaken");
        return "";
    }

    @Transactional(rollbackFor = Exception.class)
    public String delete(Long id) {
        SecurityInformationEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Security information not found"));
        repository.delete(entity);
        return ResourceBundle.getMessageByKey("YourAccountHasBeenDeleted");
    }

    @Autowired
    public void setRepository(SecurityInformationRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setAuthoritiesService(AuthoritiesService authoritiesService) {
        this.authoritiesService = authoritiesService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
