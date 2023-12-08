package pl.bilskik.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.bilskik.backend.entity.embeded.Address;

import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
    @Id
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "credit_cardno")
    private String creditCardNo;
    @Column(name = "accountno")
    private Long accountNo;
    @Column(name = "balance")
    private Long balance;
    @Embedded
    private Address address;
    @OneToMany
    private List<Transfer> transferList;
    public User() {

    }
}
