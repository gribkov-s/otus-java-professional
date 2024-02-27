package ru.otus.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.otus.dto.ConnectionContentDto;
import ru.otus.model.ConnectionContent;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConnectionContentMapper {
    ConnectionContentDto toDto(ConnectionContent connectionContent);
    ConnectionContent toModel(ConnectionContentDto connectionContentDto);
}
