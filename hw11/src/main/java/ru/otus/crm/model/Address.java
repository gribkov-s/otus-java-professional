package ru.otus.crm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address implements Cloneable {

    @Id
    @SequenceGenerator(name = "address_gen", sequenceName = "address_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_gen")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "street")
    private String street;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "address")
    private Client client;

    public Address(String street) {
        this.id = null;
        this.street = street;
    }

    public Address(Long id, String street) {
        this.id = id;
        this.street = street;
    }

    @Override
    @SuppressWarnings({"java:S2975", "java:S1182"})
    public Address clone() {
        return new Address(this.getId(), this.getStreet());
    }
}
