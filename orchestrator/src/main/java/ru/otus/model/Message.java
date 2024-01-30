package ru.otus.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.domain.Persistable;
import jakarta.persistence.*;
import com.google.gson.JsonObject;

import java.util.HashMap;

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
    @JdbcTypeCode(SqlTypes.JSON)
    private HashMap<String, Object> content;

    @Transient
    private boolean isNew;

    public Message(String id, MessageTemplate template, HashMap<String, Object> content) {
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

    public Message updateContent(MessageContent messageContent) throws JSONException {
        HashMap<String, Object> newContent = messageContent.getContent();
        return new Message(this.getId(), this.getTemplate(), newContent);
    }

    Gson gson = new Gson();
    JsonElement jsonElement = gson.toJsonTree(map);
    MyPojo pojo = gson.fromJson(jsonElement, MyPojo.class);
}
