package com.m2a.web.entity;

import com.m2a.common.entity.BasePO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "role_information")
public class RoleInformationEntity extends BasePO implements GrantedAuthority {

    @NotNull
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Override
    public String getAuthority() {
        return this.name;
    }
}
