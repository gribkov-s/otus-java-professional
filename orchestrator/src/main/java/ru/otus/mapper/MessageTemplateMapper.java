package ru.otus.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.otus.dto.MessageTemplateDto;
import ru.otus.model.MessageTemplate;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageTemplateMapper {
    MessageTemplateDto toDto(MessageTemplate messageTemplate);
    MessageTemplate toModel(MessageTemplateDto messageTemplateDto);
}
