package ru.otus.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.otus.dto.ConnectionTemplateDto;
import ru.otus.model.ConnectionTemplate;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConnectionTemplateMapper {
    ConnectionTemplateDto toDto(ConnectionTemplate connectionTemplate);
    ConnectionTemplate toModel(ConnectionTemplateDto connectionTemplateDto);
}
