package com.xm.cryptorecommendation.service.impl;

import com.xm.cryptorecommendation.dto.CryptoDTO;
import com.xm.cryptorecommendation.dto.CryptoDetailsDTO;
import com.xm.cryptorecommendation.dto.CryptoOrderedNormalizedRangeDTO;
import com.xm.cryptorecommendation.entity.CryptoEntity;
import com.xm.cryptorecommendation.exception.NotFoundException;
import com.xm.cryptorecommendation.service.CryptoRecommendationService;
import com.xm.cryptorecommendation.service.CryptoService;
import com.xm.cryptorecommendation.service.PriceService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CryptoRecommendationServiceImpl implements CryptoRecommendationService {


    private final PriceService priceService;

    private final CryptoService cryptoService;

    private final ModelMapper modelMapper;

    public CryptoRecommendationServiceImpl(PriceService priceService, CryptoService cryptoService, ModelMapper modelMapper) {
        this.priceService = priceService;
        this.cryptoService = cryptoService;
        this.modelMapper = modelMapper;
    }

    @Override
    public CryptoDetailsDTO getCryptoDetails(String cryptoName, LocalDate from, LocalDate to) {
        CryptoEntity crypto = cryptoService.getActiveCrypto(cryptoName);

        Instant fromInstant = localDateToUTCInstant(from);
        Instant toInstant = localDateToUTCInstant(to);

        BigDecimal minPrice = priceService.getMinPrice(crypto, fromInstant, toInstant);
        BigDecimal maxPrice = priceService.getMaxPrice(crypto, fromInstant, toInstant);
        BigDecimal newestPrice = priceService.getNewestPrice(crypto, fromInstant, toInstant);
        BigDecimal oldestPrice = priceService.getOldestPrice(crypto, fromInstant, toInstant);


        if (maxPrice == null || minPrice == null || oldestPrice == null || newestPrice == null) {
            throw new NotFoundException("Price not found");
        }

        CryptoDetailsDTO cryptoDetailsDTO = new CryptoDetailsDTO();
        cryptoDetailsDTO.setMinPrice(minPrice);
        cryptoDetailsDTO.setMaxPrice(maxPrice);
        cryptoDetailsDTO.setNewestPrice(newestPrice);
        cryptoDetailsDTO.setOldestPrice(oldestPrice);

        return cryptoDetailsDTO;

    }

    @Override
    public CryptoDTO getHighestNormalizedRangeCryptoForSpecificDay(LocalDate date) {
        CryptoDTO cryptoDTO = getNormalizedCryptosOrderedByPrice(date, date.plusDays(1)).getCryptos().get(0);
        if (cryptoDTO == null) {
            throw new NotFoundException("Highest Normalized Range Crypto - not found");
        }
        return cryptoDTO;
    }


    @Override
    public CryptoOrderedNormalizedRangeDTO getNormalizedCryptosOrderedByPrice(LocalDate from, LocalDate to) {
        Instant fromInstant = localDateToUTCInstant(from);
        Instant toInstant = localDateToUTCInstant(to);

        List<CryptoEntity> cryptos = cryptoService.getActiveCryptos().stream()
                .sorted(Comparator.comparingDouble(
                        c -> (priceService.getMaxPrice(c, fromInstant, toInstant).doubleValue() -
                                priceService.getMinPrice(c, fromInstant, toInstant).doubleValue()) /
                                priceService.getMinPrice(c, fromInstant, toInstant).doubleValue()))
                .collect(Collectors.toList());
        Collections.reverse(cryptos);

        List<CryptoDTO> cryptoDTOList = cryptos.stream()
                .map(this::toDto)
                .toList();

        CryptoOrderedNormalizedRangeDTO cryptoOrderedNormalizedRangeDTO = new CryptoOrderedNormalizedRangeDTO();
        cryptoOrderedNormalizedRangeDTO.setCryptos(cryptoDTOList);

        if (cryptoOrderedNormalizedRangeDTO.getCryptos().isEmpty()) {
            throw new NotFoundException("Cryptos - not found");
        }

        return cryptoOrderedNormalizedRangeDTO;
    }

    private Instant localDateToUTCInstant(LocalDate date) {
        return date.atStartOfDay().toInstant(ZoneOffset.UTC);
    }


    private CryptoDTO toDto(CryptoEntity crypto) {
        return modelMapper.map(crypto, CryptoDTO.class);
    }

}
