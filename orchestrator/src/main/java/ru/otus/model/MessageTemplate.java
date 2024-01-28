package ru.otus.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;
import com.google.gson.JsonObject;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("message_template")
public class MessageTemplate implements Persistable<String> {

    @Id
    private String id;

    @Nonnull
    private String fields;

    @Transient
    private boolean isNew;

    @PersistenceCreator
    public MessageTemplate(String id, String fields) {
        this.id = id;
        this.fields = fields;
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
