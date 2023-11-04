package ru.otus.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import jakarta.annotation.Nonnull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import ru.otus.dto.ClientDto;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Table("client")
public class Client implements Persistable<Long> {

    @Id
    private Long id;

    @Nonnull
    private String name;

    @Nonnull
    @MappedCollection(idColumn = "client_id")
    private Address address;

    @Nonnull
    @MappedCollection(idColumn = "client_id")
    private Set<Phone> phones;

    @Transient
    private final boolean isNew;

    @PersistenceCreator
    public Client(Long id, String name, Address address, Set<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
        this.isNew = false;
    }

    public Client(String name) {
        this.name = name;
        this.isNew = true;
    }

    public Client (ClientDto clientDto) {
        Address addressEntity = new Address(clientDto.getAddress());
        List<String> phonesDto = Arrays.asList(clientDto.getPhones().split("\\s*,\\s*"));
        Set<Phone> phonesEntity = phonesDto.stream()
                .map(ph -> new Phone(ph))
                .collect(Collectors.toSet());
        this.name = clientDto.getName();
        this.address = addressEntity;
        this.phones = phonesEntity;
        this.isNew = true;
    }

    public void setId(Long id) {
        this.id = id;
        this.address.setClientId(id);
        Set<Phone> phonesNew = this.phones.stream()
                .map(ph -> new Phone(null, ph.getNumber(), id))
                .collect(Collectors.toSet());
        this.phones = phonesNew;
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name='" + name + '\'' + '}';
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }
}
