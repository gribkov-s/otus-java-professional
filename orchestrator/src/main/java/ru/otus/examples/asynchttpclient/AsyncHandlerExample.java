package ru.otus.examples.asynchttpclient;

import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.Response;

public class AsyncHandlerExample extends AsyncCompletionHandler<String> {
    @Override
    public String onCompleted(Response response) throws Exception {
        return
                "RESULT: " +
                        response.getStatusCode() +
                        " - " + System.currentTimeMillis() / 1000;
    }
}
