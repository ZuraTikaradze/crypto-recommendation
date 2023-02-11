package com.xm.cryptorecommendation.service.impl;

import com.xm.cryptorecommendation.entity.CryptoEntity;
import com.xm.cryptorecommendation.repository.CryptoRepository;
import com.xm.cryptorecommendation.service.CryptoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CryptoServiceImplTest {

    @Autowired
    CryptoService cryptoService;

    @MockBean
    CryptoRepository cryptoRepository;

    @Test
    void getActiveCrypto() {

        final String expectedCryptoName = "BTC";
        String actualCryptoName;

        CryptoEntity btc = new CryptoEntity();
        btc.setName("BTC");
        CryptoEntity eth = new CryptoEntity();
        eth.setName("ETH");

        when(cryptoRepository.getByActiveIsTrue())
                .thenReturn(Stream.of(btc, eth)
                        .collect(Collectors.toList()));

        CryptoEntity crypto = cryptoService.getActiveCrypto("BTC");
        actualCryptoName = crypto.getName();

        Assertions.assertEquals(expectedCryptoName, actualCryptoName);

    }

    @Test
    void getActiveCryptos() {

        final Integer expectedSize = 2;
        Integer actualSize;

        CryptoEntity btc = new CryptoEntity();
        btc.setName("BTC");
        CryptoEntity eth = new CryptoEntity();
        eth.setName("ETH");

        when(cryptoRepository.getByActiveIsTrue())
                .thenReturn(Stream.of(btc, eth)
                        .collect(Collectors.toList()));

        List<CryptoEntity> cryptos = cryptoService.getActiveCryptos();
        actualSize = cryptos.size();

        Assertions.assertEquals(expectedSize, actualSize);
    }
}