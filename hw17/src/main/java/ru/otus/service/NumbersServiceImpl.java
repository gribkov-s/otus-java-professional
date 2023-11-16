package ru.otus.service;

import java.util.List;
import java.util.stream.LongStream;

public class NumbersServiceImpl implements NumbersService {
    @Override
    public List<Long> getByRange(Long from, Long to) {
        return LongStream
                .rangeClosed(from, to)
                .boxed()
                .toList();
    }
}
