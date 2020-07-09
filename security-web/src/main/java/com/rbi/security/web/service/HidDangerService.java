package com.rbi.security.web.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.hid.HidDangerDO;
import com.rbi.security.tool.PageData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


/**
 * @USER: "谢青"
 * @DATE: 2020/5/21
 * @TIME: 17:45
 * @YEAR: 2020
 * @MONTH: 05
 * @MONTH_NAME_SHORT: 5月
 * @MONTH_NAME_FULL: 五月
 * @DAY: 21
 * @DAY_NAME_SHORT: 周四
 * @DAY_NAME_FULL: 星期四
 * @HOUR: 17
 * @MINUTE: 45
 * @PROJECT_NAME: security
 **/
public interface HidDangerService {
    String addReport(HidDangerDO hidDangerDO, MultipartFile[] beforeImg, MultipartFile[] afterImg, MultipartFile plan, MultipartFile report) throws IOException;

    Map<String, Object> findAdmChoose(JSONArray array);

    String addOrder(HidDangerDO hidDangerDO, MultipartFile[] beforeImg, MultipartFile notice) throws IOException;

    PageData findDealByPage(int pageNo, int pageSize);

    PageData findFinishByPage(int pageNo, int pageSize);

    Map<String, Object> findDealDetailByCode(String hidDangerCode);

    Map<String, Object> findFinishDetailByCode(String hidDangerCode);

    String complete(HidDangerDO hidDangerDO, MultipartFile[] beforeImg, MultipartFile[] afterImg, MultipartFile plan, MultipartFile report) throws IOException;

    void auditPass(JSONObject json);

    void auditFalse(JSONObject json);

    String rectificationNotice(JSONObject json);

    Map<String, Object> findCorrector();

    String report(JSONObject json);

    void deletePlan(String hidDangerCode);

    void deleteReport(String hidDangerCode);

    void deletePicture(Integer id);
}
