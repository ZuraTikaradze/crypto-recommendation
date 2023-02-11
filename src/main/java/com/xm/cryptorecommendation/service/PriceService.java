package com.xm.cryptorecommendation.service;


import com.xm.cryptorecommendation.entity.CryptoEntity;

import java.math.BigDecimal;
import java.time.Instant;

public interface PriceService {
    BigDecimal getMaxPrice(CryptoEntity crypto, Instant from, Instant to);

    BigDecimal getMinPrice(CryptoEntity crypto, Instant from, Instant to);

    BigDecimal getOldestPrice(CryptoEntity crypto, Instant from, Instant to);

    BigDecimal getNewestPrice(CryptoEntity crypto, Instant from, Instant to);
}
