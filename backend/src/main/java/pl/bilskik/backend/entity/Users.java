package pl.bilskik.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.bilskik.backend.entity.Password;
import pl.bilskik.backend.entity.Transfer;
import pl.bilskik.backend.entity.embeded.Address;

import java.util.ArrayList;
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
    private long balance;
    @ManyToMany
    @JoinTable(
            name = "user_transfer_tbl",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "transfer_id")
    )
    private List<Transfer> transferList;
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Password> passwordList;

    public Users() {}

    public void addTransfer(Transfer transfer) {
        if(transferList == null) {
            transferList = new ArrayList<>();
        }
        transferList.add(transfer);
    }

}
