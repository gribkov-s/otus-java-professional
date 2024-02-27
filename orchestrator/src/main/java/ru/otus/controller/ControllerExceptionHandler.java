package ru.otus.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.otus.dao.DbMessageDao;
import ru.otus.model.ErrorMsg;

@RestControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(DbMessageDao.class); ///

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMsg> runtimeException(RuntimeException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMsg(exception.getMessage()));
    }
}
