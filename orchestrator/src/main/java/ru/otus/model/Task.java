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
import ru.otus.examples.delayqueue.DataDelayed;

import java.util.Optional;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "task")
public class Task implements Persistable<String>, Delayed {

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
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Connection connection;

    @JoinColumn(name = "parameters", nullable = false)
    @Nonnull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Parameters parameters;

    @JoinColumn(name = "message", nullable = false)
    @Nonnull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Message message;

    @Column(name = "range_sec")
    @Nonnull
    private Long rangeSec;

    @JoinColumn(name = "next", nullable = true)
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Task next;

    @Transient
    private boolean isNew;

    @Transient
    private long timestamp;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    public Task(String id,
                TaskType taskType,
                Connection connection,
                Parameters parameters,
                Message message,
                Long rangeSec) {
        this.id = id;
        this.taskType = taskType;
        this.connection = connection;
        this.parameters = parameters;
        this.message = message;
        this.rangeSec = rangeSec;
        this.next = null;
        this.isNew = false;
        this.timestamp = System.currentTimeMillis() + rangeSec * 1000;
    }

    public String getTaskTypeTitle() {
        return taskType.getTitle();
    }

    public Optional<Task> getNextOpt() {
        if (this.next != null) {
            return Optional.of(this.next.updateTimestamp());
        }
        else {
            if (rangeSec > 0) {
                return Optional.of(this.updateTimestamp());
            } else {
                return Optional.empty();
            }
        }
    }

    public boolean isRepetitive() {
        return rangeSec > 0;
    }

    public Task updateRange(TaskRange taskRange) {
        return new Task(this.id,
                        this.taskType,
                        this.connection,
                        this.parameters,
                        this.message,
                        taskRange.getRangeSec(),
                        this.next,
                        this.isNew,
                        this.timestamp);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long diff = timestamp - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return Long.compare(this.timestamp, ((DataDelayed) o).getTimestamp());
    }


    private Task updateTimestamp() {
        long currentTime = System.currentTimeMillis() + this.getRangeSec() * 1000;
        return new Task(this.getId(),
                        this.getTaskType(),
                        this.getConnection(),
                        this.getParameters(),
                        this.getMessage(),
                        this.getRangeSec(),
                        this.getNext(),
                        false,
                        currentTime);
    }
}
