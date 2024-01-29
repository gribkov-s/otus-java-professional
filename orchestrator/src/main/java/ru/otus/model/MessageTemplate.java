package ru.otus.model;

import jakarta.annotation.Nonnull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.PersistenceCreator;
import jakarta.persistence.*;
import com.google.gson.JsonObject;
import org.springframework.data.domain.Persistable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "message_template")
public class MessageTemplate implements Persistable<String> {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "fields")
    @Nonnull
    @JdbcTypeCode(SqlTypes.JSON)
    private String fields;

    @Transient
    private boolean isNew;

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
