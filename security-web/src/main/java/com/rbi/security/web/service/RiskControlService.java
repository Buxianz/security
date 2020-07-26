package com.rbi.security.web.service;

import com.rbi.security.entity.web.hid.HidDangerDO;
import com.rbi.security.entity.web.risk.RiskControl;
import com.rbi.security.tool.PageData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service
 * @NAME: RiskControlService
 * @USER: "谢青"
 * @DATE: 2020/6/18
 * @TIME: 11:04
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 18
 * @DAY_NAME_SHORT: 周四
 * @DAY_NAME_FULL: 星期四
 * @HOUR: 11
 * @MINUTE: 04
 * @PROJECT_NAME: security
 **/
public interface RiskControlService {

    String addInside(RiskControl riskControl, MultipartFile[] picture) throws IOException;

    Map<String, Object> riskValueAndGrade(RiskControl riskControl);

    Map<String, Object> measuresResult(RiskControl riskControl);

    String addOutside(RiskControl riskControl, MultipartFile[] picture) throws IOException;

    PageData findByPage(String riskType,int pageNo, int pageSize);

    PageData findSeriousRiskByPage(String riskGrad, int pageNo, int pageSize);

    String update(RiskControl riskControl, MultipartFile[] picture) throws IOException;

    void deleteByPictureId(int id);

    PageData findInsideByCondition(String type, String value, int pageNo, int pageSize);

    PageData findOutsideByCondition(String type, String value, int pageNo, int pageSize);

    PageData findSeriousByCondition(String type, String value, int pageNo, int pageSize);

    Map<String, Object> findHarmKind();
}
