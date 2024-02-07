package ru.otus.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.otus.dto.TaskTemplateDto;
import ru.otus.model.TaskTemplate;

@Mapper(componentModel = "spring",
        uses = {ConnectionMapper.class, ParametersTemplateMapper.class, MessageTemplateMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskTemplateMapper {
    TaskTemplateDto toDto(TaskTemplate taskTemplate);
    TaskTemplate toModel(TaskTemplateDto taskTemplateDto);
}
