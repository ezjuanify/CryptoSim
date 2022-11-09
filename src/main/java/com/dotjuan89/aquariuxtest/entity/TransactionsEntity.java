package com.dotjuan89.aquariuxtest.entity;

import ch.qos.logback.classic.db.names.ColumnName;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "TRANSACTIONS")
public class TransactionsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usersId", referencedColumnName = "id")
    private UsersEntity users;
    private String currency;
    private String operation;
    private Double amount;
    private Timestamp ts;

    public TransactionsEntity() {}
    public TransactionsEntity(UsersEntity users, String currency, String operation, Double amount) {
        this.users = users;
        this.currency = currency;
        this.operation = operation;
        this.amount = amount;

        Date d = new Date();
        this.ts = new Timestamp(d.getTime());
    }

    public Integer getId() { return id; }
    public UsersEntity getUsers() { return users; }
    public String getCurrency() { return currency; }
    public String getOperation() { return operation; }
    public Double getAmount() { return amount; }
    public Timestamp getTs() { return ts; }
}
