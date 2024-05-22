package com.currencyapi.demo.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "history")
@Getter
@Setter
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;

    private double buyRate;

    private double sellRate;

    private LocalDateTime timestamp;

    @Override
    public String toString() {
        return "History:" +
                "id=" + id +
                ", currency=" + currency +
                ", buyRate=" + buyRate +
                ", sellRate=" + sellRate +
                ", timestamp=" + timestamp +
                '.';
    }
}
