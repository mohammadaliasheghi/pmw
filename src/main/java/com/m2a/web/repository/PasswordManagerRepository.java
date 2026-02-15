package com.m2a.web.repository;

import com.m2a.web.entity.PasswordManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasswordManagerRepository extends JpaRepository<PasswordManagerEntity, Long> {

    List<PasswordManagerEntity> findBySecurityInformationId(Long securityInformationId);

    Boolean existsBySecurityInformationIdAndTitleAndIdNot(Long securityInformationId, String title, Long id);

    Boolean existsBySecurityInformationIdAndId(Long securityInformationId, Long id);
}
