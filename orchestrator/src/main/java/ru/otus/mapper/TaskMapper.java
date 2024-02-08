package ru.otus.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.otus.dto.TaskDto;
import ru.otus.model.Task;

@Mapper(componentModel = "spring",
        uses = {ConnectionMapper.class,
                ParametersMapper.class,
                MessageMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {
    TaskDto toDto(Task task);
    Task toModel(TaskDto taskDto);
}
