package ru.otus.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.otus.dto.MessageContentDto;
import ru.otus.model.MessageContent;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageContentMapper {
    MessageContentDto toDto(MessageContent messageContent);
    MessageContent toModel(MessageContentDto messageContentDto);
}
