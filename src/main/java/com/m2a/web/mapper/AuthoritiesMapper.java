package com.m2a.web.mapper;

import com.m2a.web.entity.AuthoritiesEntity;
import com.m2a.web.model.AuthoritiesModel;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL
        , componentModel = "spring"
)
public interface AuthoritiesMapper {

    static AuthoritiesMapper get() {
        return Mappers.getMapper(AuthoritiesMapper.class);
    }

    AuthoritiesEntity modelToEntity(AuthoritiesModel model);
}