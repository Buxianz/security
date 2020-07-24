package com.rbi.security.web.service.imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.doubleduty.*;
import com.rbi.security.entity.web.entity.SysCompanyPersonnel;
import com.rbi.security.entity.web.entity.SysOrganization;
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

import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;

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
    public PageData findByPage(int pageNo, int pageSize) {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        int pageNo2 = pageSize * (pageNo - 1);
        List<DoubleDutyTemplate> doubleDutyTemplates = doubleDutyTemplateDAO.findByPage(personnelId,pageNo2,pageSize);
        int totalPage = 0;
        int count = doubleDutyTemplateDAO.findByPageNum(personnelId);
        for (int i=0;i<doubleDutyTemplates.size();i++){
            List<DoubleDutyTemplateContent> doubleDutyTemplateContents = doubleDutyTemplateDAO.findDoubleDutyTemplateContentsBytemplateId(doubleDutyTemplates.get(i).getId());
            doubleDutyTemplates.get(i).setDoubleDutyTemplateContents(doubleDutyTemplateContents);
        }
        if (0 == count % pageSize) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        return new PageData(pageNo, pageSize, totalPage, count, doubleDutyTemplates);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public String add(JSONObject json) {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        String idt = DateUtil.date(DateUtil.FORMAT_PATTERN);
        DoubleDutyTemplate doubleDutyTemplate = JSON.toJavaObject(json,DoubleDutyTemplate.class);
        int num = doubleDutyTemplateDAO.findAddNum(doubleDutyTemplate.getOrganizationId(),doubleDutyTemplate.getPosition());
        if (num != 0){
            return "已添加了相同的组织和岗位名";
        }
        //隐患所属组织表
        SysOrganization sysOrganization2 = doubleDutyTemplateDAO.findAllByOrganizationId(doubleDutyTemplate.getOrganizationId());
        int level = sysOrganization2.getLevel();
        if (level == 4 ){
            doubleDutyTemplate.setClassName(sysOrganization2.getOrganizationName());
        }
        if (level == 3 ){
            doubleDutyTemplate.setWorkshopName(sysOrganization2.getOrganizationName());
        }
        if (level == 2 ){
            doubleDutyTemplate.setFactoryName(sysOrganization2.getOrganizationName());
        }
        if (level == 1 ){
            doubleDutyTemplate.setCompanyName(sysOrganization2.getOrganizationName());
        }
        Integer parentId = sysOrganization2.getParentId();
        level = level -1;
        while (level !=0){
            SysOrganization sysOrganization3 = doubleDutyTemplateDAO.findAllByOrganizationId(parentId);
            if (level == 3 ){
                doubleDutyTemplate.setWorkshopName(sysOrganization3.getOrganizationName());
            }
            if (level == 2 ){
                doubleDutyTemplate.setFactoryName(sysOrganization3.getOrganizationName());
            }
            if (level == 1 ){
                doubleDutyTemplate.setCompanyName(sysOrganization3.getOrganizationName());
            }
            parentId = sysOrganization3.getParentId();
            level=level - 1;
        }
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
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public String update(JSONObject json) {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        String udt = DateUtil.date(DateUtil.FORMAT_PATTERN);
        DoubleDutyTemplate doubleDutyTemplate = JSON.toJavaObject(json,DoubleDutyTemplate.class);

        int num = doubleDutyTemplateDAO.findUpdateNum(doubleDutyTemplate.getOrganizationId(),doubleDutyTemplate.getPosition(),doubleDutyTemplate.getId());
        if (num != 0){
            return "已存在相同的组织和岗位名";
        }
        doubleDutyTemplate.setPersonnelId(personnelId);
        doubleDutyTemplate.setUdt(udt);
        //隐患所属组织表
        SysOrganization sysOrganization2 = doubleDutyTemplateDAO.findAllByOrganizationId(doubleDutyTemplate.getOrganizationId());
        int level = sysOrganization2.getLevel();
        if (level == 4 ){
            doubleDutyTemplate.setClassName(sysOrganization2.getOrganizationName());
        }
        if (level == 3 ){
            doubleDutyTemplate.setWorkshopName(sysOrganization2.getOrganizationName());
        }
        if (level == 2 ){
            doubleDutyTemplate.setFactoryName(sysOrganization2.getOrganizationName());
        }
        if (level == 1 ){
            doubleDutyTemplate.setCompanyName(sysOrganization2.getOrganizationName());
        }
        Integer parentId = sysOrganization2.getParentId();
        level = level -1;
        while (level !=0){
            SysOrganization sysOrganization3 = doubleDutyTemplateDAO.findAllByOrganizationId(parentId);
            if (level == 3 ){
                doubleDutyTemplate.setWorkshopName(sysOrganization3.getOrganizationName());
            }
            if (level == 2 ){
                doubleDutyTemplate.setFactoryName(sysOrganization3.getOrganizationName());
            }
            if (level == 1 ){
                doubleDutyTemplate.setCompanyName(sysOrganization3.getOrganizationName());
            }
            parentId = sysOrganization3.getParentId();
            level=level - 1;
        }
        doubleDutyTemplateDAO.updateTemplate(doubleDutyTemplate);
        doubleDutyTemplateDAO.deleteTemplateContent(doubleDutyTemplate.getId());
        JSONArray array = json.getJSONArray("contentArry");
        List<DoubleDutyTemplateContent> doubleDutyTemplateContents = JSONObject.parseArray(array.toJSONString(),DoubleDutyTemplateContent.class);
        for (int i=0;i<doubleDutyTemplateContents.size();i++){
            doubleDutyTemplateContents.get(i).setTemplateId(doubleDutyTemplate.getId());
        }
        doubleDutyTemplateDAO.addTemplateContent(doubleDutyTemplateContents);
        return "1000";
    }

    @Override
    public String delete(JSONObject json) {
        Integer id = json.getInteger("id");
        doubleDutyTemplateDAO.deleteTemplate(id);
        doubleDutyTemplateDAO.deleteTemplateContent(id);
        return "1000";
    }

}
