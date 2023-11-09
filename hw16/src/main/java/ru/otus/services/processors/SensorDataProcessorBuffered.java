package ru.otus.services.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.SensorDataProcessor;
import ru.otus.api.model.SensorData;
import ru.otus.lib.SensorDataBufferedWriter;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

// Этот класс нужно реализовать
public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorBuffered.class);

    private final SensorDataBufferedWriter writer;
    private final BlockingQueue<SensorData> dataBuffer;

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.writer = writer;
        this.dataBuffer = new ArrayBlockingQueue<>(bufferSize);
    }

    @Override
    public void process(SensorData data) {
        boolean pushResult = dataBuffer.offer(data);
        if (!pushResult) {
            flush();
        }
    }

    public void flush() {
        try {
            ArrayList<SensorData> data = new ArrayList<>();
            dataBuffer.drainTo(data);
            data.sort(new SensorDataComparator());
            writer.writeBufferedData(data);
        } catch (Exception e) {
            log.error("Ошибка в процессе записи буфера", e);
        }
    }

    @Override
    public void onProcessingEnd() {
        flush();
    }

    private static class SensorDataComparator implements Comparator<SensorData> {
        public int compare(SensorData data1, SensorData data2) {
            LocalDateTime time1 = data1.getMeasurementTime();
            LocalDateTime time2 = data2.getMeasurementTime();
            return time1.compareTo(time2);
        }
    }
}
