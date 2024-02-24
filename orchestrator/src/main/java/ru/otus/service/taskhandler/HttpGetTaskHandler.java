package ru.otus.service.taskhandler;

import org.asynchttpclient.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.otus.model.Task;
import ru.otus.service.taskchannel.TaskChannel;
import ru.otus.service.taskhandler.interpreter.TaskInterpreter;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class HttpGetTaskHandler extends TaskHandler<BoundRequestBuilder> {
    private static final Logger log = LoggerFactory.getLogger(HttpGetTaskHandler.class);

    private final ExecutorService handleThreadPool =  Executors.newFixedThreadPool(3);
    private final ExecutorService sendThreadPool =  Executors.newFixedThreadPool(1);

    private final TaskChannel handledTaskChannel;

    @Autowired
    public HttpGetTaskHandler(TaskChannel handledTaskChannel,
                              @Qualifier("httpGetTaskInterpreter")
                                      TaskInterpreter<BoundRequestBuilder> interpreter) {
        this.handledTaskChannel = handledTaskChannel;
        this.interpreter = interpreter;
    }

    @Override
    public void handle(Task task) {
        BoundRequestBuilder getRequest = interpreter.interpret(task);
        ListenableFuture<String> responseFuture = getRequest.execute(new AsyncHttpResponseHandler());

        responseFuture.addListener(
                () -> {
                    try {
                        String response = responseFuture.get();
                        log.info("Handled task {}, result\n{}",
                                task.getId(),
                                response);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        log.warn("Failed task {} because of InterruptedException: \n{}",
                                task.getId(),
                                e.getMessage());
                    } catch (ExecutionException e) {
                        log.warn("Failed task {} because of ExecutionException:\n{}",
                                task.getId(),
                                e.getMessage());
                    }
                },
                handleThreadPool);

        sendThreadPool.submit(() -> handledTaskChannel.push(task));
    }

    private static class AsyncHttpResponseHandler extends AsyncCompletionHandler<String> {
        @Override
        public String onCompleted(Response response) throws Exception {
            return response.getResponseBody();
        }
    }
}
