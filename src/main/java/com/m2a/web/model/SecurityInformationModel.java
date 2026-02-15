package com.m2a.web.model;

import com.m2a.common.model.BaseModel;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SecurityInformationModel extends BaseModel {
    private String username;
    private String password;
}