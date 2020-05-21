package com.rbi.security.web.service;

import org.springframework.web.multipart.MultipartFile;

public interface SystemManagementService {
    void uploadDemandSystemFiles(MultipartFile[] multipartFiles,int systemCategoryId) throws RuntimeException;
}
