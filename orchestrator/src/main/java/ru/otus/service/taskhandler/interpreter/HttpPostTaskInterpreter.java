package ru.otus.service.taskhandler.interpreter;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.BoundRequestBuilder;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.request.body.multipart.Part;
import org.asynchttpclient.request.body.multipart.StringPart;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.otus.model.Connection;
import ru.otus.model.Message;
import ru.otus.model.Task;

import java.util.HashMap;

@Component
@Qualifier("httpPostTaskInterpreter")
public class HttpPostTaskInterpreter implements TaskInterpreter<BoundRequestBuilder> {
    private static final Logger log = LoggerFactory.getLogger(HttpPostTaskInterpreter.class);

    private final DefaultAsyncHttpClientConfig.Builder clientBuilder = Dsl.config().setConnectionTtl(3);
    private final AsyncHttpClient client = Dsl.asyncHttpClient(clientBuilder);

    @Override
    public BoundRequestBuilder interpret(Task task) {
        Connection connection = task.getConnection();
        Message message = task.getMessage();
        HashMap<String, Object> messageContent = message.getContent();

        String url = connection.getFromContent("url", String.class);
        String contentType = connection.getFromContent("contentType", String.class);

        BoundRequestBuilder postRequest = client.preparePost(url)
                .addHeader("Content-Type", contentType);

        JSONObject body = new JSONObject(messageContent);

        return postRequest.setBody(body.toString());
    }
}
