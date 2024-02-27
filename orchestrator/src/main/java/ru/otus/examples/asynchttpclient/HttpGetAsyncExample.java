package ru.otus.examples.asynchttpclient;

import org.asynchttpclient.*;

import java.util.concurrent.*;

public class HttpGetAsyncExample {

    public static void main(String[] args) throws InterruptedException {

        var executors =  Executors.newCachedThreadPool();

        String URL1 = "https://httpbin.org/delay/10";

        DefaultAsyncHttpClientConfig.Builder clientBuilder = Dsl.config()
                        .setConnectionTtl(3);

        AsyncHttpClient client = Dsl.asyncHttpClient(clientBuilder);

        BoundRequestBuilder getRequest = client.prepareGet(URL1);

        for (int i = 0; i < 1000; i++) {
            ListenableFuture<String> responseFuture = getRequest.execute(new AsyncHandlerExample());
            int finalI = i;
            responseFuture.addListener(
                    () -> {
                        try {
                            String response = responseFuture.get();
                            System.out.println(finalI + " - " + response);
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    },
                    executors);
            //Thread.sleep(1000);
        }

    }
}
