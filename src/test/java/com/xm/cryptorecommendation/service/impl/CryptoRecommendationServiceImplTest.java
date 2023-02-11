package com.xm.cryptorecommendation.service.impl;

import com.xm.cryptorecommendation.dto.CryptoDTO;
import com.xm.cryptorecommendation.dto.CryptoDetailsDTO;
import com.xm.cryptorecommendation.dto.CryptoOrderedNormalizedRangeDTO;
import com.xm.cryptorecommendation.entity.CryptoEntity;
import com.xm.cryptorecommendation.entity.PriceEntity;
import com.xm.cryptorecommendation.repository.CryptoRepository;
import com.xm.cryptorecommendation.repository.PriceRepository;
import com.xm.cryptorecommendation.service.CryptoRecommendationService;
import com.xm.cryptorecommendation.service.CryptoService;
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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CryptoRecommendationServiceImplTest {

    @Autowired
    CryptoRecommendationService cryptoRecommendationService;

    @MockBean
    CryptoRepository cryptoRepository;

    @MockBean
    PriceRepository priceRepository;

    @Test
    void getCryptoDetails() {

        final BigDecimal expectedPrice = new BigDecimal(10);

        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setPrice(new BigDecimal(10));

        Set<PriceEntity> priceEntities = new HashSet<>();
        priceEntities.add(priceEntity);

        CryptoEntity crypto = new CryptoEntity();
        crypto.setName("DOGE");
        crypto.setPrices(priceEntities);


        LocalDate from = LocalDate.parse("2020-01-08");
        LocalDate to = LocalDate.parse("2023-01-08");

        Instant fromInstant = localDateToUTCInstant(from);
        Instant toInstant = localDateToUTCInstant(to);


        when(cryptoRepository.getByActiveIsTrue())
                .thenReturn(Stream.of(crypto)
                        .collect(Collectors.toList()));

        when(priceRepository.findFirstByCryptoAndPriceTimestampBetween(crypto, fromInstant, toInstant, Sort.by("price").descending()))
                .thenReturn(priceEntity);


        when(priceRepository.findFirstByCryptoAndPriceTimestampBetween(crypto, fromInstant, toInstant, Sort.by("price").ascending()))
                .thenReturn(priceEntity);

        when(priceRepository.findFirstByCryptoAndPriceTimestampBetween(crypto, fromInstant, toInstant, Sort.by("priceTimestamp").ascending()))
                .thenReturn(priceEntity);

        when(priceRepository.findFirstByCryptoAndPriceTimestampBetween(crypto, fromInstant, toInstant, Sort.by("priceTimestamp").descending()))
                .thenReturn(priceEntity);


        CryptoDetailsDTO cryptoDetailsDTO = cryptoRecommendationService.getCryptoDetails("DOGE", from, to);

        Assertions.assertEquals(expectedPrice, cryptoDetailsDTO.getMaxPrice());
        Assertions.assertEquals(expectedPrice, cryptoDetailsDTO.getMaxPrice());
        Assertions.assertEquals(expectedPrice, cryptoDetailsDTO.getNewestPrice());
        Assertions.assertEquals(expectedPrice, cryptoDetailsDTO.getOldestPrice());


    }

    @Test
    void getHighestNormalizedRangeCryptoForSpecificDay() {

        final String expectedName = "DOGE";
        String actualName;

        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setPrice(new BigDecimal(10));

        Set<PriceEntity> priceEntities = new HashSet<>();
        priceEntities.add(priceEntity);

        CryptoEntity doge = new CryptoEntity();
        doge.setName("DOGE");
        doge.setPrices(priceEntities);

        when(cryptoRepository.getByActiveIsTrue())
                .thenReturn(Stream.of(doge)
                        .collect(Collectors.toList()));

        LocalDate from = LocalDate.parse("2020-01-08");
        LocalDate to = LocalDate.parse("2023-01-08");

        CryptoOrderedNormalizedRangeDTO cryptoOrderedNormalizedRangeDTO =
                cryptoRecommendationService.getNormalizedCryptosOrderedByPrice(from, to);

        actualName = cryptoOrderedNormalizedRangeDTO.getCryptos().get(0).getName();

        Assertions.assertEquals(expectedName, actualName);

    }

    @Test
    void getNormalizedCryptosOrderedByPrice() {

        final String expectedName = "DOGE";
        String actualName;

        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setPrice(new BigDecimal(10));

        Set<PriceEntity> priceEntities = new HashSet<>();
        priceEntities.add(priceEntity);

        CryptoEntity doge = new CryptoEntity();
        doge.setName("DOGE");
        doge.setPrices(priceEntities);

        when(cryptoRepository.getByActiveIsTrue())
                .thenReturn(Stream.of(doge)
                        .collect(Collectors.toList()));

        LocalDate date = LocalDate.parse("2020-01-08");

        CryptoDTO cryptoDTO = cryptoRecommendationService.getHighestNormalizedRangeCryptoForSpecificDay(date);

        actualName = cryptoDTO.getName();

        Assertions.assertEquals(expectedName, actualName);
    }

    private Instant localDateToUTCInstant(LocalDate date) {
        return date.atStartOfDay().toInstant(ZoneOffset.UTC);
    }
}