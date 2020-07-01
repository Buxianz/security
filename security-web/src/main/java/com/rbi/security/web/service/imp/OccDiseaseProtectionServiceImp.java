package com.rbi.security.web.service.imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.health.OccDailyMonitoring;
import com.rbi.security.entity.web.health.OccDiseaseProtection;
import com.rbi.security.tool.DateUtil;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.health.OccDiseaseProtectionDAO;
import com.rbi.security.web.service.OccDiseaseProtectionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: SXD
 * @Description:
 * @Date: create in 2020/6/26 10:33
 */
@Service
public class OccDiseaseProtectionServiceImp implements OccDiseaseProtectionService {
    @Autowired
    OccDiseaseProtectionDAO occDiseaseProtectionDAO;

    @Override
    public PageData findByPage(int pageNo, int pageSize) {
        int pageNo2 = pageSize * (pageNo - 1);
        List<OccDiseaseProtection> occDiseaseProtections = occDiseaseProtectionDAO.findByPage(pageNo2,pageSize);
        int totalPage = 0;
        int count = occDiseaseProtectionDAO.findByPageNum();
        if (0 == count % pageSize) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        return new PageData(pageNo, pageSize, totalPage, count, occDiseaseProtections);
    }

    @Override
    public String add(OccDiseaseProtection occDiseaseProtection) {
        try {
            Subject subject = SecurityUtils.getSubject();
            AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
            Integer personnelId  =  currentUser.getCompanyPersonnelId();
            occDiseaseProtection.setOperatingStaff(personnelId);
            String idt = DateUtil.date(DateUtil.FORMAT_PATTERN);
            occDiseaseProtection.setIdt(idt);
            occDiseaseProtectionDAO.add(occDiseaseProtection);
            return "1000";
        }catch (NumberFormatException e){
            return "参数填写错误，请注意填写数字";
        }

    }

    @Override
    public String update(OccDiseaseProtection occDiseaseProtection) {
        try {
            String udt = DateUtil.date(DateUtil.FORMAT_PATTERN);
            occDiseaseProtection.setUdt(udt);
            occDiseaseProtectionDAO.update(occDiseaseProtection);
            return "1000";
        }catch (NumberFormatException e){
            return "参数填写错误，请注意填写数字";
        }

    }

    @Override
    public void delete(JSONObject json) {
        JSONArray array = json.getJSONArray("data");
        for (int i=0;i< array.size();i++){
            JSONObject result = (JSONObject) array.get(i);
            int id = result.getInteger("id");
            occDiseaseProtectionDAO.delete(id);
        }
    }
}
