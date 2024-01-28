package ru.otus.model;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.domain.Persistable;
import jakarta.persistence.*;
import com.google.gson.JsonObject;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "message")
public class Message implements Persistable<String> {

    @Id
    @Column(name = "id")
    private String id;

    @JoinColumn(name = "template", nullable = false)
    @Nonnull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MessageTemplate template;

    @Column(name = "content")
    @Nonnull
    private String content;

    @Transient
    private boolean isNew;

    public Message(String id, MessageTemplate template, String content) {
        this.id = id;
        this.template = template;
        this.content = content;
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
