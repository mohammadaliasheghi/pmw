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
@Table(name = "chat_history")
public class ChatHistoryEntity extends BasePO {

    @NotNull
    @Column(name = "prompt", nullable = false)
    private String prompt;
    @NotNull
    @Column(name = "response")
    private String response;
    @NotNull
    @Column(name = "chat_id")
    private Long chatId;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    @JoinColumn(name = "document_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private DocumentEntity document;

    @NotNull
    @Column(name = "document_id", nullable = false)
    private Long documentId;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    @JoinColumn(name = "security_information_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private SecurityInformationEntity securityInformation;

    @NotNull
    @Column(name = "security_information_id", nullable = false)
    private Long securityInformationId;
}
