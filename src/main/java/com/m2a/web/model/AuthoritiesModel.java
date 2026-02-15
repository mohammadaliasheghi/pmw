package com.m2a.web.model;

import com.m2a.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthoritiesModel extends BaseModel {
    private SecurityInformationModel securityInformation;
    private Long securityInformationId;
    private RoleInformationModel roleInformation;
    private Long roleInformationId;
}
