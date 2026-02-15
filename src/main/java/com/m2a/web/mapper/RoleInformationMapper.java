package com.m2a.web.mapper;

import com.m2a.web.entity.RoleInformationEntity;
import com.m2a.web.model.RoleInformationModel;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL
        , componentModel = "spring"
)
public interface RoleInformationMapper {

    static RoleInformationMapper get() {
        return Mappers.getMapper(RoleInformationMapper.class);
    }

    RoleInformationModel entityToModel(RoleInformationEntity entity);

    RoleInformationEntity modelToEntity(RoleInformationModel model);
}