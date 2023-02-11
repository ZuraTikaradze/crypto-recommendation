package com.xm.cryptorecommendation.validator;

import com.xm.cryptorecommendation.exception.CSVFormatException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CSVFileValidator implements Validator<MultipartFile> {
    public static final String TYPE = "text/csv";

    @Override
    public boolean isValid(MultipartFile source) {
        String contentType = source.getContentType();
        if (contentType == null) {
            throw new CSVFormatException("Content Type is null");
        }
        return contentType.equals(TYPE);
    }

}
