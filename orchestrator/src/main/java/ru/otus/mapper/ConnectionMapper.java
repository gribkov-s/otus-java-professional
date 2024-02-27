package ru.otus.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.otus.dto.ConnectionDto;
import ru.otus.model.Connection;

@Mapper(componentModel = "spring",
        uses = {ConnectionTemplateMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConnectionMapper {
    ConnectionDto toDto(Connection connection);
    Connection toModel(ConnectionDto connectionDto);
}
