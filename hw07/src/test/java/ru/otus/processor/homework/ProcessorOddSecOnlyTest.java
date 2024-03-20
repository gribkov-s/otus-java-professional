package ru.otus.processor.homework;

import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class ProcessorOddSecOnlyTest {

    @Test
    void succeedTest() {
        Message message = new Message.Builder(1).build();
        Clock clock = Clock.fixed(
                Instant.parse("1970-01-01T00:00:01.00Z"),
                ZoneId.systemDefault());
        ProcessorOddSecOnly processor = new ProcessorOddSecOnly(clock);

        assertThatCode(() -> processor.process(message))
                .doesNotThrowAnyException();
    }

    @Test
    void failedTest() {
        Message message = new Message.Builder(1).build();
        Clock clock = Clock.fixed(
                Instant.parse("1970-01-01T00:00:02.00Z"),
                ZoneId.systemDefault());
        ProcessorOddSecOnly processor = new ProcessorOddSecOnly(clock);

        assertThatThrownBy(() -> processor.process(message))
                .isInstanceOf(ProcessedInEvenSecException.class);
    }
}
