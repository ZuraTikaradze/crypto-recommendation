package com.xm.cryptorecommendation.service.impl;

import com.xm.cryptorecommendation.entity.CryptoEntity;
import com.xm.cryptorecommendation.entity.PriceEntity;
import com.xm.cryptorecommendation.exception.CSVParseException;
import com.xm.cryptorecommendation.exception.FileReadException;
import com.xm.cryptorecommendation.parser.CryptoDataParser;
import com.xm.cryptorecommendation.parser.PriceDataParser;
import com.xm.cryptorecommendation.repository.CryptoRepository;
import com.xm.cryptorecommendation.service.CSVUploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Set;

@Service
@Slf4j
public class CSVUploadServiceImpl implements CSVUploadService {

    private final CryptoDataParser cryptoDataParser;

    private final PriceDataParser priceDataParser;

    private final CryptoRepository cryptoRepository;

    public CSVUploadServiceImpl(CryptoDataParser cryptoDataParser, PriceDataParser priceDataParser, CryptoRepository cryptoRepository) {
        this.cryptoDataParser = cryptoDataParser;
        this.priceDataParser = priceDataParser;
        this.cryptoRepository = cryptoRepository;
    }

    @Override
    public void upload(MultipartFile multipartFile) {
        try {
            Reader reader = getReader(multipartFile);
            Iterable<CSVRecord> records = readRecords(reader);
            CryptoEntity crypto = cryptoDataParser.buildCryptoEntity(records.iterator().next());
            Set<PriceEntity> priceSet = priceDataParser.buildPriceSet(records);
            saveData(crypto, priceSet);
            log.info("File uploaded successfully : " + multipartFile.getOriginalFilename());
        } catch (CSVParseException e) {
            throw new CSVParseException(e.getMessage() + " " + multipartFile.getOriginalFilename());
        } catch (IOException ioException) {
            throw new FileReadException("Can't read file : " + multipartFile.getOriginalFilename());
        }
    }

    private Reader getReader(MultipartFile file) throws IOException {
        return new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
    }

    public Iterable<CSVRecord> readRecords(Reader reader) throws IOException {
        return CSVFormat.RFC4180.builder().setHeader().build().parse(reader);
    }

    public void saveData(CryptoEntity cryptoEntity, Set<PriceEntity> priceSet) {
        CryptoEntity crypto = new CryptoEntity();
        crypto.setActive(true);
        crypto.setName(cryptoEntity.getName());
        priceSet.stream().forEach(price -> price.setCrypto(crypto));
        crypto.setPrices(priceSet);
        cryptoRepository.save(crypto);
    }


}
