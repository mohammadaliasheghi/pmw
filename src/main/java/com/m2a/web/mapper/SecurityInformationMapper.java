package com.m2a.web.mapper;

import com.m2a.web.entity.SecurityInformationEntity;
import com.m2a.web.model.SecurityInformationModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL
        , componentModel = "spring"
)
public interface SecurityInformationMapper {

    static SecurityInformationMapper get() {
        return Mappers.getMapper(SecurityInformationMapper.class);
    }

    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "roles", ignore = true)
    SecurityInformationEntity modelToEntity(SecurityInformationModel model);
}