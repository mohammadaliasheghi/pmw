package com.m2a.web.repository;

import com.m2a.web.entity.SecurityInformationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityInformationRepository extends JpaRepository<SecurityInformationEntity, Long> {

    Optional<SecurityInformationEntity> findUsersByUsername(String username);
}
