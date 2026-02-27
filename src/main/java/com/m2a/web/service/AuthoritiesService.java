package com.m2a.web.service;

import com.m2a.web.entity.AuthoritiesEntity;
import com.m2a.web.mapper.AuthoritiesMapper;
import com.m2a.web.model.AuthoritiesModel;
import com.m2a.web.repository.AuthoritiesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthoritiesService {

    private final AuthoritiesRepository repository;

    @Transactional(rollbackFor = Exception.class)
    public void create(Long securityInformationId, Long roleInformationId) {
        AuthoritiesModel model = new AuthoritiesModel();
        model.setSecurityInformationId(securityInformationId);
        model.setRoleInformationId(roleInformationId);
        AuthoritiesEntity entity = AuthoritiesMapper.get().modelToEntity(model);
        repository.save(entity);
    }
}
