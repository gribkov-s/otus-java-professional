package ru.otus.service.taskhandler.interpreter;

import org.asynchttpclient.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.otus.model.Connection;
import ru.otus.model.Parameters;
import ru.otus.model.Task;
import java.util.HashMap;

@Component
public class HttpGetTaskInterpreter implements TaskInterpreter<BoundRequestBuilder> {
    private static final Logger log = LoggerFactory.getLogger(HttpGetTaskInterpreter.class);

    private final DefaultAsyncHttpClientConfig.Builder clientBuilder = Dsl.config().setConnectionTtl(3);
    private final AsyncHttpClient client = Dsl.asyncHttpClient(clientBuilder);

    @Override
    public BoundRequestBuilder interpret(Task task) {
        Connection connection = task.getConnection();
        Parameters parameters = task.getParameters();
        HashMap<String, Object> paramsContent = parameters.getContent();
        String url = connection.getFromContent("urlWithPathParams", String.class);

        for (var entry : paramsContent.entrySet()) {
            String k = entry.getKey();
            String v = entry.getValue().toString();
            url = url.replace(String.format("{%s}", k), v);
        }
;
        return client.prepareGet(url);
    }
}
