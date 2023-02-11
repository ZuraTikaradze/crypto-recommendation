package com.xm.cryptorecommendation.service.impl;

import com.xm.cryptorecommendation.entity.CryptoEntity;
import com.xm.cryptorecommendation.entity.PriceEntity;
import com.xm.cryptorecommendation.repository.PriceRepository;
import com.xm.cryptorecommendation.service.PriceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;

@SpringBootTest
class PriceServiceImplTest {

    @Autowired
    PriceService priceService;

    @MockBean
    PriceRepository priceRepository;


    @Test
    void getMaxPrice() {

        final BigDecimal expectedPrice = new BigDecimal(10);
        BigDecimal actualPrice;

        LocalDate from = LocalDate.parse("2020-01-08");
        LocalDate to = LocalDate.parse("2023-01-08");

        Instant fromInstant = localDateToUTCInstant(from);
        Instant toInstant = localDateToUTCInstant(to);


        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setPrice(new BigDecimal(10));

        Set<PriceEntity> priceEntities = new HashSet<>();
        priceEntities.add(priceEntity);

        CryptoEntity crypto = new CryptoEntity();
        crypto.setName("DOGE");
        crypto.setPrices(priceEntities);

        when(priceRepository.findFirstByCryptoAndPriceTimestampBetween(crypto, fromInstant, toInstant, Sort.by("price").descending()))
                .thenReturn(priceEntity);

        actualPrice = priceService.getMaxPrice(crypto, fromInstant, toInstant);

        Assertions.assertEquals(expectedPrice, actualPrice);

    }

    @Test
    void getMinPrice() {

        final BigDecimal expectedPrice = new BigDecimal(10);
        BigDecimal actualPrice;

        LocalDate from = LocalDate.parse("2020-01-08");
        LocalDate to = LocalDate.parse("2023-01-08");

        Instant fromInstant = localDateToUTCInstant(from);
        Instant toInstant = localDateToUTCInstant(to);

        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setPrice(new BigDecimal(10));

        Set<PriceEntity> priceEntities = new HashSet<>();
        priceEntities.add(priceEntity);

        CryptoEntity crypto = new CryptoEntity();
        crypto.setName("DOGE");
        crypto.setPrices(priceEntities);

        when(priceRepository.findFirstByCryptoAndPriceTimestampBetween(crypto, fromInstant, toInstant, Sort.by("price").ascending()))
                .thenReturn(priceEntity);

        actualPrice = priceService.getMinPrice(crypto, fromInstant, toInstant);

        Assertions.assertEquals(expectedPrice, actualPrice);
    }

    @Test
    void getOldestPrice() {

        final BigDecimal expectedPrice = new BigDecimal(10);
        BigDecimal actualPrice;

        LocalDate from = LocalDate.parse("2020-01-08");
        LocalDate to = LocalDate.parse("2023-01-08");

        Instant fromInstant = localDateToUTCInstant(from);
        Instant toInstant = localDateToUTCInstant(to);

        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setPrice(new BigDecimal(10));

        Set<PriceEntity> priceEntities = new HashSet<>();
        priceEntities.add(priceEntity);

        CryptoEntity crypto = new CryptoEntity();
        crypto.setName("DOGE");
        crypto.setPrices(priceEntities);

        when(priceRepository.findFirstByCryptoAndPriceTimestampBetween(crypto, fromInstant, toInstant, Sort.by("priceTimestamp").ascending()))
                .thenReturn(priceEntity);

        actualPrice = priceService.getOldestPrice(crypto, fromInstant, toInstant);

        Assertions.assertEquals(expectedPrice, actualPrice);
    }

    @Test
    void getNewestPrice() {

        final BigDecimal expectedPrice = new BigDecimal(10);
        BigDecimal actualPrice;

        LocalDate from = LocalDate.parse("2020-01-08");
        LocalDate to = LocalDate.parse("2023-01-08");

        Instant fromInstant = localDateToUTCInstant(from);
        Instant toInstant = localDateToUTCInstant(to);

        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setPrice(new BigDecimal(10));

        Set<PriceEntity> priceEntities = new HashSet<>();
        priceEntities.add(priceEntity);

        CryptoEntity crypto = new CryptoEntity();
        crypto.setName("DOGE");
        crypto.setPrices(priceEntities);

        when(priceRepository.findFirstByCryptoAndPriceTimestampBetween(crypto, fromInstant, toInstant, Sort.by("priceTimestamp").descending()))
                .thenReturn(priceEntity);

        actualPrice = priceService.getNewestPrice(crypto, fromInstant, toInstant);

        Assertions.assertEquals(expectedPrice, actualPrice);
    }

    private Instant localDateToUTCInstant(LocalDate date) {
        return date.atStartOfDay().toInstant(ZoneOffset.UTC);
    }
}