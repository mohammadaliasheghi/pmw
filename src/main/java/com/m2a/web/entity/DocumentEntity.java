package com.m2a.web.entity;

import com.m2a.common.entity.BasePO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Setter
@Getter
@Table(name = "document")
public class DocumentEntity extends BasePO {

    @NotBlank
    @NotNull
    @Column(name = "document_name", length = 200)
    private String documentName;

    @NotBlank
    @NotNull
    @Column(name = "document_type", length = 100)
    private String documentType;

    @Column(name = "document_path", length = 500)
    private String documentPath;
}

