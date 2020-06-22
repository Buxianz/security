package com.rbi.security.web.service;

import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.health.OccRegularMonitoring;
import com.rbi.security.tool.PageData;
import org.apache.ibatis.annotations.Insert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service
 * @NAME: OccRegularMonitoringService
 * @USER: "谢青"
 * @DATE: 2020/6/22
 * @TIME: 15:34
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 22
 * @DAY_NAME_SHORT: 周一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 15
 * @MINUTE: 34
 * @PROJECT_NAME: security
 **/
public interface OccRegularMonitoringService {

    PageData findByPage(int pageNo, int pageSize);

    void add(OccRegularMonitoring occRegularMonitoring, MultipartFile file) throws IOException;

    void update(OccRegularMonitoring occRegularMonitoring, MultipartFile file) throws IOException;

    void delete(JSONObject json);

    void deleteFile(Integer id);
}
