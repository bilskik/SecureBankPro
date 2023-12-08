package pl.bilskik.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "transfer")
@Getter
@Setter
public class Transfer {
    @Id
    @Column(name = "transfer_id")
    private Long transferId;
    @Column(name = "title")
    private String transferTitle;
    @Column(name = "title")
    private String tran;
    @Column(name = "amount")
    private Long amount;
    @ManyToOne
    private User fromUser;
    @ManyToOne
    private User toUser;

    public Transfer() {}

}

