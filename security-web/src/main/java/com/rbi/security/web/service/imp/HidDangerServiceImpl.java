package com.rbi.security.web.service.imp;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.entity.SysCompanyPersonnel;
import com.rbi.security.entity.web.entity.SysOrganization;
import com.rbi.security.entity.web.entity.SysRole;
import com.rbi.security.entity.web.hid.HidDangerDO;
import com.rbi.security.entity.web.hid.HidDangerProcessDO;
import com.rbi.security.entity.web.hid.SystemSettingDTO;
import com.rbi.security.tool.DateUtil;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.hid.HidDangerDAO;
import com.rbi.security.web.service.HidDangerService;
import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


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
@ConfigurationProperties(prefix="path")
@Data
@Service
public class HidDangerServiceImpl implements HidDangerService {
    private String hiddenPath;
    private String findHiddenPath;


    @Autowired
    HidDangerDAO hidDangerDAO;

    @Override
    public String addReport(HidDangerDO hidDangerDO, MultipartFile[] beforeImg, MultipartFile[] afterImg, MultipartFile plan, MultipartFile report) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        int personnelId  =  currentUser.getCompanyPersonnelId();
        int userId = currentUser.getId();

        SysCompanyPersonnel sysCompanyPersonnel = hidDangerDAO.findPersonnelById(personnelId);
        String idt = DateUtil.date(DateUtil.FORMAT_PATTERN);
        String hidDangerCode = DateUtil.timeStamp();

        hidDangerDO.setHidDangerCode(hidDangerCode);
        hidDangerDO.setCopyOrganizationId(123);
        hidDangerDO.setCopyOrganizationName("安防部");
        hidDangerDO.setHidDangerType(1);
        hidDangerDO.setIdt(idt);

        //排查前照片添加
        if (beforeImg.length > 6) {
            return "排查前照片数量不能大于6张";
        }
        if (beforeImg.length > 0) {
            for (int i = 0; i < beforeImg.length; i++) {
                String contentType = beforeImg[i].getContentType();
                if (contentType.startsWith("image")) {
                    String timestamps = DateUtil.timeStamp();
                    String newFileName = timestamps + new Random().nextInt() + ".jpg";
                    FileUtils.copyInputStreamToFile(beforeImg[i].getInputStream(), new File(hiddenPath, newFileName));
                    hidDangerDAO.addBeforeImg(hidDangerCode,findHiddenPath+newFileName);
                }
            }
        }
        //排查后照片添加
        if (afterImg.length > 6) {
            return "排查后照片数量不能大于6张";
        }
        if (afterImg.length > 0) {
            for (int i = 0; i < afterImg.length; i++) {
                String contentType = afterImg[i].getContentType();
                if (contentType.startsWith("image")) {
                    String timestamps = DateUtil.timeStamp();
                    String newFileName = timestamps + new Random().nextInt() + ".jpg";
                    FileUtils.copyInputStreamToFile(afterImg[i].getInputStream(), new File(hiddenPath, newFileName));
                    hidDangerDAO.addAfterImg(hidDangerCode,findHiddenPath+newFileName);
                }
            }
        }
        //整改方案
        if (plan!=null){
            String filename = plan.getOriginalFilename();
//            String fileType = filename.substring(filename.lastIndexOf(".")+1);
            String timestamps = DateUtil.timeStamp();
            String newFileName = timestamps + filename;
            System.out.println(newFileName);
            FileUtils.copyInputStreamToFile(plan.getInputStream(), new File(hiddenPath, newFileName));
            hidDangerDO.setRectificationPlan(findHiddenPath+newFileName);
        }
        if (report != null){
            String filename = report.getOriginalFilename();
            String timestamps = DateUtil.timeStamp();
            String newFileName = timestamps+ filename;
            FileUtils.copyInputStreamToFile(report.getInputStream(), new File(hiddenPath, newFileName));
            hidDangerDO.setAcceptanceReport(findHiddenPath+newFileName);
        }

//        进程表添加
        SysRole sysRole = hidDangerDAO.findRoleByUserId(userId);
        HidDangerProcessDO hidDangerProcessDO = new HidDangerProcessDO();
        SysOrganization sysOrganization = hidDangerDAO.findAllByOrganizationId(sysCompanyPersonnel.getOrganizationId());
        if (sysRole.getWhetherSee() == 1) {//判断角色权限等级
            //上报组织
            hidDangerProcessDO.setOrganizationId(sysOrganization.getParentId());
            SysOrganization sysOrganization2 = hidDangerDAO.findAllByOrganizationId(sysOrganization.getParentId());
            hidDangerProcessDO.setOrganizationName(sysOrganization2.getOrganizationName());
            //责任人
            SysCompanyPersonnel sysCompanyPersonnel1 = hidDangerDAO.findFirstUserByOrganizationId(sysOrganization2.getId());
            hidDangerProcessDO.setCorrectorId(sysCompanyPersonnel1.getId());
            hidDangerProcessDO.setCorrectorName(sysCompanyPersonnel1.getName());
        }else {
            //上报组织
            hidDangerProcessDO.setOrganizationId(sysOrganization.getId());
            hidDangerProcessDO.setOrganizationName(sysOrganization.getOrganizationName());
            SysCompanyPersonnel sysCompanyPersonnel1 = hidDangerDAO.findFirstUserByOrganizationId(sysCompanyPersonnel.getOrganizationId());
            //责任人
            hidDangerProcessDO.setCorrectorId(sysCompanyPersonnel1.getId());
            hidDangerProcessDO.setCorrectorName(sysCompanyPersonnel1.getName());
        }
        hidDangerProcessDO.setHidDangerCode(hidDangerCode);
        hidDangerProcessDO.setOperatorId(sysCompanyPersonnel.getId());
        hidDangerProcessDO.setOperatorName(sysCompanyPersonnel.getName());
        if (hidDangerDO.getIfDeal().equals("是")){ //判断是否能处理
            hidDangerProcessDO.setIfDeal("是");
            hidDangerProcessDO.setDealWay("处理");
            hidDangerDO.setCorrectorId(sysCompanyPersonnel.getId());
            hidDangerDO.setCorrectorName(sysCompanyPersonnel.getName());
            hidDangerDO.setProcessingStatus("4");//已处理待审核
        }else {
            hidDangerProcessDO.setIfDeal("否");
            hidDangerProcessDO.setDealWay("上报");
            hidDangerDO.setProcessingStatus("1");//上报
        }
        hidDangerProcessDO.setDealTime(idt);
        hidDangerProcessDO.setIdt(idt);
        hidDangerDAO.addProcess(hidDangerProcessDO);


        //隐患所属组织表
        SysOrganization sysOrganization2 = hidDangerDAO.findAllByOrganizationId(hidDangerDO.getOrganizationId());
        int level = sysOrganization2.getLevel();
        if (level == 4 ){
            hidDangerDO.setClassId(sysOrganization2.getId());
            hidDangerDO.setClassName(sysOrganization2.getOrganizationName());
        }
        if (level == 3 ){
            hidDangerDO.setWorkshopId(sysOrganization2.getId());
            hidDangerDO.setWorkshopName(sysOrganization2.getOrganizationName());
        }
        if (level == 2 ){
            hidDangerDO.setFactoryId(sysOrganization2.getId());
            hidDangerDO.setFactoryName(sysOrganization2.getOrganizationName());
        }
        if (level == 1 ){
            hidDangerDO.setCompanyId(sysOrganization2.getId());
            hidDangerDO.setCompanyName(sysOrganization2.getOrganizationName());
        }
        Integer parentId = sysOrganization2.getParentId();
        level = level -1;
        while (level !=0){
            SysOrganization sysOrganization3 = hidDangerDAO.findAllByOrganizationId(parentId);
            if (level == 3 ){
                hidDangerDO.setWorkshopId(sysOrganization3.getId());
                hidDangerDO.setWorkshopName(sysOrganization3.getOrganizationName());
            }
            if (level == 2 ){
                hidDangerDO.setFactoryId(sysOrganization3.getId());
                hidDangerDO.setFactoryName(sysOrganization3.getOrganizationName());
            }
            if (level == 1 ){
                hidDangerDO.setCompanyId(sysOrganization3.getId());
                hidDangerDO.setCompanyName(sysOrganization3.getOrganizationName());
            }
            parentId = sysOrganization3.getParentId();
            level=level - 1;
        }
        hidDangerDAO.addHidDanger(hidDangerDO);
        return "1000";
    }

    @Override
    public Map<String, Object> findAdmChoose(JSONArray array) {
            Map<String,Object> map = new HashMap<>();
            for (int i=0;i<array.size();i++){
                System.out.println(array.get(i).toString());
                JSONObject json = JSON.parseObject(array.get(i).toString());
                String settingType = json.getString("settingType");
                List<SystemSettingDTO> systemSettingDTOS = hidDangerDAO.findChoose(settingType);
                map.put(settingType,systemSettingDTOS);
            }
            return map;
    }

    @Override
    public String addOrder(HidDangerDO hidDangerDO, MultipartFile[] beforeImg, MultipartFile notice) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        int personnelId  =  currentUser.getCompanyPersonnelId();
//        int userId = currentUser.getId();
        SysCompanyPersonnel sysCompanyPersonnel = hidDangerDAO.findPersonnelById(personnelId);
        String idt = DateUtil.date(DateUtil.FORMAT_PATTERN);
        hidDangerDO.setCopyOrganizationId(123);
        hidDangerDO.setCopyOrganizationName("安防部");
        hidDangerDO.setHidDangerType(2);
        String hidDangerCode = DateUtil.timeStamp();
        hidDangerDO.setIdt(idt);
        hidDangerDO.setHidDangerCode(hidDangerCode);

        //排查前照片添加
        if (beforeImg.length > 6) {
            return "排查前照片数量不能大于6张";
        }
        if (beforeImg.length > 0) {
            for (int i = 0; i < beforeImg.length; i++) {
                String contentType = beforeImg[i].getContentType();
                if (contentType.startsWith("image")) {
                    String timestamps = DateUtil.timeStamp();
                    String newFileName = timestamps + new Random().nextInt() + ".jpg";
                    FileUtils.copyInputStreamToFile(beforeImg[i].getInputStream(), new File(hiddenPath, newFileName));
                    hidDangerDAO.addBeforeImg(hidDangerCode,findHiddenPath+newFileName);
                }
            }
        }
        //整改通知书附件
        if (notice!=null){
            String filename = notice.getOriginalFilename();
//            String fileType = filename.substring(filename.lastIndexOf(".")+1);
            String timestamps = DateUtil.timeStamp();
            String newFileName = timestamps + filename;
            System.out.println(newFileName);
            FileUtils.copyInputStreamToFile(notice.getInputStream(), new File(hiddenPath, newFileName));
            hidDangerDO.setRectificationNoticeAnnex(findHiddenPath+newFileName);
        }

//        进程表添加
        HidDangerProcessDO hidDangerProcessDO = new HidDangerProcessDO();
        hidDangerProcessDO.setHidDangerCode(hidDangerCode);
        hidDangerProcessDO.setOperatorId(sysCompanyPersonnel.getId());
        hidDangerProcessDO.setOperatorName(sysCompanyPersonnel.getName());
        //整改组织
        hidDangerProcessDO.setOrganizationId(hidDangerDO.getRectificationUnitId());
        hidDangerProcessDO.setOrganizationName(hidDangerDO.getRectificationUnitName());
        //整改负责人
        SysCompanyPersonnel sysCompanyPersonnel1 = hidDangerDAO.findFirstUserByOrganizationId(hidDangerDO.getRectificationUnitId());
        hidDangerProcessDO.setCorrectorId(sysCompanyPersonnel1.getId());
        hidDangerProcessDO.setCorrectorName(sysCompanyPersonnel1.getName());
        hidDangerProcessDO.setIfDeal("");
        hidDangerProcessDO.setDealWay("责令整改");
        hidDangerProcessDO.setDealTime(idt);//提交时间
        hidDangerProcessDO.setIdt(idt);
        hidDangerDAO.addProcess(hidDangerProcessDO);

        //隐患所属组织表
        SysOrganization sysOrganization2 = hidDangerDAO.findAllByOrganizationId(hidDangerDO.getOrganizationId());
        int level = sysOrganization2.getLevel();
        if (level == 4 ){
            hidDangerDO.setClassId(sysOrganization2.getId());
            hidDangerDO.setClassName(sysOrganization2.getOrganizationName());
        }
        if (level == 3 ){
            hidDangerDO.setWorkshopId(sysOrganization2.getId());
            hidDangerDO.setWorkshopName(sysOrganization2.getOrganizationName());
        }
        if (level == 2 ){
            hidDangerDO.setFactoryId(sysOrganization2.getId());
            hidDangerDO.setFactoryName(sysOrganization2.getOrganizationName());
        }
        if (level == 1 ){
            hidDangerDO.setCompanyId(sysOrganization2.getId());
            hidDangerDO.setCompanyName(sysOrganization2.getOrganizationName());
        }
        Integer parentId = sysOrganization2.getParentId();
        level = level -1;
        while (level !=0){
            SysOrganization sysOrganization3 = hidDangerDAO.findAllByOrganizationId(parentId);
            if (level == 3 ){
                hidDangerDO.setWorkshopId(sysOrganization3.getId());
                hidDangerDO.setWorkshopName(sysOrganization3.getOrganizationName());
            }
            if (level == 2 ){
                hidDangerDO.setFactoryId(sysOrganization3.getId());
                hidDangerDO.setFactoryName(sysOrganization3.getOrganizationName());
            }
            if (level == 1 ){
                hidDangerDO.setCompanyId(sysOrganization3.getId());
                hidDangerDO.setCompanyName(sysOrganization3.getOrganizationName());
            }
            parentId = sysOrganization3.getParentId();
            level=level - 1;
        }
        hidDangerDAO.addHidDanger(hidDangerDO);
        return "1000";
    }

    @Override
    public PageData findDealByPage(int pageNo, int pageSize) {
        int pageNo2 = pageSize * (pageNo - 1);
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        int personnelId  =  currentUser.getCompanyPersonnelId();
        int userId = currentUser.getId();
        SysCompanyPersonnel sysCompanyPersonnel = hidDangerDAO.findPersonnelById(personnelId);
        int organizationId = sysCompanyPersonnel.getOrganizationId();
        SysOrganization sysOrganization = hidDangerDAO.findAllByOrganizationId(organizationId);
        int level = sysOrganization.getLevel();
        Integer parentId = sysOrganization.getParentId();
        Integer companyId = null;
        while (level !=1){
            SysOrganization sysOrganization3 = hidDangerDAO.findAllByOrganizationId(parentId);
            parentId = sysOrganization3.getParentId();
            level=level - 1;
            companyId = sysOrganization3.getId();
        }
        SysRole sysRole = hidDangerDAO.findRoleByUserId(userId);
        if (sysRole.getWhetherSee() == 0){
            List<HidDangerDO> hidDangerDOS = hidDangerDAO.findAllDealHidByPage(companyId,pageNo2,pageSize);
            int totalPage = 0;
            int count = hidDangerDAO.findAllDealHidByPageNum(companyId);
            if (0 == count % pageSize) {
                totalPage = count / pageSize;
            } else {
                totalPage = count / pageSize + 1;
            }
            return new PageData(pageNo, pageSize, totalPage, count, hidDangerDOS);
        }else {
            List<HidDangerDO> hidDangerDOS = hidDangerDAO.findPersonnelDealByPage(userId,pageNo2,pageSize);
            int totalPage = 0;
            int count = hidDangerDAO.findPersonnelDealByPageNum(userId);
            if (0 == count % pageSize) {
                totalPage = count / pageSize;
            } else {
                totalPage = count / pageSize + 1;
            }
            return new PageData(pageNo, pageSize, totalPage, count, hidDangerDOS);
        }
    }

    @Override
    public PageData findFinishByPage(int pageNo, int pageSize) {
        int pageNo2 = pageSize * (pageNo - 1);
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        int personnelId  =  currentUser.getCompanyPersonnelId();
        int userId = currentUser.getId();
        SysCompanyPersonnel sysCompanyPersonnel = hidDangerDAO.findPersonnelById(personnelId);
        int organizationId = sysCompanyPersonnel.getOrganizationId();
        SysOrganization sysOrganization = hidDangerDAO.findAllByOrganizationId(organizationId);
        int level = sysOrganization.getLevel();
        Integer parentId = sysOrganization.getParentId();
        Integer companyId = null;
        while (level !=1){
            SysOrganization sysOrganization3 = hidDangerDAO.findAllByOrganizationId(parentId);
            parentId = sysOrganization3.getParentId();
            level=level - 1;
            companyId = sysOrganization3.getId();
        }
        SysRole sysRole = hidDangerDAO.findRoleByUserId(userId);
        if (sysRole.getWhetherSee() == 0){
            List<HidDangerDO> hidDangerDOS = hidDangerDAO.findAllFinishHidByPage(companyId,pageNo2,pageSize);
            int totalPage = 0;
            int count = hidDangerDAO.findAllFinishHidByPageNum(companyId);
            if (0 == count % pageSize) {
                totalPage = count / pageSize;
            } else {
                totalPage = count / pageSize + 1;
            }
            return new PageData(pageNo, pageSize, totalPage, count, hidDangerDOS);
        }else {
            List<HidDangerDO> hidDangerDOS = hidDangerDAO.findPersonnelFinishByPage(userId,pageNo2,pageSize);
            int totalPage = 0;
            int count = hidDangerDAO.findPersonnelFinishByPageNum(userId);
            if (0 == count % pageSize) {
                totalPage = count / pageSize;
            } else {
                totalPage = count / pageSize + 1;
            }
            return new PageData(pageNo, pageSize, totalPage, count, hidDangerDOS);
        }
    }


}
