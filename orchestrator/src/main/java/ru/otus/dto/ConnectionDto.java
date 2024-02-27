package ru.otus.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
public class ConnectionDto {
    private String id;
    private ConnectionTemplateDto template;
    private HashMap<String, Object> content;

}
