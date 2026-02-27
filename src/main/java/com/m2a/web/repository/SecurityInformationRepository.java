package com.m2a.web.repository;

import com.m2a.common.repository.AbstractDAO;
import com.m2a.web.entity.SecurityInformationEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityInformationRepository extends AbstractDAO<SecurityInformationEntity> {

    Optional<SecurityInformationEntity> findUsersByUsername(String username);
}
