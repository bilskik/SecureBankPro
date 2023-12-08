package pl.bilskik.backend.entity.embeded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    @Column(name = "city")
    private String city;
    @Column(name = "Street")
    private String street;
    @Column(name = "apartment_no")
    private String apartmenetNo;
    @Column(name = "zip_code")
    private String zipCode;
}
