package com.rbi.security.web.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.hid.HidDangerDTO;
import com.rbi.security.tool.PageData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface HidDangerService {
    String addReport(HidDangerDTO hidDangerDTO, MultipartFile[] beforeImg, MultipartFile[] afterImg, MultipartFile plan, MultipartFile report) throws IOException;

    Map<String, Object> findAdmChoose(JSONArray array);

    String addOrder(HidDangerDTO hidDangerDTO, MultipartFile[] beforeImg, MultipartFile notice) throws IOException;

    PageData findDealByPage(int pageNo, int pageSize);
}
