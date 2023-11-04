package ru.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.model.Client;
import ru.otus.model.Phone;
import java.util.Set;
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
        String nameDto = client.getName();
        String addressDto = client.getAddress().getStreet();
        Set<Phone> phonesEntity = client.getPhones();
        String phonesDto = phonesEntity.stream()
                .map(ph -> ph.getNumber())
                .collect(Collectors.joining(", "));
        this.name = nameDto;
        this.address = addressDto;
        this.phones = phonesDto;
    }
}
