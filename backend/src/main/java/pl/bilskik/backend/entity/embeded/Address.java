package pl.bilskik.backend.entity.embeded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    @Column(name = "country",
            nullable = false)
    private String country;
    @Column(name = "city",
            nullable = false)
    private String city;
    @Column(name = "Street",
            nullable = true)
    private String street;
    @Column(name = "apartment_no",
            nullable = false)
    private String apartmenetNo;
    @Column(name = "zip_code",
            nullable = false)
    private String zipCode;
}
