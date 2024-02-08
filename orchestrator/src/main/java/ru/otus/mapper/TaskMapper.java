package ru.otus.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import ru.otus.dto.TaskDto;
import ru.otus.model.Task;

@Mapper(componentModel = "spring",
        uses = {ConnectionMapper.class,
                ParametersMapper.class,
                MessageMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {
    @Mapping(source = "next", target = "nextId", qualifiedByName = "getTaskId")
    TaskDto toDto(Task task);
    Task toModel(TaskDto taskDto);

    @Named("getTaskId")
    public static String getTaskId(Task task) {
        return task == null ? null : task.getId();
    }
}
