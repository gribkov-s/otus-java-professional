package ru.otus.examples.completablefuture;

import java.util.concurrent.*;

public class CompletableFutureExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        var executor = Executors.newFixedThreadPool(3);

        var dataCollection = new Data[]{
                new Data("a", 10L),
                new Data("b"),
                new Data("c", 3L),
                new Data("d", 25L),
                new Data("e", 30L),
                new Data("f", 17L),
                new Data("g", 8L),
                new Data("h", 1L),
                new Data("i", 20L),
                new Data("j", 60L),
                new Data("k", 45L)
        };

        executor.submit(() -> print(dataCollection));
    }

    private static void print(Data[] dataCollection) {
        for (Data data : dataCollection) {
            var executor = CompletableFuture.delayedExecutor(data.getDelay(), TimeUnit.SECONDS);
            String value = data.getValue();
            Long ts = data.getTimestamp() / 1000;
            executor.execute(() ->
                    System.out.println(value + " " + (System.currentTimeMillis() / 1000 - ts)));
        }
    }
}
