package ru.otus.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.otus.dto.ParametersTemplateDto;
import ru.otus.model.ParametersTemplate;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParametersTemplateMapper {
    ParametersTemplateDto toDto(ParametersTemplate parametersTemplate);
    ParametersTemplate toModel(ParametersTemplateDto parametersTemplateDto);
}
