package com.xm.cryptorecommendation.service.impl;

import com.xm.cryptorecommendation.entity.CryptoEntity;
import com.xm.cryptorecommendation.repository.PriceRepository;
import com.xm.cryptorecommendation.service.PriceService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Service
public class PriceServiceImpl implements PriceService {

    private static final String PRICE = "price";
    private static final String PRICE_TIMESTAMP = "priceTimestamp";

    private final PriceRepository priceRepository;

    public PriceServiceImpl(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public BigDecimal getMaxPrice(CryptoEntity crypto, Instant from, Instant to) {
        return priceRepository.findFirstByCryptoAndPriceTimestampBetween(crypto, from, to, Sort.by(PRICE).descending()).getPrice();
    }

    @Override
    public BigDecimal getMinPrice(CryptoEntity crypto, Instant from, Instant to) {
        return priceRepository.findFirstByCryptoAndPriceTimestampBetween(crypto, from, to, Sort.by(PRICE).ascending()).getPrice();
    }

    @Override
    public BigDecimal getOldestPrice(CryptoEntity crypto, Instant from, Instant to) {
        return priceRepository.findFirstByCryptoAndPriceTimestampBetween(crypto, from, to, Sort.by(PRICE_TIMESTAMP).ascending()).getPrice();
    }

    @Override
    public BigDecimal getNewestPrice(CryptoEntity crypto, Instant from, Instant to) {
        return priceRepository.findFirstByCryptoAndPriceTimestampBetween(crypto, from, to, Sort.by(PRICE_TIMESTAMP).descending()).getPrice();
    }
}
