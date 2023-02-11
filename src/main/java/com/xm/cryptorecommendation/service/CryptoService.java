package com.xm.cryptorecommendation.service;

import com.xm.cryptorecommendation.entity.CryptoEntity;

import java.util.List;

public interface CryptoService {
    CryptoEntity getActiveCrypto(String cryptoName);

    List<CryptoEntity> getActiveCryptos();
}
