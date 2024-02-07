package ru.otus.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.dto.serde.EnumTaskTypeDeserializer;
import ru.otus.model.TaskType;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class TaskTemplateDto implements Serializable {
    private String id;

    @JsonDeserialize(using = EnumTaskTypeDeserializer.class)
    private TaskType taskType;

    private ConnectionDto connection;
    private ParametersTemplateDto parametersTemplate;
    private MessageTemplateDto messageTemplate;
}
