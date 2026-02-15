package com.m2a.web.mapper;

import com.m2a.web.entity.PasswordManagerEntity;
import com.m2a.web.model.PasswordManagerModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL
        , componentModel = "spring"
)
public interface PasswordManagerMapper {

    static PasswordManagerMapper get() {
        return Mappers.getMapper(PasswordManagerMapper.class);
    }

    @Mapping(target = "securityInformation", ignore = true)
    PasswordManagerModel entityToModel(PasswordManagerEntity entity);

    PasswordManagerEntity modelToEntity(PasswordManagerModel model);

    List<PasswordManagerModel> entitiesToModels(List<PasswordManagerEntity> entities);
}