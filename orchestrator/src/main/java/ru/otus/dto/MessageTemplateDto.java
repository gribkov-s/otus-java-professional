package ru.otus.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.model.EntityField;
import ru.otus.model.EntitySchema;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class MessageTemplateDto {
    private String id;
    private Set<EntityField> fields;
}
