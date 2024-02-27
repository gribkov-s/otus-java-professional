package ru.otus.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.otus.dto.MessageDto;
import ru.otus.model.Message;

@Mapper(componentModel = "spring",
        uses = {MessageTemplateMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageMapper {
    MessageDto toDto(Message message);
    Message toModel(MessageDto messageDto);
}
