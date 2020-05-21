package com.rbi.security.web.service.imp;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.entity.SysCompanyPersonnel;
import com.rbi.security.entity.web.entity.SysOrganization;
import com.rbi.security.entity.web.entity.SysRole;
import com.rbi.security.entity.web.hid.HidDangerDTO;
import com.rbi.security.entity.web.hid.HidDangerProcessDTO;
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

@ConfigurationProperties(prefix="path")
@Data
@Service
public class HidDangerServiceImpl implements HidDangerService {
    private String hiddenPath;
    private String findHiddenPath;


    @Autowired
    HidDangerDAO hidDangerDAO;

    @Override
    public String addReport(HidDangerDTO hidDangerDTO, MultipartFile[] beforeImg, MultipartFile[] afterImg, MultipartFile plan, MultipartFile report) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        int personnelId  =  currentUser.getCompanyPersonnelId();
        int userId = currentUser.getId();

        SysCompanyPersonnel sysCompanyPersonnel = hidDangerDAO.findPersonnelById(personnelId);
        String idt = DateUtil.date(DateUtil.FORMAT_PATTERN);
        String hidDangerCode = DateUtil.timeStamp();

        hidDangerDTO.setHidDangerCode(hidDangerCode);
        hidDangerDTO.setCopyOrganizationId(123);
        hidDangerDTO.setCopyOrganizationName("安防部");
        hidDangerDTO.setHidDangerType(1);
        hidDangerDTO.setIdt(idt);

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
            hidDangerDTO.setRectificationPlan(findHiddenPath+newFileName);
        }
        if (report != null){
            String filename = report.getOriginalFilename();
            String timestamps = DateUtil.timeStamp();
            String newFileName = timestamps+ filename;
            FileUtils.copyInputStreamToFile(report.getInputStream(), new File(hiddenPath, newFileName));
            hidDangerDTO.setAcceptanceReport(findHiddenPath+newFileName);
        }

//        进程表添加
        SysRole sysRole = hidDangerDAO.findRoleByUserId(userId);
        HidDangerProcessDTO hidDangerProcessDTO = new HidDangerProcessDTO();
        SysOrganization sysOrganization = hidDangerDAO.findAllByOrganizationId(sysCompanyPersonnel.getOrganizationId());
        if (sysRole.getWhetherSee() == 1) {//判断角色权限等级
            //上报组织
            hidDangerProcessDTO.setOrganizationId(sysOrganization.getParentId());
            SysOrganization sysOrganization2 = hidDangerDAO.findAllByOrganizationId(sysOrganization.getParentId());
            hidDangerProcessDTO.setOrganizationName(sysOrganization2.getOrganizationName());
            //责任人
            SysCompanyPersonnel sysCompanyPersonnel1 = hidDangerDAO.findFirstUserByOrganizationId(sysOrganization2.getId());
            hidDangerProcessDTO.setCorrectorId(sysCompanyPersonnel1.getId());
            hidDangerProcessDTO.setCorrectorName(sysCompanyPersonnel1.getName());
        }else {
            //上报组织
            hidDangerProcessDTO.setOrganizationId(sysOrganization.getId());
            hidDangerProcessDTO.setOrganizationName(sysOrganization.getOrganizationName());
            SysCompanyPersonnel sysCompanyPersonnel1 = hidDangerDAO.findFirstUserByOrganizationId(sysCompanyPersonnel.getOrganizationId());
            //责任人
            hidDangerProcessDTO.setCorrectorId(sysCompanyPersonnel1.getId());
            hidDangerProcessDTO.setCorrectorName(sysCompanyPersonnel1.getName());
        }
        hidDangerProcessDTO.setHidDangerCode(hidDangerCode);
        hidDangerProcessDTO.setOperatorId(sysCompanyPersonnel.getId());
        hidDangerProcessDTO.setOperatorName(sysCompanyPersonnel.getName());
        if (hidDangerDTO.getIfDeal().equals("是")){ //判断是否能处理
            hidDangerProcessDTO.setIfDeal("是");
            hidDangerProcessDTO.setDealWay("处理");
            hidDangerDTO.setCorrectorId(sysCompanyPersonnel.getId());
            hidDangerDTO.setCorrectorName(sysCompanyPersonnel.getName());
            hidDangerDTO.setProcessingStatus("4");//已处理待审核
        }else {
            hidDangerProcessDTO.setIfDeal("否");
            hidDangerProcessDTO.setDealWay("上报");
            hidDangerDTO.setProcessingStatus("1");//上报
        }
        hidDangerProcessDTO.setDealTime(idt);
        hidDangerProcessDTO.setIdt(idt);
        hidDangerDAO.addProcess(hidDangerProcessDTO);
        //隐患所属组织表
        SysOrganization sysOrganization2 = hidDangerDAO.findAllByOrganizationId(hidDangerDTO.getOrganizationId());
        int level = sysOrganization2.getLevel();
        hidDangerDAO.addOrganization(hidDangerCode,sysOrganization2.getId(),sysOrganization2.getOrganizationName(),sysOrganization2.getLevel());
        Integer parentId = sysOrganization2.getParentId();
        while (level !=1){
            SysOrganization sysOrganization3 = hidDangerDAO.findAllByOrganizationId(parentId);
            hidDangerDAO.addOrganization(hidDangerCode,sysOrganization3.getId(),sysOrganization3.getOrganizationName(),sysOrganization3.getLevel());
            parentId = sysOrganization3.getParentId();
            level=level - 1;
        }
        hidDangerDAO.addHidDanger(hidDangerDTO);
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
    public String addOrder(HidDangerDTO hidDangerDTO, MultipartFile[] beforeImg, MultipartFile notice) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        int personnelId  =  currentUser.getCompanyPersonnelId();
//        int userId = currentUser.getId();
        SysCompanyPersonnel sysCompanyPersonnel = hidDangerDAO.findPersonnelById(personnelId);
        String idt = DateUtil.date(DateUtil.FORMAT_PATTERN);
        hidDangerDTO.setCopyOrganizationId(123);
        hidDangerDTO.setCopyOrganizationName("安防部");
        hidDangerDTO.setHidDangerType(2);
        String hidDangerCode = DateUtil.timeStamp();
        hidDangerDTO.setIdt(idt);
        hidDangerDTO.setHidDangerCode(hidDangerCode);

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
            hidDangerDTO.setRectificationNoticeAnnex(findHiddenPath+newFileName);
        }

//        进程表添加
        HidDangerProcessDTO hidDangerProcessDTO = new HidDangerProcessDTO();
        hidDangerProcessDTO.setHidDangerCode(hidDangerCode);
        hidDangerProcessDTO.setOperatorId(sysCompanyPersonnel.getId());
        hidDangerProcessDTO.setOperatorName(sysCompanyPersonnel.getName());
        //整改组织
        hidDangerProcessDTO.setOrganizationId(hidDangerDTO.getRectificationUnitId());
        hidDangerProcessDTO.setOrganizationName(hidDangerDTO.getRectificationUnitName());
        //整改负责人
        SysCompanyPersonnel sysCompanyPersonnel1 = hidDangerDAO.findFirstUserByOrganizationId(hidDangerDTO.getRectificationUnitId());
        hidDangerProcessDTO.setCorrectorId(sysCompanyPersonnel1.getId());
        hidDangerProcessDTO.setCorrectorName(sysCompanyPersonnel1.getName());
        hidDangerProcessDTO.setIfDeal("");
        hidDangerProcessDTO.setDealWay("责令整改");
        hidDangerProcessDTO.setDealTime(idt);//提交时间
        hidDangerProcessDTO.setIdt(idt);
        hidDangerDAO.addProcess(hidDangerProcessDTO);

        //隐患所属组织表
        SysOrganization sysOrganization2 = hidDangerDAO.findAllByOrganizationId(hidDangerDTO.getOrganizationId());
        int level = sysOrganization2.getLevel();
        hidDangerDAO.addOrganization(hidDangerCode,sysOrganization2.getId(),sysOrganization2.getOrganizationName(),sysOrganization2.getLevel());
        Integer parentId = sysOrganization2.getParentId();
        while (level !=1){
            SysOrganization sysOrganization3 = hidDangerDAO.findAllByOrganizationId(parentId);
            hidDangerDAO.addOrganization(hidDangerCode,sysOrganization3.getId(),sysOrganization3.getOrganizationName(),sysOrganization3.getLevel());
            parentId = sysOrganization3.getParentId();
            level=level - 1;
        }
        hidDangerDAO.addHidDanger(hidDangerDTO);
        return "1000";
    }

    @Override
    public PageData findDealByPage(int pageNo, int pageSize) {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        int personnelId  =  currentUser.getCompanyPersonnelId();
        int userId = currentUser.getId();


        return null;
    }


}
