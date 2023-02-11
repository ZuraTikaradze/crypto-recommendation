package com.xm.cryptorecommendation.service;

import org.springframework.web.multipart.MultipartFile;

public interface CSVUploadService {
    void upload(MultipartFile file);
}
