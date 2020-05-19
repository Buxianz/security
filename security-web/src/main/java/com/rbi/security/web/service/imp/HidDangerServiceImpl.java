package com.rbi.security.web.service.imp;


import com.rbi.security.entity.web.entity.SysCompanyPersonnel;
import com.rbi.security.entity.web.entity.SysOrganization;
import com.rbi.security.entity.web.entity.SysRole;
import com.rbi.security.entity.web.hid.HidDangerDTO;
import com.rbi.security.entity.web.hid.HidDangerProcessDTO;
import com.rbi.security.tool.DateUtil;
import com.rbi.security.web.DAO.hid.HidDangerDAO;
import com.rbi.security.web.service.HidDangerService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Random;

@Service
public class HidDangerServiceImpl implements HidDangerService {
    @Autowired
    HidDangerDAO hidDangerDAO;

    @Override
    public String addReport(int userId, HidDangerDTO hidDangerDTO, MultipartFile[] beforeImg, MultipartFile[] afterImg, MultipartFile plan, MultipartFile report) throws IOException {
        SysCompanyPersonnel sysCompanyPersonnel = hidDangerDAO.findPersonnelByUserId(userId);
//        hidDangerDTO.setCopyOrganizationId(123);
//        hidDangerDTO.setCopyOrganizationName("安防部");
//
//        String hidDangerCode = DateUtil.timeStamp();
//        //排查前照片添加
//        if (beforeImg.length > 6) {
//            return "排查前照片数量不能大于6张";
//        }
//        if (beforeImg.length > 0) {
//            for (int i = 0; i < beforeImg.length; i++) {
//                String contentType = beforeImg[i].getContentType();
//                if (contentType.startsWith("image")) {
//                    String timestamps = DateUtil.timeStamp();
//                    String newFileName = timestamps + "" + new Random().nextInt() + ".jpg";
//                    FileUtils.copyInputStreamToFile(beforeImg[i].getInputStream(), new File("D:", newFileName));
//                    hidDangerDAO.addBeforeImg(hidDangerCode,newFileName);
//                }
//            }
//        }
//        //排查后照片添加
//        if (afterImg.length > 6) {
//            return "排查后照片数量不能大于6张";
//        }
//        if (afterImg.length > 0) {
//            for (int i = 0; i < afterImg.length; i++) {
//                String contentType = afterImg[i].getContentType();
//                if (contentType.startsWith("image")) {
//                    String timestamps = DateUtil.timeStamp();
//                    String newFileName = timestamps + "" + new Random().nextInt() + ".jpg";
//                    FileUtils.copyInputStreamToFile(afterImg[i].getInputStream(), new File("D:", newFileName));
//                    hidDangerDAO.addBeforeImg(hidDangerCode,newFileName);
//                    hidDangerDAO.addAfterImg(hidDangerCode,newFileName);
//                }
//            }
//        }
//        //整改方案
//        if (plan!=null){
//            String filename = plan.getOriginalFilename();
//            System.out.println("************************"+filename);
//            String timestamps = DateUtil.timeStamp();
//            String newFileName = timestamps + "" + new Random().nextInt() + "filename";
//            FileUtils.copyInputStreamToFile(plan.getInputStream(), new File("D:", newFileName));
//        }
//        if (report != null){
//            String filename = plan.getOriginalFilename();
//            System.out.println("************************"+filename);
//            String timestamps = DateUtil.timeStamp();
//            String newFileName = timestamps + "" + new Random().nextInt() + "filename";
//            FileUtils.copyInputStreamToFile(plan.getInputStream(), new File("D:", newFileName));
//        }
//
////        进程表添加
//        SysRole sysRole = hidDangerDAO.findRoleByUserId(userId);
//        HidDangerProcessDTO hidDangerProcessDTO = new HidDangerProcessDTO();
//        SysOrganization sysOrganization = hidDangerDAO.findAllByOrganizationId(sysCompanyPersonnel.getOrganizationId());
//        if (sysRole.getWhetherSee() == 1) {//判断角色权限等级
//            hidDangerProcessDTO.setReportOrganizationId(sysOrganization.getParentId());
//            SysOrganization sysOrganization2 = hidDangerDAO.findAllByOrganizationId(sysOrganization.getParentId());
//            hidDangerProcessDTO.setReportOrganizationName(sysOrganization2.getOrganizationName());
//        }else {
//            hidDangerProcessDTO.setReportOrganizationId(sysOrganization.getId());
//            hidDangerProcessDTO.setReportOrganizationName(sysOrganization.getOrganizationName());
//        }
//        String idt = DateUtil.date(DateUtil.FORMAT_PATTERN);
//        hidDangerProcessDTO.setHidDangerCode(hidDangerCode);
//        hidDangerProcessDTO.setReportPersonId(sysCompanyPersonnel.getId());
//        hidDangerProcessDTO.setReportPersonName(sysCompanyPersonnel.getName());
//        if (hidDangerDTO.getIfDeal() == 1){ //判断是否能处理
//            hidDangerProcessDTO.setIfDeal(1);
//            hidDangerProcessDTO.setDealWay(1);
//        }else {
//            hidDangerProcessDTO.setIfDeal(0);
//            hidDangerProcessDTO.setDealWay(2);
//        }
//        hidDangerProcessDTO.setDealTime(hidDangerDTO.getCompletionTime());
//        hidDangerProcessDTO.setIdt(idt);
//        hidDangerDAO.addProcess(hidDangerProcessDTO);
//
//        hidDangerDAO.addHidDanger(hidDangerDTO);
        return "1000";
    }
}
