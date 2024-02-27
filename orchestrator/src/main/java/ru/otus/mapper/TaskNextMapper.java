package ru.otus.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.otus.dto.TaskNextDto;
import ru.otus.model.TaskNext;

@Mapper(componentModel = "spring",
        uses = {TaskMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskNextMapper {
    TaskNextDto toDto(TaskNext taskNext);
    TaskNext toModel(TaskNextDto taskNextDto);
}
