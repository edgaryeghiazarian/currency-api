package com.currencyapi.demo.entity;

import jakarta.persistence.*;

@Entity
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Currencies name;

    @ManyToOne
    @JoinColumn(name = "market_id")
    private Market market;
}
