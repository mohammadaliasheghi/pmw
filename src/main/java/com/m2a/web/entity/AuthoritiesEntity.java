package com.m2a.web.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.m2a.common.entity.BasePO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "authorities")
public class AuthoritiesEntity extends BasePO {

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    @JoinColumn(name = "security_information_id", insertable = false, updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private SecurityInformationEntity securityInformation;

    @NotNull
    @Column(name = "security_information_id", nullable = false)
    private Long securityInformationId;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    @JoinColumn(name = "role_information_id", insertable = false, updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private RoleInformationEntity roleInformation;

    @NotNull
    @Column(name = "role_information_id", nullable = false)
    private Long roleInformationId;
}
