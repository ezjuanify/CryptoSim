package io.juanez.cryptosim.entity;

import javax.persistence.*;

@Entity
@Table(name = "USERS")
public class UsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    private Double usdt;

    @Column(nullable = false)
    private Double btc;

    @Column(nullable = false)
    private Double eth;

    public UsersEntity() {}

    public UsersEntity(String name, Double usdt, Double btc, Double eth) {
        this.name = name;
        this.usdt = usdt;
        this.btc = btc;
        this.eth = eth;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public Double getUsdt() { return usdt; }
    public Double getBtc() { return btc; }
    public Double getEth() { return eth; }
    public void setName(String name) { this.name = name; }
    public void setUsdt(Double usdt) { this.usdt = usdt; }
    public void setBtc(Double btc) { this.btc = btc; }
    public void setEth(Double eth) { this.eth = eth; }
}
