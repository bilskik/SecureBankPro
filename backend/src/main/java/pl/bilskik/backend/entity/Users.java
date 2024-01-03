package pl.bilskik.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.bilskik.backend.entity.Password;
import pl.bilskik.backend.entity.Transfer;
import pl.bilskik.backend.entity.embeded.Address;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users_tbl")
@Getter
@Setter
public class Users {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "users_id")
    private int userId;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "pesel", unique = true, nullable = false)
    private String pesel;

    @Column(name = "credit_cardno", unique = true, nullable = false)
    private String creditCardNo;

    @Column(name = "accountno", unique = true, nullable = false)
    private String accountNo;

    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "users_id")
    )
    private Set<String> roles;

    @Column(name = "is_locked")
    private boolean isLocked = false;

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
