package ru.otus.examples.asynchttpclient;

import org.asynchttpclient.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class HttpGetSyncExample {

    public static void main(String[] args) {

        String URL1 = "https://httpbin.org/delay/10";

        DefaultAsyncHttpClientConfig.Builder clientBuilder = Dsl.config();
        AsyncHttpClient client = Dsl.asyncHttpClient(clientBuilder);

        BoundRequestBuilder getRequest = client.prepareGet(URL1);

        for (int i = 0; i < 1000; i++) {
            Future<Response> responseFuture = getRequest.execute();
            try {
                Response response = responseFuture.get();
                System.out.println(
                        "RESULT: " +
                                response.getStatusCode() +
                                " - " + System.currentTimeMillis() / 1000);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
