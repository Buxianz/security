package com.rbi.security.web.service;

import org.springframework.web.multipart.MultipartFile;

public interface DemandSystemService {
    void uploadDemandSystemFiles(MultipartFile[] multipartFiles) throws RuntimeException;
}
