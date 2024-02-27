package ru.otus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.dao.ParametersDao;
import ru.otus.dto.ParametersContentDto;
import ru.otus.dto.ParametersDto;
import ru.otus.mapper.ParametersContentMapper;
import ru.otus.mapper.ParametersMapper;
import ru.otus.model.Parameters;
import ru.otus.model.ParametersContent;

@RestController
@RequestMapping("/parameters")
public class ParametersRestController {

    private final ParametersDao parametersDao;
    private final ParametersMapper parametersMapper;
    private final ParametersContentMapper parametersContentMapper;

    public ParametersRestController(ParametersDao parametersDao,
                                    ParametersMapper parametersMapper,
                                    ParametersContentMapper parametersContentMapper) {
        this.parametersDao = parametersDao;
        this.parametersMapper = parametersMapper;
        this.parametersContentMapper = parametersContentMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParametersDto> getParameters(@PathVariable String id) {
        Parameters parameters = parametersDao.findById(id);
        return ResponseEntity.ok(
                parametersMapper.toDto(parameters));
    }

    @PostMapping
    public ResponseEntity<ParametersDto> saveParameters(
            Model model,
            @RequestBody ParametersDto parametersDto) {
        Parameters parameters = parametersMapper.toModel(parametersDto);
        Parameters savedParameters = parametersDao.save(parameters);
        return ResponseEntity.ok(
                parametersMapper.toDto(savedParameters));
    }

    @PatchMapping
    public ResponseEntity<ParametersDto> updateParameters(
            Model model,
            @RequestBody ParametersContentDto parametersContentDto) {
        ParametersContent parametersContent = parametersContentMapper.toModel(parametersContentDto);
        Parameters updatedParameters = parametersDao.updateContent(parametersContent);
        return ResponseEntity.ok(
                parametersMapper.toDto(updatedParameters));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteParameters(@PathVariable String id) {
        return ResponseEntity.ok(
                parametersDao.delete(id));
    }
}
