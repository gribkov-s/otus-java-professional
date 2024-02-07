package ru.otus.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.domain.Persistable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "task_template")
public class TaskTemplate implements Persistable<String> {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "task_type")
    @Nonnull
    @Enumerated(EnumType.STRING)
    private TaskType taskType;

    @JoinColumn(name = "connection", nullable = false)
    @Nonnull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Connection connection;

    @JoinColumn(name = "parameters_template", nullable = false)
    @Nonnull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ParametersTemplate parametersTemplate;

    @JoinColumn(name = "message_template", nullable = false)
    @Nonnull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MessageTemplate messageTemplate;

    @Transient
    private boolean isNew;

    public TaskTemplate(String id,
                        TaskType taskType,
                        Connection connection,
                        ParametersTemplate parametersTemplate,
                        MessageTemplate messageTemplate) {
        this.id = id;
        this.taskType = taskType;
        this.connection = connection;
        this.parametersTemplate = parametersTemplate;
        this.messageTemplate = messageTemplate;
        this.isNew = false;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }
}
