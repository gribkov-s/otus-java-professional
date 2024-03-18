package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.Clock;

public class ProcessorOddSecOnly implements Processor {
    private Clock clock;

    public ProcessorOddSecOnly(Clock clock) {
        this.clock = clock;
    }

    public ProcessorOddSecOnly() {
        this.clock = Clock.systemDefaultZone();
    }

    @Override
    public Message process(Message message) {
        long currentSec = clock.instant().getEpochSecond();
        if (currentSec % 2 == 0) {
            throw new ProcessedInEvenSecException("Can not process message in an even second");
        } else {
            System.out.println(currentSec + " - " + message.toString());
        }
        return message.copy();
    }
}
