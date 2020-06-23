package com.rbi.security.web.service.imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.health.OccDailyMonitoring;
import com.rbi.security.entity.web.health.OccDiseaseFactors;
import com.rbi.security.tool.DateUtil;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.health.OccDiseaseFactorsDAO;
import com.rbi.security.web.service.OccDiseaseFactorsService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service.imp
 * @NAME: OccDiseaseFactorsServiceImp
 * @USER: "谢青"
 * @DATE: 2020/6/23
 * @TIME: 14:36
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 23
 * @DAY_NAME_SHORT: 周二
 * @DAY_NAME_FULL: 星期二
 * @HOUR: 14
 * @MINUTE: 36
 * @PROJECT_NAME: security
 **/
@Service
public class OccDiseaseFactorsServiceImp implements OccDiseaseFactorsService {
    @Autowired
    OccDiseaseFactorsDAO occDiseaseFactorsDAO;

    @Override
    public PageData findByPage(int pageNo, int pageSize) {
        int pageNo2 = pageSize * (pageNo - 1);
        List<OccDiseaseFactors> occDiseaseFactors = occDiseaseFactorsDAO.findByPage(pageNo2,pageSize);
        int totalPage = 0;
        int count = occDiseaseFactorsDAO.findByPageNum();
        if (0 == count % pageSize) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        return new PageData(pageNo, pageSize, totalPage, count, occDiseaseFactors);
    }

    @Override
    public String add(OccDiseaseFactors occDiseaseFactors) {
        int num = occDiseaseFactorsDAO.findNum(occDiseaseFactors.getFactor());
        if (num != 0){
            return "此职业病危害因素已添加";
        }
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        occDiseaseFactors.setOperatingStaff(personnelId);
        String idt = DateUtil.date(DateUtil.FORMAT_PATTERN);
        occDiseaseFactors.setIdt(idt);
        occDiseaseFactorsDAO.add(occDiseaseFactors);
        return "1000";
    }

    @Override
    public String update(OccDiseaseFactors occDiseaseFactors) {
        int num = occDiseaseFactorsDAO.findNumExceptId(occDiseaseFactors.getFactor(),occDiseaseFactors.getId());
        if (num != 0){
            return "此职业病危害因素已添加";
        }
        String udt = DateUtil.date(DateUtil.FORMAT_PATTERN);
        occDiseaseFactors.setUdt(udt);
        occDiseaseFactorsDAO.update(occDiseaseFactors);
        return "1000";
    }

    @Override
    public void delete(JSONObject json) {
        JSONArray array = json.getJSONArray("data");
        for (int i=0;i< array.size();i++){
            JSONObject result = (JSONObject) array.get(i);
            int id = result.getInteger("id");
            occDiseaseFactorsDAO.delete(id);
        }
    }
}
