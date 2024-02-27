package ru.otus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.dao.ParametersTemplateDao;
import ru.otus.dto.ParametersTemplateDto;
import ru.otus.mapper.ParametersTemplateMapper;
import ru.otus.model.ParametersTemplate;
import java.util.List;

@RestController
@RequestMapping("/parameters-templates")
public class ParametersTemplateRestController {

    private final ParametersTemplateDao parametersTemplateDao;
    private final ParametersTemplateMapper parametersTemplateMapper;

    public ParametersTemplateRestController(ParametersTemplateDao parametersTemplateDao,
                                            ParametersTemplateMapper parametersTemplateMapper) {
        this.parametersTemplateDao = parametersTemplateDao;
        this.parametersTemplateMapper = parametersTemplateMapper;
    }

    @GetMapping("/ids")
    public ResponseEntity<List<String>> getParametersTemplatesIds() {
        return ResponseEntity.ok(
                parametersTemplateDao.findAllIds());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParametersTemplateDto> getParametersTemplate(@PathVariable String id) {
        ParametersTemplate parametersTemplate = parametersTemplateDao.findById(id);
        return ResponseEntity.ok(
                parametersTemplateMapper.toDto(parametersTemplate));
    }

    @PostMapping
    public ResponseEntity<ParametersTemplateDto> saveParametersTemplate(
            Model model,
            @RequestBody ParametersTemplateDto parametersTemplateDto) {
        ParametersTemplate parametersTemplate = parametersTemplateMapper.toModel(parametersTemplateDto);
        ParametersTemplate savedParametersTemplate = parametersTemplateDao.save(parametersTemplate);
        return ResponseEntity.ok(
                parametersTemplateMapper.toDto(savedParametersTemplate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMessageTemplate(@PathVariable String id) {
        return ResponseEntity.ok(
                parametersTemplateDao.delete(id));
    }
}
