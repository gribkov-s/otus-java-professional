package ru.otus.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.otus.dto.TaskDto;
import ru.otus.dto.TaskRangeDto;
import ru.otus.model.Task;
import ru.otus.model.TaskRange;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskRangeMapper {
    TaskRangeDto toDto(TaskRange taskRange);
    TaskRange toModel(TaskRangeDto taskRangeDto);
}
