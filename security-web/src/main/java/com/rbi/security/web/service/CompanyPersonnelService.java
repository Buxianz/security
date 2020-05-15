package com.rbi.security.web.service;

import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.entity.SysCompanyPersonnel;
import com.rbi.security.tool.PageData;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CompanyPersonnelService {

    Map<String,Object> excelImport(MultipartFile file);

    PageData<SysCompanyPersonnel> queryByPage(JSONObject jsonObject);

    boolean add(JSONObject jsonObject);

    boolean update(JSONObject jsonObject);

    void delete(JSONObject jsonObject);

}
