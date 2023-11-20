package ru.otus.service;

import java.util.List;

public interface NumbersService {
    List<Long> getByRange(Long from, Long to);
}
