package ru.otus;

import ru.otus.annotations.Log;

public class TestLoggingImpl implements TestLoggingInterface {
    @Log
    @Override
    public void printName() {
        System.out.println(this.getClass().getCanonicalName());
    }

    @Log
    @Override
    public Long sum(Long arg1, Long arg2) {
        return arg1 + arg2;
    }

    @Override
    public Long sum(Long arg1, Long arg2, Long arg3) {
        return arg1 + arg2 + arg3;
    }

    @Log
    @Override
    public Double sum(Double arg1, Double arg2) {
        return arg1 + arg2;
    }

    @Log
    @Override
    public Double share(Double whole, Double part) {
        return part / whole;
    }
}
