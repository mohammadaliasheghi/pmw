package com.m2a.web.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.m2a.common.entity.BasePO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "password_manager")
public class PasswordManagerEntity extends BasePO {

    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "description")
    private String description;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    @JoinColumn(name = "security_information_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private SecurityInformationEntity securityInformation;

    @Column(name = "security_information_id", nullable = false)
    private Long securityInformationId;
}
