package ru.otus.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
public class ParametersContentDto {
    private String parametersId;
    private HashMap<String, Object> content;
}
