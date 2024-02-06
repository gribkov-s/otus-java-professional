package ru.otus.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.domain.Persistable;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "connection_template")
public class ConnectionTemplate implements Persistable<String> {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "fields")
    @Nonnull
    @JdbcTypeCode(SqlTypes.JSON)
    private Set<EntityField> fields;

    @Transient
    private boolean isNew;

    public ConnectionTemplate(String id, Set<EntityField> fields) {
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
