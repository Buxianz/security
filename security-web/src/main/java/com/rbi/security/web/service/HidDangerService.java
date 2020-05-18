package com.rbi.security.web.service;

import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.hid.HidDangerDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface HidDangerService {
    String addReport(int userId, HidDangerDTO hidDangerDTO, MultipartFile[] beforeImg, MultipartFile[] afterImg, MultipartFile plan, MultipartFile report) throws IOException;
}
