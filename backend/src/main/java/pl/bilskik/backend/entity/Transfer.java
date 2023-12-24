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
    @Column(name = "sender_name")
    private String senderName;
    @Column(name = "sender_accno")
    private String senderAccNo;
    @Column(name = "receiver_name")
    private String receiverName;
    @Column(name = "receiver_accno")
    private String receiverAccNo;
    @Column(name = "balance")
    private long balance;
    @ManyToMany(
            mappedBy = "transferList",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    private List<Users> user; //0 fromUser //1 toUser

    public Transfer() {}

    public Transfer(int transferId,
                    String transferTitle,
                    String senderName,
                    String senderAccNo,
                    String receiverName,
                    String receiverAccNo,
                    long balance,
                    List<Users> user) {
        this.transferId = transferId;
        this.transferTitle = transferTitle;
        this.senderName = senderName;
        this.senderAccNo = senderAccNo;
        this.receiverName = receiverName;
        this.receiverAccNo = receiverAccNo;
        this.balance = balance;
        this.user = user;
    }
}

