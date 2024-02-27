package ru.otus.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.model.EntityField;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ConnectionTemplateDto {
    private String id;
    private Set<EntityField> fields;
}
