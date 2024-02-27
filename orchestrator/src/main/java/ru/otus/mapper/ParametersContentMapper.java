package ru.otus.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.otus.dto.ParametersContentDto;
import ru.otus.model.ParametersContent;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParametersContentMapper {
    ParametersContentDto toDto(ParametersContent parametersContent);
    ParametersContent toModel(ParametersContentDto parametersContentDto);
}
