package com.currencyapi.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "currency")
@Getter
@Setter
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private CurrencyEnum name;

    private double buyRate;

    private double sellRate;

    @ManyToOne
    @JoinColumn(name = "market_id")
    private Market market;

    @OneToMany(mappedBy = "currency", cascade = CascadeType.PERSIST)
    private List<History> history;

    @Override
    public String toString() {
        return  "currency id=" + id +
                ", name=" + name +
                ", buyRate=" + buyRate +
                ", sellRate=" + sellRate +
                ", market=" + market.getName() + ".\n";
    }
}
