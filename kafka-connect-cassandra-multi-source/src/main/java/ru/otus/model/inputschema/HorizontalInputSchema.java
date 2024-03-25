package ru.otus.model.inputschema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HorizontalInputSchema implements InputSchema {
    private List<String> valueFields;
}
