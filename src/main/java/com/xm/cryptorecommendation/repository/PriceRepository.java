package com.xm.cryptorecommendation.repository;

import com.xm.cryptorecommendation.entity.CryptoEntity;
import com.xm.cryptorecommendation.entity.PriceEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface PriceRepository extends JpaRepository<PriceEntity, Long> {
    PriceEntity findFirstByCryptoAndPriceTimestampBetween(CryptoEntity crypto, Instant from, Instant to, Sort order);
}
