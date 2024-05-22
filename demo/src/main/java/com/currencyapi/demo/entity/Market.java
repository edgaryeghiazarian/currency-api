package com.currencyapi.demo.entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Currency> currencyList;
}
