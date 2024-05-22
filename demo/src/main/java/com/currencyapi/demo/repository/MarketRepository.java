package com.currencyapi.demo.repository;

import com.currencyapi.demo.entity.Market;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketRepository extends JpaRepository<Market, Long> {
}
