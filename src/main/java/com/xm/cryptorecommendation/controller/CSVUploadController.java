package com.xm.cryptorecommendation.controller;

import com.xm.cryptorecommendation.exception.CSVFormatException;
import com.xm.cryptorecommendation.service.CSVUploadService;
import com.xm.cryptorecommendation.validator.CSVFileValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/csv/files/data")
public class CSVUploadController {
    private final CSVUploadService csvUploadService;

    private final CSVFileValidator csvFileValidator;

    public CSVUploadController(CSVUploadService csvUploadService, CSVFileValidator csvFileValidator) {
        this.csvUploadService = csvUploadService;
        this.csvFileValidator = csvFileValidator;
    }

    @PostMapping("crypto")
    public ResponseEntity<Void> uploadCryptos(@RequestParam("file") MultipartFile file) {
        validateRequest(file);
        csvUploadService.upload(file);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private void validateRequest(MultipartFile file) {
        if (csvFileValidator.isInvalid(file)) {
            throw new CSVFormatException("Uploaded file is not in SCV format");
        }
    }

}
