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
    @Column(name = "receiver_name")
    private String receiverName;
    @Column(name = "amount")
    private Long amount;
    @ManyToMany(mappedBy = "transferList")
    private List<Users> user; //0 fromUser //1 toUser

    public Transfer() {

    }

//    public Transfer(Long transferId, String transferTitle, String receiverName, Long amount, User fromUser, User toUser) {
//        this.transferId = transferId;
//        this.transferTitle = transferTitle;
//        this.receiverName = receiverName;
//        this.amount = amount;
//        this.fromUser = fromUser;
//        this.toUser = toUser;
//    }
}

