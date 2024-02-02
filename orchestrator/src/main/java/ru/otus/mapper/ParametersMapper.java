package ru.otus.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.otus.dto.ParametersDto;
import ru.otus.model.Parameters;

@Mapper(componentModel = "spring",
        uses = {ParametersTemplateMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParametersMapper {
    ParametersDto toDto(Parameters parameters);
    Parameters toModel(ParametersDto parametersDto);
}
