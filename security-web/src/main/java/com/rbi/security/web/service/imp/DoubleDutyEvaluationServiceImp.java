package com.rbi.security.web.service.imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.doubleduty.*;
import com.rbi.security.entity.web.entity.SysCompanyPersonnel;
import com.rbi.security.entity.web.entity.SysRole;
import com.rbi.security.entity.web.hid.SystemSettingDTO;
import com.rbi.security.tool.DateUtil;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.StringUtils;
import com.rbi.security.web.DAO.doubleduty.DoubleDutyEvaluationDAO;
import com.rbi.security.web.DAO.doubleduty.DoubleDutyFieDAO;
import com.rbi.security.web.service.DoubleDutyEvaluationService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service.imp
 * @NAME: DoubleDutyEvaluationServiceImp
 * @USER: "谢青"
 * @DATE: 2020/7/22
 * @TIME: 10:24
 * @YEAR: 2020
 * @MONTH: 07
 * @MONTH_NAME_SHORT: 7月
 * @MONTH_NAME_FULL: 七月
 * @DAY: 22
 * @DAY_NAME_SHORT: 周三
 * @DAY_NAME_FULL: 星期三
 * @HOUR: 10
 * @MINUTE: 24
 * @PROJECT_NAME: security
 **/
@Service
public class DoubleDutyEvaluationServiceImp implements DoubleDutyEvaluationService {
    @Autowired
    DoubleDutyEvaluationDAO doubleDutyEvaluationDAO;

    @Autowired
    DoubleDutyFieDAO doubleDutyFieDAO;

    @Override
    public PageData findPersonelByPage(int pageNo, int pageSize) {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        int pageNo2 = pageSize * (pageNo - 1);
        List<DoubleDutyEvaluation> doubleDutyEvaluations = doubleDutyEvaluationDAO.findPersonelByPage(personnelId,pageNo2,pageSize);
        for (int i=0;i<doubleDutyEvaluations.size();i++){
            if (doubleDutyEvaluations.get(i).getStatus().equals("1")){
                doubleDutyEvaluations.get(i).setStatusName("待填写");
            }
            if (doubleDutyEvaluations.get(i).getStatus().equals("2")) {
                doubleDutyEvaluations.get(i).setStatusName("待审核");
            }
            if (doubleDutyEvaluations.get(i).getStatus().equals("3")) {
                doubleDutyEvaluations.get(i).setStatusName("审核完成");
            }
            List<DoubleDutyEvaluationContent> doubleDutyEvaluationContents = doubleDutyFieDAO.findEvaluationContentById(doubleDutyEvaluations.get(i).getId());
            doubleDutyEvaluations.get(i).setDoubleDutyEvaluationContents(doubleDutyEvaluationContents);
        }
        int totalPage = 0;
        int count = doubleDutyEvaluationDAO.findPersonelNum(personnelId);
        if (0 == count % pageSize) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        return new PageData(pageNo, pageSize, totalPage, count, doubleDutyEvaluations);
    }


    @Override
    public PageData findAuditByPage(int pageNo, int pageSize) {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        Integer userId = currentUser.getId();
        int pageNo2 = pageSize * (pageNo - 1);
        SysCompanyPersonnel sysCompanyPersonnel = doubleDutyEvaluationDAO.findPersonnelById(personnelId);
        SysRole sysRole = doubleDutyEvaluationDAO.findRoleByUserId(userId);
        if (sysRole.getLevel() == 1){
            List<DoubleDutyEvaluation> doubleDutyEvaluations = doubleDutyEvaluationDAO.findFirstLevelAuditByPage(sysCompanyPersonnel.getOrganizationId(),personnelId,pageNo2,pageSize);
            for (int i=0;i<doubleDutyEvaluations.size();i++){
                if (doubleDutyEvaluations.get(i).getStatus().equals("1")){
                    doubleDutyEvaluations.get(i).setStatusName("待填写");
                }
                if (doubleDutyEvaluations.get(i).getStatus().equals("2")) {
                    doubleDutyEvaluations.get(i).setStatusName("待审核");
                }
                List<DoubleDutyEvaluationContent> doubleDutyEvaluationContents = doubleDutyFieDAO.findEvaluationContentById(doubleDutyEvaluations.get(i).getId());
                doubleDutyEvaluations.get(i).setDoubleDutyEvaluationContents(doubleDutyEvaluationContents);
            }
            int totalPage = 0;
            int count = doubleDutyEvaluationDAO.findFirstLevelAuditNum(sysCompanyPersonnel.getOrganizationId(),personnelId);
            if (0 == count % pageSize) {
                totalPage = count / pageSize;
            } else {
                totalPage = count / pageSize + 1;
            }
            return new PageData(pageNo, pageSize, totalPage, count, doubleDutyEvaluations);
        }if (sysRole.getLevel() == 2){
            List<DoubleDutyEvaluation> doubleDutyEvaluations = doubleDutyEvaluationDAO.findSecondLevelAuditByPage(sysCompanyPersonnel.getOrganizationId(),pageNo2,pageSize);
            for (int i=0;i<doubleDutyEvaluations.size();i++){
                if (doubleDutyEvaluations.get(i).getStatus().equals("1")){
                    doubleDutyEvaluations.get(i).setStatusName("待填写");
                }
                if (doubleDutyEvaluations.get(i).getStatus().equals("2")) {
                    doubleDutyEvaluations.get(i).setStatusName("待审核");
                }
                List<DoubleDutyEvaluationContent> doubleDutyEvaluationContents = doubleDutyFieDAO.findEvaluationContentById(doubleDutyEvaluations.get(i).getId());
                doubleDutyEvaluations.get(i).setDoubleDutyEvaluationContents(doubleDutyEvaluationContents);
            }
            int totalPage = 0;
            int count = doubleDutyEvaluationDAO.findSecondLevelAuditNum(sysCompanyPersonnel.getOrganizationId());
            if (0 == count % pageSize) {
                totalPage = count / pageSize;
            } else {
                totalPage = count / pageSize + 1;
            }
            return new PageData(pageNo, pageSize, totalPage, count, doubleDutyEvaluations);
        }
        return null;
    }

    @Override
    public DoubleDutyTemplate findTemplate() {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        SysCompanyPersonnel sysCompanyPersonnel = doubleDutyEvaluationDAO.findPersonnelById(personnelId);
        Integer organizationId = sysCompanyPersonnel.getOrganizationId();
        String position = sysCompanyPersonnel.getPosition();
        DoubleDutyTemplate doubleDutyTemplate =  doubleDutyEvaluationDAO.findTemplate(organizationId,position);
        if (StringUtils.isNotBlank(doubleDutyTemplate)){
            List<DoubleDutyTemplateContent> doubleDutyTemplateContent = doubleDutyEvaluationDAO.findTemplateContentByTemplateId(doubleDutyTemplate.getId());
            doubleDutyTemplate.setDoubleDutyTemplateContents(doubleDutyTemplateContent);
        }
        return doubleDutyTemplate;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public String write(JSONObject json) {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        SysCompanyPersonnel sysCompanyPersonnel = doubleDutyEvaluationDAO.findPersonnelById(personnelId);
        DoubleDutyEvaluation doubleDutyEvaluation = JSON.toJavaObject(json,DoubleDutyEvaluation.class);
        doubleDutyEvaluation.setPersonnelId(personnelId);
        doubleDutyEvaluation.setPersonnelName(sysCompanyPersonnel.getName());
        String time = DateUtil.date(DateUtil.FORMAT_PATTERN);
        doubleDutyEvaluation.setWriteTime(time);
        doubleDutyEvaluation.setStatus("2");
        doubleDutyEvaluation.setIdt(time);
        doubleDutyEvaluationDAO.writeEvaluation(doubleDutyEvaluation);
        JSONArray array = json.getJSONArray("content");
        List<DoubleDutyEvaluationContent> doubleDutyEvaluationContents = JSONObject.parseArray(array.toJSONString(),DoubleDutyEvaluationContent.class);
        for (int i=0;i<doubleDutyEvaluationContents.size();i++){
            doubleDutyEvaluationContents.get(i).setEvaluationId(doubleDutyEvaluation.getId());
        }
        doubleDutyEvaluationDAO.writeEvaluationContentList(doubleDutyEvaluationContents);
        return "1000";
    }

    @Override
    public String audit(JSONObject json) {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        SysCompanyPersonnel sysCompanyPersonnel = doubleDutyEvaluationDAO.findPersonnelById(personnelId);
        DoubleDutyEvaluation doubleDutyEvaluation = JSON.toJavaObject(json,DoubleDutyEvaluation.class);
        doubleDutyEvaluation.setPersonnelId(personnelId);
        doubleDutyEvaluation.setPersonnelName(sysCompanyPersonnel.getName());
        String time = DateUtil.date(DateUtil.FORMAT_PATTERN);
        doubleDutyEvaluation.setWriteTime(time);
        doubleDutyEvaluation.setStatus("2");
        doubleDutyEvaluation.setIdt(time);
        doubleDutyEvaluationDAO.writeEvaluation(doubleDutyEvaluation);
        JSONArray array = json.getJSONArray("content");
        List<DoubleDutyEvaluationContent> doubleDutyEvaluationContents = JSONObject.parseArray(array.toJSONString(),DoubleDutyEvaluationContent.class);
        for (int i=0;i<doubleDutyEvaluationContents.size();i++){
            doubleDutyEvaluationContents.get(i).setEvaluationId(doubleDutyEvaluation.getId());
        }
        doubleDutyEvaluationDAO.writeEvaluationContentList(doubleDutyEvaluationContents);
        return "1000";
    }
}
