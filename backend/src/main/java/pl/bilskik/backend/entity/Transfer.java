package pl.bilskik.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "transfer_tbl")
@Getter
@Setter
public class Transfer {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "transfer_id")
    private int transferId;
    @Column(name = "transfer_title")
    private String transferTitle;
    @Column(name = "balance")
    private long balance;
    @ManyToMany(
            mappedBy = "transferList",
            cascade = { CascadeType.PERSIST, CascadeType.MERGE }
    )
    private List<Users> user; //0 fromUser //1 toUser

    public Transfer() {}
    public Transfer(int transferId, String transferTitle, long balance, List<Users> user) {
        this.transferId = transferId;
        this.transferTitle = transferTitle;
        this.balance = balance;
        this.user = user;
    }
}

