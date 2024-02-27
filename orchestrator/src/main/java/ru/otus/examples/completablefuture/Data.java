package ru.otus.examples.completablefuture;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Data {
    private String value;
    private Long delay;
    private Long timestamp;

    public Data(String value, Long delay) {
        this.value = value;
        this.delay = delay;
        this.timestamp = System.currentTimeMillis();
    }

    public Data(String value) {
        this.value = value;
        this.delay = 0L;
        this.timestamp = System.currentTimeMillis();
    }
}
