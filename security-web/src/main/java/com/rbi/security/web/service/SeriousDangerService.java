package com.rbi.security.web.service;

import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.SeriousDanger.PagingSeriousDanger;
import com.rbi.security.entity.web.entity.SeriousDanger;
import com.rbi.security.tool.PageData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SeriousDangerService {

    String insertSeriousDanger(SeriousDanger seriousDanger, MultipartFile[] seriousDangerPicture) throws IOException;

    PageData findSeriousDangerByPage(JSONObject json) throws IOException;

    PageData findSeriousDangerByPageAndName(JSONObject json) throws IOException;

    PagingSeriousDanger findSeriousDangerByID(JSONObject json) throws IOException;

    String updateSeriousDanger(SeriousDanger seriousDanger,Integer pictureId, MultipartFile[] seriousDangerPicture) throws IOException;
}
