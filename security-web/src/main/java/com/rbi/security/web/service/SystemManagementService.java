package com.rbi.security.web.service;

import com.rbi.security.entity.web.system.PagingSystemInfo;
import com.rbi.security.tool.PageData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SystemManagementService {
    void uploadDemandSystemFiles(MultipartFile[] multipartFiles,int systemCategoryId) throws RuntimeException;
    PageData<PagingSystemInfo> getPagingSystemInfo(int pageNo, int pageSize , int startIndex)throws RuntimeException;
    void deleteSystemFile(int id) throws RuntimeException;
}
