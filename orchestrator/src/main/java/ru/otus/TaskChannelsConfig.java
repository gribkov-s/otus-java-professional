package ru.otus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.otus.service.taskchannel.TaskChannel;
import ru.otus.service.taskchannel.TaskChannelImpl;

@Configuration
public class TaskChannelsConfig {
    @Bean
    public TaskChannel inputTaskChannel() {
        return new TaskChannelImpl("Input task channel");
    }

    @Bean
    public TaskChannel handledTaskChannel() {
        return new TaskChannelImpl("Handled task channel");
    }

    @Bean
    public TaskChannel nextTaskChannel() {
        return new TaskChannelImpl("Next task channel");
    }
}
