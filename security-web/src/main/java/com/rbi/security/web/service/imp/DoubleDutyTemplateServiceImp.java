package com.rbi.security.web.service.imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.doubleduty.DoubleDuty;
import com.rbi.security.entity.web.doubleduty.DoubleDutyContent;
import com.rbi.security.entity.web.doubleduty.DoubleDutyTemplate;
import com.rbi.security.entity.web.doubleduty.DoubleDutyTemplateContent;
import com.rbi.security.entity.web.entity.SysCompanyPersonnel;
import com.rbi.security.entity.web.health.OccDiseaseProtection;
import com.rbi.security.tool.DateUtil;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.doubleduty.DoubleDutyTemplateDAO;
import com.rbi.security.web.service.DoubleDutyTemplateService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service.imp
 * @NAME: DoubleDutyTemplateServiceImp
 * @USER: "谢青"
 * @DATE: 2020/7/14
 * @TIME: 15:16
 * @YEAR: 2020
 * @MONTH: 07
 * @MONTH_NAME_SHORT: 7月
 * @MONTH_NAME_FULL: 七月
 * @DAY: 14
 * @DAY_NAME_SHORT: 周二
 * @DAY_NAME_FULL: 星期二
 * @HOUR: 15
 * @MINUTE: 16
 * @PROJECT_NAME: security
 **/
@Service
public class DoubleDutyTemplateServiceImp implements DoubleDutyTemplateService {
    @Autowired
    DoubleDutyTemplateDAO doubleDutyTemplateDAO;

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public String add(JSONObject json) {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        String idt = DateUtil.date(DateUtil.FORMAT_PATTERN);
        DoubleDutyTemplate doubleDutyTemplate = JSON.toJavaObject(json,DoubleDutyTemplate.class);
        doubleDutyTemplate.setPersonnelId(personnelId);
        doubleDutyTemplate.setIdt(idt);
        doubleDutyTemplateDAO.addTemplate(doubleDutyTemplate);

        JSONArray array = json.getJSONArray("contentArry");
        List<DoubleDutyTemplateContent> doubleDutyTemplateContents = JSONObject.parseArray(array.toJSONString(),DoubleDutyTemplateContent.class);
        for (int i=0;i<doubleDutyTemplateContents.size();i++){
            doubleDutyTemplateContents.get(i).setTemplateId(doubleDutyTemplate.getId());
        }
        doubleDutyTemplateDAO.addTemplateContent(doubleDutyTemplateContents);
        return "1000";
    }

    @Override
    public String update(JSONObject json) {
        return null;
    }

    @Override
    public String delete(JSONObject json) {
        return null;
    }

    @Override
    public PageData findByPage(int pageNo, int pageSize) {
        int pageNo2 = pageSize * (pageNo - 1);
        List<OccDiseaseProtection> occDiseaseProtections = doubleDutyTemplateDAO.findByPage(pageNo2,pageSize);
        int totalPage = 0;
        int count = doubleDutyTemplateDAO.findByPageNum();
        if (0 == count % pageSize) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        return new PageData(pageNo, pageSize, totalPage, count, occDiseaseProtections);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public String release(JSONObject json) {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        String name = doubleDutyTemplateDAO.fidNameById(personnelId);
        String idt = DateUtil.date(DateUtil.FORMAT_PATTERN);
        DoubleDuty doubleDuty = JSON.toJavaObject(json,DoubleDuty.class);
        doubleDuty.setPersonnelId(personnelId);
        doubleDuty.setIdt(idt);
        doubleDuty.setName(name);
        doubleDutyTemplateDAO.addDuty(doubleDuty);
        JSONArray contentArry = json.getJSONArray("contentArry");
        List<DoubleDutyContent> doubleDutyContents = JSONObject.parseArray(contentArry.toJSONString(),DoubleDutyContent.class);

        for (int i=0;i<doubleDutyContents.size();i++){
            doubleDutyContents.get(i).setDoubleDutyId(doubleDuty.getId());
        }
        doubleDutyTemplateDAO.addContent(doubleDutyContents);
        JSONArray array = json.getJSONArray("contentArry");

        return "1000";
    }
}
