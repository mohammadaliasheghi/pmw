package com.m2a.web.model;

import com.m2a.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoleInformationModel extends BaseModel {
    private String name;
}
