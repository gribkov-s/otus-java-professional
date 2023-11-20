package ru.otus;

import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.atomic.AtomicLong;

@SuppressWarnings({"squid:S106", "squid:S2142"})
public class GRPCClient {
    private static final Logger logger = LoggerFactory.getLogger(GRPCClient.class);

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;

    private static Long localCounter = 0L;
    private static final AtomicLong remoteCounter = new AtomicLong(0);

    public static void main(String[] args) {
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        var newStub = RemoteNumbersServiceGrpc.newStub(channel);

        var rangeMessage = NumRangeMessage.newBuilder()
                .setFrom(0)
                .setTo(30)
                .build();

        var observer = new StreamObserver<NumberMessage>() {
            @Override
            public void onNext(NumberMessage nm) {
                var newValue = remoteCounter.updateAndGet(v -> nm.getNum());
                logger.info("remote counter: {}", newValue);
            }

            @Override
            public void onError(Throwable t) {
                logger.error("remote counter error", t);
            }

            @Override
            public void onCompleted() {
                logger.info("remote counter is over");
            }
        };

        newStub.getByRange(rangeMessage, observer);

        for (int i = 0; i <= 50; i++) {
            try {
                Thread.sleep(1000); ///
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            localCounter = localCounter + remoteCounter.getAndSet(0) + 1;
            logger.info("local counter: {}", localCounter);
        }

        channel.shutdown();
    }
}
