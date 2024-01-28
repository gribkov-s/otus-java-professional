package ru.otus.model;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import com.google.gson.JsonObject;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("message")
public class Message implements Persistable<String> {

    @Id
    private String id;

    @Nonnull
    @MappedCollection(idColumn = "template")
    private MessageTemplate template;

    @Nonnull
    private String content;

    @Transient
    private boolean isNew;

    @PersistenceCreator
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
