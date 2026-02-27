package com.m2a.web.model;

import com.m2a.common.entity.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DocumentModel extends BasePO {

    private String documentName;
    private String documentType;
    private String documentPath;
}

