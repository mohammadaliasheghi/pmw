package com.m2a.web.service;

import com.m2a.common.util.ResourceBundle;
import com.m2a.web.entity.PasswordManagerEntity;
import com.m2a.web.mapper.PasswordManagerMapper;
import com.m2a.web.model.PasswordManagerModel;
import com.m2a.web.repository.PasswordManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//todo must be encrypted all data in this entity
@Service
@RequiredArgsConstructor
public class PasswordManagerService {

    private final PasswordManagerRepository repository;

    @Transactional(rollbackFor = Exception.class)
    public String create(PasswordManagerModel model) {
        String validate = this.validate(model);
        if (!validate.isEmpty()) return validate;
        PasswordManagerEntity entity = PasswordManagerMapper
                .get()
                .modelToEntity(model);
        try {
            repository.save(entity);
            return ResourceBundle.getMessageByKey("PasswordManagerCreated");
        } catch (Exception e) {
            e.fillInStackTrace();
            return ResourceBundle.getMessageByKey("ServerError");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public String update(PasswordManagerModel model) {
        if (model.getId() == null) return ResourceBundle.getMessageByKey("IdMustNotBeNull");
        if (repository.existsBySecurityInformationIdAndId(model.getSecurityInformationId(), model.getId()))
            return ResourceBundle.getMessageByKey("IdNotFoundForThisUser");
        String validate = this.validate(model);
        if (!validate.isEmpty()) return validate;
        PasswordManagerEntity entity = PasswordManagerMapper
                .get()
                .modelToEntity(model);
        try {
            repository.save(entity);
            return ResourceBundle.getMessageByKey("PasswordManagerUpdated");
        } catch (Exception e) {
            e.fillInStackTrace();
            return ResourceBundle.getMessageByKey("ServerError");
        }
    }

    private String validate(PasswordManagerModel model) {
        if (model.getTitle() == null || model.getTitle().isEmpty())
            return ResourceBundle.getMessageByKey("TitleIsRequired");
        if (model.getPassword() == null || model.getPassword().isEmpty())
            return ResourceBundle.getMessageByKey("PasswordIsRequired");
        if (repository.existsBySecurityInformationIdAndTitleAndIdNot(
                model.getSecurityInformationId(),
                model.getTitle(),
                model.getId() != null ? model.getId() : 0L)
        ) return ResourceBundle.getMessageByKey("TitleMustBeUnique");
        return "";
    }

    @Transactional(rollbackFor = Exception.class)
    public String delete(Long id) {
        try {
            repository.deleteById(id);
            return ResourceBundle.getMessageByKey("PasswordManagerDeleted");
        } catch (Exception e) {
            e.fillInStackTrace();
            return ResourceBundle.getMessageByKey("ServerError");
        }
    }

    @Transactional(readOnly = true)
    public PasswordManagerModel get(Long id) {
        PasswordManagerEntity entity = repository.findById(id)
                .orElse(new PasswordManagerEntity());
        return PasswordManagerMapper.get().entityToModel(entity);
    }

    @Transactional(readOnly = true)
    public List<PasswordManagerModel> getList(Long securityInformationId) {
        List<PasswordManagerEntity> entities = repository.findBySecurityInformationId(securityInformationId);
        return PasswordManagerMapper
                .get()
                .entitiesToModels(entities);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteList(Long securityInformationId) {
        List<PasswordManagerModel> list = this.getList(securityInformationId);
        if (list == null || list.isEmpty()) return;
        list.forEach(model -> repository.deleteById(model.getId()));
    }
}
