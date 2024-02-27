package ru.otus.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
public class ParametersDto {
    private String id;
    private ParametersTemplateDto template;
    private HashMap<String, Object> content;

}
