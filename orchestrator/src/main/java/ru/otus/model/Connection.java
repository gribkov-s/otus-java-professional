package ru.otus.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;
import org.springframework.data.domain.Persistable;

import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "connection")
public class Connection implements Persistable<String> {

    @Id
    @Column(name = "id")
    private String id;

    @JoinColumn(name = "template", nullable = false)
    @Nonnull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ConnectionTemplate template;

    @Column(name = "content")
    @Nonnull
    @JdbcTypeCode(SqlTypes.JSON)
    private HashMap<String, Object> content;

    @Transient
    private boolean isNew;

    public Connection(String id, ConnectionTemplate template, HashMap<String, Object> content) {
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

    public Connection updateContent(ConnectionContent connectionContent) {
        HashMap<String, Object> newContent = connectionContent.getContent();
        return new Connection(this.getId(), this.getTemplate(), newContent);
    }

    public <T> T getFromContent(String name, Class<T> clazz) {
        try {
            Object obj = this.content.getOrDefault(name, null);
            return clazz.cast(obj);
        } catch (ClassCastException e) {
            return null;
        }
    }
}
