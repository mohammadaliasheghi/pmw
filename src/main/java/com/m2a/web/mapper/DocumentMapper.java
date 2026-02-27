package com.m2a.web.mapper;

import com.m2a.web.entity.DocumentEntity;
import com.m2a.web.model.DocumentModel;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL
        , componentModel = "spring"
)
public interface DocumentMapper {

    static DocumentMapper get() {
        return Mappers.getMapper(DocumentMapper.class);
    }

    DocumentEntity entityToModel(DocumentModel entity);

    DocumentModel modelToEntity(DocumentEntity model);

    List<DocumentModel> entitiesToModels(List<DocumentEntity> entities);
}