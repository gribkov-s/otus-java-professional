package ru.otus.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.otus.dto.ClientDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @SequenceGenerator(name = "client_gen", sequenceName = "client_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_gen")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "client")
    @Fetch(FetchMode.SELECT)
    private List<Phone> phones;

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
        this.address.setClient(this);
        for (Phone phone : this.phones) {
            phone.setClient(this);
        }
    }
    
    public Client(ClientDto dto) {
        List<String> phonesDto = Arrays.asList(dto.getPhones().split("\\s*,\\s*"));
        List<Phone> phones = phonesDto.stream()
                .map(ph -> new Phone(null, ph))
                .toList();
        this.id = null;
        this.name = dto.getName();
        this.address = new Address(null, dto.getAddress());
        this.phones = phones;
    }

    @Override
    @SuppressWarnings({"java:S2975", "java:S1182"})
    public Client clone() {
        var clientClone = new Client(this.id, this.name);
        if (this.address != null) {
            var addressClone = address.clone();
            addressClone.setClient(clientClone);
            clientClone.setAddress(addressClone);
        }
        if (this.phones != null) {
            clientClone.setPhones(new ArrayList<Phone>());
            for (Phone phone : this.phones) {
                var phoneClone = phone.clone();
                phoneClone.setClient(clientClone);
                clientClone.phones.add(phoneClone);
            }
        }
        return clientClone;
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
