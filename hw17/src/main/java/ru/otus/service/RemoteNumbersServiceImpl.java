package ru.otus.service;

import io.grpc.stub.StreamObserver;
import ru.otus.NumRangeMessage;
import ru.otus.NumberMessage;
import ru.otus.RemoteNumbersServiceGrpc;
import java.util.List;

public class RemoteNumbersServiceImpl
        extends RemoteNumbersServiceGrpc.RemoteNumbersServiceImplBase {

    private final NumbersService numberService;

    public RemoteNumbersServiceImpl(NumbersService numberService) {
        this.numberService = numberService;
    }

    @Override
    public void getByRange(NumRangeMessage request,
                           StreamObserver<NumberMessage> responseObserver) {
        List<Long> numbers = numberService.getByRange(
                request.getFrom(),
                request.getTo());
        numbers.forEach(num -> {
            try {
                Thread.sleep(2000); ///
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            responseObserver.onNext(longToNumberMessage(num));
        });
        responseObserver.onCompleted();
    }

    private NumberMessage longToNumberMessage(Long num) {
        return NumberMessage.newBuilder()
                .setNum(num)
                .build();
    }
}
