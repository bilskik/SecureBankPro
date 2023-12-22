package pl.bilskik.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.bilskik.backend.entity.Password;
import pl.bilskik.backend.entity.Transfer;
import pl.bilskik.backend.entity.embeded.Address;

import java.util.List;

@Entity
@Table(name = "users_tbl")
@Getter
@Setter
public class Users {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "users_id")
    private int userId;
    @Column(name = "username",
            unique = true)
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "pesel",
            unique = true)
    private String pesel;
    @Column(name = "credit_cardno")
    private String creditCardNo;
    @Column(name = "accountno",
            unique = true)
    private String accountNo;
    @Column(name = "balance")
    private int balance;
//    @Embedded
//    private Address address;
    @ManyToMany
    @JoinTable(
            name = "user_transfer_tbl",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "transfer_id")
    )
    private List<Transfer> transferList;
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Password> passwordList;

    public Users() {}

//    public Users(Long userId,
//                String username,
//                String password,
//                int pesel,
//                String creditCardNo,
//                int accountNo,
//                Long balance,
//                Address address,
//                List<Transfer> transferList
//    ) {
//        this.userId = userId;
//        this.username = username;
//        this.password = password;
//        this.pesel = pesel;
//        this.creditCardNo = creditCardNo;
//        this.accountNo = accountNo;
//        this.balance = balance;
//        this.address = address;
//        this.transferList = transferList;
//    }
}
