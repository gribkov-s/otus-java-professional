package ru.otus.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
public class MessageDto {
    private String id;
    private MessageTemplateDto template;
    private HashMap<String, Object> content;

}
