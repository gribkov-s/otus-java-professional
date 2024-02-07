package ru.otus.examples.delayqueue;

import java.util.concurrent.*;

public class DelayQueueExample {

    private static BlockingQueue<DataDelayed> queue = new DelayQueue<DataDelayed>();

    public static void main(String[] args) {

        var executor = Executors.newSingleThreadScheduledExecutor();

        queue.offer(new DataDelayed("a", 10000L));
        queue.offer(new DataDelayed("b", 5000L));
        queue.offer(new DataDelayed("c", 60000L));
        queue.offer(new DataDelayed("d", 30000L));
        queue.offer(new DataDelayed("e", 20000L));

        executor.scheduleAtFixedRate(
                () -> {
                    try {
                        readData();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    private static void readData() throws InterruptedException {
        DataDelayed data = queue.take();
        String value = data.getValue();
        Long created = data.getCreated();
        Long delayMs = data.getDelayMs();
        System.out.println(value + " " + (System.currentTimeMillis() - created) + " " + delayMs);
        queue.offer(data.update());
    }
}
