package com.m2a.web.mapper;

import com.m2a.web.entity.ChatHistoryEntity;
import com.m2a.web.model.ChatHistoryModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL
        , componentModel = "spring"
)
public interface ChatHistoryMapper {

    static ChatHistoryMapper get() {
        return Mappers.getMapper(ChatHistoryMapper.class);
    }

    @Mapping(target = "securityInformation", ignore = true)
    ChatHistoryModel entityToModel(ChatHistoryEntity entity);

    ChatHistoryEntity modelToEntity(ChatHistoryModel model);

    List<ChatHistoryModel> entitiesToModels(List<ChatHistoryEntity> entities);
}