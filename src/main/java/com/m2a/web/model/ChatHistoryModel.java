package com.m2a.web.model;

import com.m2a.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChatHistoryModel extends BaseModel {

    private String prompt;
    private String response;
    private Long chatId;
    private DocumentModel document;
    private Long documentId;
    private SecurityInformationModel securityInformation;
    private Long securityInformationId;
}
