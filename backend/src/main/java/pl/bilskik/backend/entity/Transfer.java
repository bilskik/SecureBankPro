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
    @Column(name = "amount")
    private long amount;
    @ManyToMany(mappedBy = "transferList")
    private List<Users> user; //0 fromUser //1 toUser

    public Transfer() {}
    public Transfer(int transferId, String transferTitle, long amount, List<Users> user) {
        this.transferId = transferId;
        this.transferTitle = transferTitle;
        this.amount = amount;
        this.user = user;
    }
}

