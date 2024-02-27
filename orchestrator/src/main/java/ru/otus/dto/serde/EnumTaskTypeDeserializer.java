package ru.otus.dto.serde;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ru.otus.model.TaskType;

import java.io.IOException;

public class EnumTaskTypeDeserializer extends JsonDeserializer<TaskType> {

    @Override
    public TaskType deserialize(JsonParser jsonParser,
                                DeserializationContext deserializationContext) throws IOException {
        final ObjectCodec objectCodec = jsonParser.getCodec();
        final JsonNode node = objectCodec.readTree(jsonParser);
        final String type = node.asText();
        return TaskType.valueOf(type.toUpperCase());
    }
}