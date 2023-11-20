package ru.otus;

import io.grpc.ServerBuilder;
import ru.otus.service.NumbersServiceImpl;
import ru.otus.service.RemoteNumbersServiceImpl;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"squid:S106"})
public class GRPCServer {
    private static final Logger logger = LoggerFactory.getLogger(GRPCServer.class);
    public static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws IOException, InterruptedException {

        var numbersService = new NumbersServiceImpl();
        var remoteNumbersService = new RemoteNumbersServiceImpl(numbersService);

        var server =
                ServerBuilder.forPort(SERVER_PORT).addService(remoteNumbersService).build();
        server.start();
        logger.info("server waiting for client connections...");
        server.awaitTermination();
    }
}
