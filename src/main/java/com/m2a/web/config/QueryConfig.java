package com.m2a.web.config;

import com.m2a.web.enums.RoleEnum;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Configuration
public class QueryConfig {

    private final EntityManager entityManager;

    public QueryConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Bean
    @Transactional(rollbackFor = Exception.class)
    public boolean initializeRoles() {
        try {
            List<Long> existingRoleIds = getExistingRoleIds();
            List<RoleEnum> rolesToInsert = filterMissingRoles(existingRoleIds);
            if (rolesToInsert.isEmpty())
                return false;
            insertRoles(rolesToInsert);
            return true;
        } catch (Exception e) {
            e.fillInStackTrace();
            return false;
        }
    }

    private List<Long> getExistingRoleIds() {
        return entityManager.createQuery(
                        "SELECT r.id FROM RoleInformationEntity r WHERE r.id IN :roleIds", Long.class)
                .setParameter("roleIds", Arrays.stream(RoleEnum.values())
                        .map(RoleEnum::getId)
                        .toList())
                .getResultList();
    }

    private List<RoleEnum> filterMissingRoles(List<Long> existingRoleIds) {
        return Arrays.stream(RoleEnum.values())
                .filter(role -> !existingRoleIds.contains(role.getId()))
                .toList();
    }

    private void insertRoles(List<RoleEnum> roles) {
        String insertSql = "INSERT INTO role_information (id, name) VALUES (?, ?)";

        roles.forEach(role -> entityManager.createNativeQuery(insertSql)
                .setParameter(1, role.getId())
                .setParameter(2, role.getName())
                .executeUpdate());
    }
}
