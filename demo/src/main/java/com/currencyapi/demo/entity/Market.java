package com.currencyapi.demo.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "markets")
@Getter
@Setter
public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(mappedBy = "market", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Currency> currencyList;
}
