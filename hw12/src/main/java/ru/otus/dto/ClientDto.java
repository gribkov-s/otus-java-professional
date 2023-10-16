package ru.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.model.Client;
import ru.otus.model.Phone;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    private String name;
    private String address;
    private String phones;

    public ClientDto(Client client) {
        Client clientClone = client.clone();
        String name = clientClone.getName();
        String address = clientClone.getAddress().getStreet();
        List<Phone> phonesEntity = clientClone.getPhones();
        String phones = phonesEntity.stream()
                .map(ph -> ph.getNumber())
                .collect(Collectors.joining(", "));
        this.name = name;
        this.address = address;
        this.phones = phones;
    }
}
