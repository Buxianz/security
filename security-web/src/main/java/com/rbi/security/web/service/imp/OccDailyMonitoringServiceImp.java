package com.rbi.security.web.service.imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.health.OccDailyMonitoring;
import com.rbi.security.entity.web.safe.content.SafeContentCategory;
import com.rbi.security.tool.DateUtil;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.health.OccDailyMonitoringDAO;
import com.rbi.security.web.service.OccDailyMonitoringService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service.imp
 * @NAME: OccDailyMonitoringServiceImp
 * @USER: "谢青"
 * @DATE: 2020/6/22
 * @TIME: 11:19
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 22
 * @DAY_NAME_SHORT: 周一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 11
 * @MINUTE: 19
 * @PROJECT_NAME: security
 **/
@Service
public class OccDailyMonitoringServiceImp implements OccDailyMonitoringService {
    @Autowired
    OccDailyMonitoringDAO occDailyMonitoringDAO;

    @Override
    public void add(OccDailyMonitoring occDailyMonitoring) {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        occDailyMonitoring.setOperatingStaff(personnelId);
        String idt = DateUtil.date(DateUtil.FORMAT_PATTERN);
        occDailyMonitoring.setIdt(idt);
        occDailyMonitoringDAO.add(occDailyMonitoring);
    }

    @Override
    public void update(OccDailyMonitoring occDailyMonitoring) {
        String udt = DateUtil.date(DateUtil.FORMAT_PATTERN);
        occDailyMonitoring.setUdt(udt);
        occDailyMonitoringDAO.update(occDailyMonitoring);
    }

    @Override
    public void delete(JSONObject json) {
        JSONArray array = json.getJSONArray("data");
        for (int i=0;i< array.size();i++){
            JSONObject result = (JSONObject) array.get(i);
            int id = result.getInteger("id");
            occDailyMonitoringDAO.delete(id);
        }
    }

    @Override
    public PageData findByPage(int pageNo, int pageSize) {
        int pageNo2 = pageSize * (pageNo - 1);
        List<OccDailyMonitoring> occDailyMonitorings = occDailyMonitoringDAO.findByPage(pageNo2,pageSize);
        int totalPage = 0;
        int count = occDailyMonitoringDAO.findByPageNum();
        if (0 == count % pageSize) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        return new PageData(pageNo, pageSize, totalPage, count, occDailyMonitorings);
    }
}
