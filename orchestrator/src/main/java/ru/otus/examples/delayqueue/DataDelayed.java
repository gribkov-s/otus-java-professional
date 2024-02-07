package ru.otus.examples.delayqueue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataDelayed implements Delayed {
    private String value;
    private Long delayMs;
    private Long timestamp;
    private Long created;

    public DataDelayed(String value, Long delayMs) {
        this.value = value;
        this.delayMs = delayMs;
        this.timestamp = System.currentTimeMillis() + delayMs;
        this.created = System.currentTimeMillis();
    }

    public DataDelayed(String value) {
        this.value = value;
        this.delayMs = 0L;
        this.timestamp = System.currentTimeMillis();
        this.created = System.currentTimeMillis();
    }

    DataDelayed update() {
        return new DataDelayed(this.value, this.delayMs, System.currentTimeMillis() + this.delayMs, this.created);
    }

    boolean hasDelay() {
        return this.delayMs > 0L;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long diff = timestamp - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return Long.compare(this.timestamp, ((DataDelayed) o).getTimestamp());
    }
}
