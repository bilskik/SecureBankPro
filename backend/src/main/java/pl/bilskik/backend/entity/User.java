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
    @Column(name = "login",
            unique = true)
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "pesel",
            unique = true)
    private int pesel;
    @Column(name = "credit_cardno")
    private String creditCardNo;
    @Column(name = "accountno",
            unique = true)
    private int accountNo;
    @Column(name = "balance")
    private Long balance;
    @Embedded
    private Address address;
    @OneToMany
    private List<Transfer> transferList;

    public User() {}

    public User(Long userId,
                String login,
                String password,
                int pesel,
                String creditCardNo,
                int accountNo,
                Long balance,
                Address address,
                List<Transfer> transferList
    ) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.pesel = pesel;
        this.creditCardNo = creditCardNo;
        this.accountNo = accountNo;
        this.balance = balance;
        this.address = address;
        this.transferList = transferList;
    }
}
