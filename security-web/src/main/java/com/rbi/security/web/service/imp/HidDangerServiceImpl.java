package com.rbi.security.web.service.imp;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.entity.SysCompanyPersonnel;
import com.rbi.security.entity.web.entity.SysOrganization;
import com.rbi.security.entity.web.entity.SysRole;
import com.rbi.security.entity.web.hid.*;
import com.rbi.security.tool.DateUtil;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.StringUtils;
import com.rbi.security.web.DAO.hid.HidDangerDAO;
import com.rbi.security.web.service.HidDangerService;
import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;


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

@Service
public class HidDangerServiceImpl implements HidDangerService {
    @Value("${uploadfile.ip}")
    private String fileIp;
    @Value("${hiddenPath}")
    private String hiddenPath;
    @Value("${path2}")
    private String path;



    @Autowired
    HidDangerDAO hidDangerDAO;

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public String addReport(HidDangerDO hidDangerDO, MultipartFile[] beforeImg, MultipartFile[] afterImg, MultipartFile plan, MultipartFile report) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        Integer userId = currentUser.getId();
        try {
            SysCompanyPersonnel sysCompanyPersonnel = hidDangerDAO.findPersonnelById(personnelId);
            String idt = DateUtil.date(DateUtil.FORMAT_PATTERN);
            String hidDangerCode = DateUtil.timeStamp();
            hidDangerDO.setHidDangerCode(hidDangerCode);
            hidDangerDO.setCopyOrganizationId(123);
            hidDangerDO.setCopyOrganizationName("安防部");
            hidDangerDO.setHidDangerType("1");
            hidDangerDO.setIdt(idt);
            //排查前照片添加
            if (beforeImg != null){
                if (beforeImg.length > 6) {
                    return "排查前照片数量不能大于6张";
                }
                if (beforeImg.length > 0) {
                    for (int i = 0; i < beforeImg.length; i++) {
                        String contentType = beforeImg[i].getContentType();
                        if (contentType.startsWith("image")) {
                            String timestamps = DateUtil.timeStamp();
                            String newFileName = timestamps + new Random().nextInt() + ".jpg";
                            FileUtils.copyInputStreamToFile(beforeImg[i].getInputStream(), new File(path+hiddenPath, newFileName));
                            hidDangerDAO.addBeforeImg(hidDangerCode,hiddenPath+newFileName);
                        }
                    }
                }
            }

            if (afterImg!=null){
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
                            FileUtils.copyInputStreamToFile(afterImg[i].getInputStream(), new File(path+hiddenPath, newFileName));
                            hidDangerDAO.addAfterImg(hidDangerCode,hiddenPath+newFileName);
                        }
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
                FileUtils.copyInputStreamToFile(plan.getInputStream(), new File(path+hiddenPath, newFileName));
                hidDangerDO.setRectificationPlan(hiddenPath+newFileName);
            }
            if (report != null){
                String filename = report.getOriginalFilename();
                String timestamps = DateUtil.timeStamp();
                String newFileName = timestamps+ filename;
                FileUtils.copyInputStreamToFile(report.getInputStream(), new File(path+hiddenPath, newFileName));
                hidDangerDO.setAcceptanceReport(hiddenPath+newFileName);
            }
//        进程表添加
            SysRole sysRole = hidDangerDAO.findRoleByUserId(userId);
            HidDangerProcessDO hidDangerProcessDO = new HidDangerProcessDO();
            SysOrganization sysOrganization = hidDangerDAO.findAllByOrganizationId(sysCompanyPersonnel.getOrganizationId());
            if (sysRole.getLevel() == 1) {//判断角色权限等级
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
            //操作人信息
            hidDangerProcessDO.setOperatorId(sysCompanyPersonnel.getId());
            hidDangerProcessDO.setOperatorName(sysCompanyPersonnel.getName());
            hidDangerProcessDO.setOperatorOrganizationId(sysOrganization.getId());
            hidDangerProcessDO.setOperatorOrganizationName(sysOrganization.getOrganizationName());
            //处理方式
            if (hidDangerDO.getIfDeal().equals("是")){ //判断是否能处理
                hidDangerProcessDO.setIfDeal("是");
                hidDangerProcessDO.setDealWay("处理");
                hidDangerDO.setCorrectorId(sysCompanyPersonnel.getId());
                hidDangerDO.setCorrectorName(sysCompanyPersonnel.getName());
                hidDangerDO.setIfRectificationPlan("有");
                hidDangerDO.setIfControlMeasures("有");
                hidDangerDO.setProcessingStatus("4");//已处理待审核
            }else {
                hidDangerProcessDO.setIfDeal("否");
                hidDangerProcessDO.setDealWay("上报");
                hidDangerDO.setIfRectificationPlan("无");
                hidDangerDO.setIfControlMeasures("无");
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
        }catch (NullPointerException e){
            return "没有创建完整的单位和对应的角色用户";
        }catch (NumberFormatException e){
            return "数据格式错误";
        }catch (IndexOutOfBoundsException e){
            return "数组溢出，配置了多个上级负责人";
        }

    }


    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public String addOrder(HidDangerDO hidDangerDO, MultipartFile[] beforeImg, MultipartFile notice) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
//        Integer userId = currentUser.getId();
        try {
            SysCompanyPersonnel sysCompanyPersonnel = hidDangerDAO.findPersonnelById(personnelId);
            SysOrganization sysOrganization = hidDangerDAO.findAllByOrganizationId(sysCompanyPersonnel.getOrganizationId());


            Integer personnelOrganizationId = sysCompanyPersonnel.getOrganizationId();
            List<ListOrganizationIds> organizationIds = hidDangerDAO.findSonOrganizationIds(personnelOrganizationId);
            //判断隐患发生单位是否符合规范
            Integer organizationId = hidDangerDO.getOrganizationId();
            int organizationIdNum = 0;
            if (organizationIds != null){
                for (int i=0;i<organizationIds.size();i++){
                    if (organizationIds.get(i).getOrganizationId() == organizationId){
                        organizationIdNum = 1;
                    }
                }
            }
            if (organizationIdNum == 0){
                return "隐患发生单位只能选择下级所属单位";
            }
            //判断整改单位是否符合规范
            Integer RetOrganizationId = hidDangerDO.getRectificationUnitId();
            int ret = 0;
            if (organizationIds != null){
                for (int i=0;i<organizationIds.size();i++){
                    if (organizationIds.get(i).getOrganizationId() == RetOrganizationId){
                        ret = 1;
                    }
                }
            }
            if (ret == 0){
                return "整改单位只能选择下级所属单位";
            }

            String idt = DateUtil.date(DateUtil.FORMAT_PATTERN);
            hidDangerDO.setCopyOrganizationId(123);
            hidDangerDO.setCopyOrganizationName("安防部");
            hidDangerDO.setHidDangerType("2");
            hidDangerDO.setIfRectificationPlan("无");
            hidDangerDO.setIfControlMeasures("无");
            String hidDangerCode = DateUtil.timeStamp();
            hidDangerDO.setIdt(idt);
            hidDangerDO.setRectificationNoticeTime(idt);
            hidDangerDO.setHidDangerCode(hidDangerCode);
            hidDangerDO.setProcessingStatus("2");

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
                        FileUtils.copyInputStreamToFile(beforeImg[i].getInputStream(), new File(path+hiddenPath, newFileName));
                        hidDangerDAO.addBeforeImg(hidDangerCode,hiddenPath+newFileName);
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
                FileUtils.copyInputStreamToFile(notice.getInputStream(), new File(path+hiddenPath, newFileName));
                hidDangerDO.setRectificationNoticeAnnex(hiddenPath+newFileName);
            }

//        进程表添加
            ////组织查询
            HidDangerProcessDO hidDangerProcessDO = new HidDangerProcessDO();
            hidDangerProcessDO.setHidDangerCode(hidDangerCode);
            //操作人信息
            hidDangerProcessDO.setOperatorId(sysCompanyPersonnel.getId());
            hidDangerProcessDO.setOperatorName(sysCompanyPersonnel.getName());
            hidDangerProcessDO.setOperatorOrganizationId(sysOrganization.getId());
            hidDangerProcessDO.setOperatorOrganizationName(sysOrganization.getOrganizationName());
            //整改组织
            hidDangerProcessDO.setOrganizationId(hidDangerDO.getRectificationUnitId());
            hidDangerProcessDO.setOrganizationName(hidDangerDO.getRectificationUnitName());
            //整改负责人
            SysCompanyPersonnel sysCompanyPersonnel2 = hidDangerDAO.findFirstUserByOrganizationId(hidDangerDO.getRectificationUnitId());
            hidDangerProcessDO.setCorrectorId(sysCompanyPersonnel2.getId());
            hidDangerProcessDO.setCorrectorName(sysCompanyPersonnel2.getName());
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
        }catch (NullPointerException e){
            return "没有创建完整的单位和对应的角色用户";
        }catch (NumberFormatException e){
            return "数据格式错误";
        }catch (IndexOutOfBoundsException e){
            return "数组溢出，配置了多个上级负责人";
        }
    }

    @Override
    public PageData findDealByPage(int pageNo, int pageSize) {
        try {
            int pageNo2 = pageSize * (pageNo - 1);
            Subject subject = SecurityUtils.getSubject();
            AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
            Integer personnelId  =  currentUser.getCompanyPersonnelId();
            Integer userId = currentUser.getId();
            SysCompanyPersonnel sysCompanyPersonnel = hidDangerDAO.findPersonnelById(personnelId);
            SysOrganization sysOrganization = hidDangerDAO.findAllByOrganizationId(sysCompanyPersonnel.getOrganizationId());

            int level = sysOrganization.getLevel();
            Integer parentId = sysOrganization.getParentId();
            Integer companyId = sysOrganization.getId();
            while (level !=1){
                SysOrganization sysOrganization3 = hidDangerDAO.findAllByOrganizationId(parentId);
                parentId = sysOrganization3.getParentId();
                level=level - 1;
                companyId = sysOrganization3.getId();
            }
            SysRole sysRole = hidDangerDAO.findRoleByUserId(userId);
            if (sysRole.getWhetherSee() == 1){
                List<HidDangerDO> hidDangerDOS = hidDangerDAO.findAllDealHidByPage(companyId,pageNo2,pageSize);
                for (int i = 0; i<hidDangerDOS.size(); i++){
                    List<HidDangerProcessDO> hidDangerProcessDOS = hidDangerDAO.findProcessByHidDangerCode(hidDangerDOS.get(i).getHidDangerCode());
                    if (hidDangerProcessDOS.get(hidDangerProcessDOS.size()-1).getCorrectorId().intValue() == personnelId.intValue()){
                        hidDangerDOS.get(i).setColor("#D80000");
                    }else {
                        hidDangerDOS.get(i).setColor("#3B86FF");
                    }
                }
                int totalPage = 0;
                int count = hidDangerDAO.findAllDealHidByPageNum(companyId);
                if (0 == count % pageSize) {
                    totalPage = count / pageSize;
                } else {
                    totalPage = count / pageSize + 1;
                }
                return new PageData(pageNo, pageSize, totalPage, count, hidDangerDOS);
            }else {
                List<HidDangerDO> hidDangerDOS = hidDangerDAO.findPersonnelDealByPage(personnelId,pageNo2,pageSize);
                for (int i = 0; i<hidDangerDOS.size(); i++){
                    List<HidDangerProcessDO> hidDangerProcessDOS = hidDangerDAO.findProcessByHidDangerCode(hidDangerDOS.get(i).getHidDangerCode());
                    if (hidDangerProcessDOS.get(hidDangerProcessDOS.size()-1).getCorrectorId().intValue() == personnelId.intValue()){
                        hidDangerDOS.get(i).setColor("#D80000");
                    }else {
                        hidDangerDOS.get(i).setColor("#3B86FF");
                    }
                }
                int totalPage = 0;
                int count = hidDangerDAO.findPersonnelDealByPageNum(personnelId);
                if (0 == count % pageSize) {
                    totalPage = count / pageSize;
                } else {
                    totalPage = count / pageSize + 1;
                }
                return new PageData(pageNo, pageSize, totalPage, count, hidDangerDOS);
            }
        }catch (NullPointerException e){
            return null;
        }

    }

    @Override
    public PageData findFinishByPage(int pageNo, int pageSize) {
        try {
            int pageNo2 = pageSize * (pageNo - 1);
            Subject subject = SecurityUtils.getSubject();
            AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
            Integer personnelId  =  currentUser.getCompanyPersonnelId();
            Integer userId = currentUser.getId();

            SysCompanyPersonnel sysCompanyPersonnel = hidDangerDAO.findPersonnelById(personnelId);
            int organizationId = sysCompanyPersonnel.getOrganizationId();
            SysOrganization sysOrganization = hidDangerDAO.findAllByOrganizationId(organizationId);
            int level = sysOrganization.getLevel();
            Integer parentId = sysOrganization.getParentId();
            Integer companyId = sysOrganization.getId();
            while (level !=1){
                SysOrganization sysOrganization3 = hidDangerDAO.findAllByOrganizationId(parentId);
                parentId = sysOrganization3.getParentId();
                level=level - 1;
                companyId = sysOrganization3.getId();
            }
            SysRole sysRole = hidDangerDAO.findRoleByUserId(userId);
            if (sysRole.getWhetherSee() == 1){
                List<HidDangerDO> hidDangerDOS = hidDangerDAO.findAllFinishHidByPage(companyId,pageNo2,pageSize);
                for (int i = 0;i<hidDangerDOS.size();i++){
                    if (hidDangerDOS.get(i).getProcessingStatus().equals("5")){
                        hidDangerDOS.get(i).setProcessingStatus("审核通过");
                    }
                }
                int totalPage = 0;
                int count = hidDangerDAO.findAllFinishHidByPageNum(companyId);
                if (0 == count % pageSize) {
                    totalPage = count / pageSize;
                } else {
                    totalPage = count / pageSize + 1;
                }
                return new PageData(pageNo, pageSize, totalPage, count, hidDangerDOS);
            }else {
                List<HidDangerDO> hidDangerDOS = hidDangerDAO.findPersonnelFinishByPage(personnelId,pageNo2,pageSize);
                for (int i = 0;i<hidDangerDOS.size();i++){
                    if (hidDangerDOS.get(i).getProcessingStatus().equals("5")){
                        hidDangerDOS.get(i).setProcessingStatus("审核通过");
                    }
                }
                int totalPage = 0;
                int count = hidDangerDAO.findPersonnelFinishByPageNum(personnelId);
                if (0 == count % pageSize) {
                    totalPage = count / pageSize;
                } else {
                    totalPage = count / pageSize + 1;
                }
                return new PageData(pageNo, pageSize, totalPage, count, hidDangerDOS);
            }
        }catch (NullPointerException e){
            return null;
        }
    }


    @Override
    public Map<String, Object> findDealDetailByCode(String hidDangerCode) {
        Map<String, Object> map = new HashMap<>();
        HidDangerDO hidDangerDO = hidDangerDAO.findDetailByCode(hidDangerCode);
        if (StringUtils.isNotBlank(hidDangerDO.getRectificationNoticeAnnex())){
            hidDangerDO.setRectificationNoticeAnnex(fileIp+hidDangerDO.getRectificationNoticeAnnex());
        }
        if (StringUtils.isNotBlank(hidDangerDO.getRectificationPlan())){
            hidDangerDO.setRectificationPlan(fileIp+hidDangerDO.getRectificationPlan());
        }
        if (StringUtils.isNotBlank(hidDangerDO.getAcceptanceReport())){
            hidDangerDO.setAcceptanceReport(fileIp+hidDangerDO.getAcceptanceReport());
        }
        List<HidDangerPictureDO> beforImgs = hidDangerDAO.findBeforPictureByHidDangerCode(hidDangerCode);
        for (int i=0;i<beforImgs.size();i++){
            beforImgs.get(i).setBeforePicture(fileIp+beforImgs.get(i).getBeforePicture());
        }
        List<HidDangerPictureDO> afterImgs = hidDangerDAO.findAfterPictureByHidDangerCode(hidDangerCode);
        for (int i=0;i<afterImgs.size();i++){
            afterImgs.get(i).setAfterPicture(fileIp+afterImgs.get(i).getAfterPicture());
        }
        List<HidDangerProcessDO> hidDangerProcessDOS = hidDangerDAO.findProcessByHidDangerCode(hidDangerCode);
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        Integer userId = currentUser.getId();
        SysCompanyPersonnel companyPersonnel = hidDangerDAO.findPersonnelById(personnelId);
        SysOrganization sysOrganization = hidDangerDAO.findAllByOrganizationId(companyPersonnel.getOrganizationId());
        SysRole sysRole = hidDangerDAO.findRoleByUserId(userId);
        String processingStatus =  hidDangerDO.getProcessingStatus();
        int index = hidDangerProcessDOS.size();
        JSONArray jsonArray = new JSONArray();
        if (hidDangerDO.getHidDangerType().equals("1")) {//上报整改
            if (hidDangerProcessDOS.get(index-1).getCorrectorId().intValue() == personnelId.intValue()) {
                if (processingStatus.equals("1")) {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("botton","完成整改");
                    //最顶层的不能有上报处理
                    if (sysOrganization.getLevel() == 1 && sysRole.getLevel() == 1){
                        System.out.println(sysOrganization.getLevel()+"     "+sysRole.getLevel());
                    }else {
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("botton","上报处理");
                        jsonArray.add(jsonObject2);
                    }
                    if (sysOrganization.getLevel() == 4 && sysRole.getLevel() == 2){
                    }else {
                        JSONObject jsonObject3 = new JSONObject();
                        jsonObject3.put("botton","通知整改");
                        jsonArray.add(jsonObject3);
                    }
                    jsonArray.add(jsonObject1);
                }
                if (processingStatus.equals("3")) {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("botton","完成整改");
                    if (sysOrganization.getLevel() == 4 && sysRole.getLevel() == 2){
                    }else {
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("botton","通知整改");
                        jsonArray.add(jsonObject2);
                    }
                    jsonArray.add(jsonObject1);
                }
                if (processingStatus.equals("4")) {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("botton","审核通过");
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("botton","审核不通过");
                    jsonArray.add(jsonObject1);
                    jsonArray.add(jsonObject2);
                }
                //审核不通过，有后续处理时，放开此注释，并修改隐患处理分页的sql
                if (processingStatus.equals("6")) {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("botton","完成整改");
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("botton","上报处理");
                    jsonArray.add(jsonObject1);
                    jsonArray.add(jsonObject2);
                }
            }
        }
        if (hidDangerDO.getHidDangerType().equals("2")){//责令整改
            if (hidDangerProcessDOS.get(index-1).getCorrectorId().intValue() == personnelId.intValue()){
                if (processingStatus.equals("2")){
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("botton","完成整改");
                    if (sysOrganization.getLevel() == 4 && sysRole.getLevel() == 2){
                    }else {
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("botton","通知整改");
                        jsonArray.add(jsonObject2);
                    }
                    JSONObject jsonObject3 = new JSONObject();
                    jsonObject3.put("botton","查看责令通知书");
                    jsonArray.add(jsonObject1);
                    jsonArray.add(jsonObject3);
                }
                if (processingStatus.equals("3")){
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("botton","完成整改");
                    if (sysOrganization.getLevel() == 4 && sysRole.getLevel() == 2){
                    }else {
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("botton","通知整改");
                        jsonArray.add(jsonObject2);
                    }
                    JSONObject jsonObject3 = new JSONObject();
                    jsonObject3.put("botton","查看责令通知书");
                    jsonArray.add(jsonObject1);
                    jsonArray.add(jsonObject3);
                }
                if (processingStatus.equals("4")){
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("botton","审核通过");
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("botton","审核不通过");
                    JSONObject jsonObject3 = new JSONObject();
                    jsonObject3.put("botton","查看责令通知书");
                    jsonArray.add(jsonObject1);
                    jsonArray.add(jsonObject2);
                    jsonArray.add(jsonObject3);
                }
                //审核不通过，有后续处理时，放开此注释，并修改隐患处理分页的sql
                if (processingStatus.equals("6")) {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("botton","完成整改");
                    jsonArray.add(jsonObject1);
                    if (sysOrganization.getLevel() == 4 && sysRole.getLevel() == 2){
                    }else {
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("botton","通知整改");
                        jsonArray.add(jsonObject2);
                    }
                    JSONObject jsonObject3 = new JSONObject();
                    jsonArray.add(jsonObject3);
                    jsonObject3.put("botton","查看责令通知书");
                }
            }else {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("botton","查看责令通知书");
                jsonArray.add(jsonObject1);
            }
        }
        map.put("hidDangerDO",hidDangerDO);
        map.put("beforImgs",beforImgs);
        map.put("afterImgs",afterImgs);
        map.put("botton",jsonArray);
        return map;
    }

    @Override
    public Map<String, Object> findFinishDetailByCode(String hidDangerCode) {
        Map<String, Object> map = new HashMap<>();
        HidDangerDO hidDangerDO = hidDangerDAO.findDetailByCode(hidDangerCode);
        if (StringUtils.isNotBlank(hidDangerDO.getRectificationNoticeAnnex())){
            hidDangerDO.setRectificationNoticeAnnex(fileIp+hidDangerDO.getRectificationNoticeAnnex());
        }
        if (StringUtils.isNotBlank(hidDangerDO.getRectificationPlan())){
            hidDangerDO.setRectificationPlan(fileIp+hidDangerDO.getRectificationPlan());
        }
        if (StringUtils.isNotBlank(hidDangerDO.getAcceptanceReport())){
            hidDangerDO.setAcceptanceReport(fileIp+hidDangerDO.getAcceptanceReport());
        }

        List<HidDangerPictureDO> beforImgs = hidDangerDAO.findBeforPictureByHidDangerCode(hidDangerCode);
        for (int i=0;i<beforImgs.size();i++){
            beforImgs.get(i).setBeforePicture(fileIp+beforImgs.get(i).getBeforePicture());
        }
        List<HidDangerPictureDO> afterImgs = hidDangerDAO.findAfterPictureByHidDangerCode(hidDangerCode);
        for (int i=0;i<afterImgs.size();i++){
            afterImgs.get(i).setAfterPicture(fileIp+afterImgs.get(i).getAfterPicture());
        }
        map.put("hidDangerDO",hidDangerDO);
        map.put("beforImgs",beforImgs);
        map.put("afterImgs",afterImgs);
        return map;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public String complete(HidDangerDO hidDangerDO, MultipartFile[] beforeImg, MultipartFile[] afterImg, MultipartFile plan, MultipartFile report) throws IOException {
        if (hidDangerDO.getIfDeal().equals("否")){
            return "完成整改，请选择处理，并填写完整处理信息";
        }
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        Integer userId = currentUser.getId();
        try {
            HidDangerDO oringinalDO = hidDangerDAO.findAllByHidDangerCode(hidDangerDO.getHidDangerCode());
            SysCompanyPersonnel sysCompanyPersonnel = hidDangerDAO.findPersonnelById(personnelId);
            String udt = DateUtil.date(DateUtil.FORMAT_PATTERN);
            String hidDangerCode = hidDangerDO.getHidDangerCode();
            hidDangerDO.setUdt(udt);
            hidDangerDO.setIfControlMeasures("有");
            hidDangerDO.setIfRectificationPlan("有");
            //排查后照片添加
            if (afterImg.length > 6) {
                return "排查后照片数量不能大于6张";
            }
            if (afterImg.length > 0) {
//                hidDangerDAO.deleteAfterPictureByHidDangerCode(hidDangerDO.getHidDangerCode());
                for (int i = 0; i < afterImg.length; i++) {
                    String contentType = afterImg[i].getContentType();
                    if (contentType.startsWith("image")) {
                        String timestamps = DateUtil.timeStamp();
                        String newFileName = timestamps + new Random().nextInt() + ".jpg";
                        FileUtils.copyInputStreamToFile(afterImg[i].getInputStream(), new File(path+hiddenPath, newFileName));
                        hidDangerDAO.addAfterImg(hidDangerCode,hiddenPath+newFileName);
                    }
                }
            }

            //整改方案、验收报告
            if (plan!=null){
                String filename = plan.getOriginalFilename();
                String timestamps = DateUtil.timeStamp();
                String newFileName = timestamps + filename;
                System.out.println(newFileName);
                FileUtils.copyInputStreamToFile(plan.getInputStream(), new File(path+hiddenPath, newFileName));
                hidDangerDO.setRectificationPlan(hiddenPath+newFileName);
            }else {
                hidDangerDO.setRectificationPlan(oringinalDO.getRectificationPlan());
            }
            if (report != null){
                String filename = report.getOriginalFilename();
                String timestamps = DateUtil.timeStamp();
                String newFileName = timestamps+ filename;
                FileUtils.copyInputStreamToFile(report.getInputStream(), new File(path+hiddenPath, newFileName));
                hidDangerDO.setAcceptanceReport(hiddenPath+newFileName);
            }else {
                hidDangerDO.setAcceptanceReport(oringinalDO.getAcceptanceReport());
            }


//        进程表添加
            SysRole sysRole = hidDangerDAO.findRoleByUserId(userId);
            HidDangerProcessDO hidDangerProcessDO = new HidDangerProcessDO();
            SysOrganization sysOrganization = hidDangerDAO.findAllByOrganizationId(sysCompanyPersonnel.getOrganizationId());
            if (sysOrganization.getLevel() == 1 && sysRole.getLevel() == 1){
                hidDangerDO.setProcessingStatus("5");//最高权限，完成并默认审核通过
            }else {
                if (sysRole.getLevel() == 1) {//判断角色权限等级
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
                hidDangerDO.setProcessingStatus("4");//已处理待审核
            }
            //操作人信息
            hidDangerProcessDO.setHidDangerCode(hidDangerCode);
            hidDangerProcessDO.setOperatorId(sysCompanyPersonnel.getId());
            hidDangerProcessDO.setOperatorName(sysCompanyPersonnel.getName());
            hidDangerProcessDO.setOperatorOrganizationId(sysOrganization.getId());
            hidDangerProcessDO.setOperatorOrganizationName(sysOrganization.getOrganizationName());
            //处理信息
            hidDangerProcessDO.setIfDeal("是");
            hidDangerProcessDO.setDealWay("完成整改");
            hidDangerProcessDO.setDealTime(udt);
            hidDangerProcessDO.setIdt(udt);

            hidDangerDO.setCorrectorId(sysCompanyPersonnel.getId());
            hidDangerDO.setCorrectorName(sysCompanyPersonnel.getName());

            hidDangerDAO.addProcess(hidDangerProcessDO);
            hidDangerDAO.updateCompleteHidDanger(hidDangerDO);
            return "1000";
        }catch (NullPointerException e){
            return "没有创建完整的单位和对应的角色用户";
        }catch (NumberFormatException e){
            return "数据格式错误";
        }catch (IndexOutOfBoundsException e){
            return "数组溢出，配置了多个上级负责人";
        }
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public void auditPass(JSONObject json) {
        //审核修改
        HidDangerDO hidDangerDO = JSON.toJavaObject(json,HidDangerDO.class);
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        SysCompanyPersonnel sysCompanyPersonnel = hidDangerDAO.findPersonnelById(personnelId);
        String time = DateUtil.date(DateUtil.FORMAT_PATTERN);

        hidDangerDO.setAuditorId(sysCompanyPersonnel.getId());
        hidDangerDO.setAuditorName(sysCompanyPersonnel.getName());
        hidDangerDO.setAuditTime(time);
        hidDangerDO.setProcessingStatus("5");

        //添加进程
        String hidDangerCode = hidDangerDO.getHidDangerCode();
        HidDangerProcessDO hidDangerProcessDO = new HidDangerProcessDO();
        SysOrganization sysOrganization = hidDangerDAO.findAllByOrganizationId(sysCompanyPersonnel.getOrganizationId());
        hidDangerProcessDO.setHidDangerCode(hidDangerCode);
        //操作人信息
        hidDangerProcessDO.setOperatorId(sysCompanyPersonnel.getId());
        hidDangerProcessDO.setOperatorName(sysCompanyPersonnel.getName());
        hidDangerProcessDO.setOperatorOrganizationId(sysOrganization.getId());
        hidDangerProcessDO.setOperatorOrganizationName(sysOrganization.getOrganizationName());
        //处理方式
        hidDangerProcessDO.setDealWay("审核通过");
        hidDangerProcessDO.setDealTime(time);
        hidDangerProcessDO.setIdt(time);
        hidDangerDAO.auditPass(hidDangerDO);

    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public void auditFalse(JSONObject json) {
        //审核不通过
        HidDangerDO hidDangerDO = JSON.toJavaObject(json,HidDangerDO.class);
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        SysCompanyPersonnel sysCompanyPersonnel = hidDangerDAO.findPersonnelById(personnelId);
        String time = DateUtil.date(DateUtil.FORMAT_PATTERN);
        hidDangerDO.setAuditorId(sysCompanyPersonnel.getId());
        hidDangerDO.setAuditorName(sysCompanyPersonnel.getName());
        hidDangerDO.setAuditTime(time);
        hidDangerDO.setProcessingStatus("6");

        //添加进程
        String hidDangerCode = hidDangerDO.getHidDangerCode();
        HidDangerProcessDO hidDangerProcessDO2 = hidDangerDAO.findLastProcess(hidDangerCode);
        HidDangerProcessDO hidDangerProcessDO = new HidDangerProcessDO();
        SysOrganization sysOrganization = hidDangerDAO.findAllByOrganizationId(sysCompanyPersonnel.getOrganizationId());
        hidDangerProcessDO.setHidDangerCode(hidDangerCode);
        hidDangerProcessDO.setOperatorId(sysCompanyPersonnel.getId());
        hidDangerProcessDO.setOperatorName(sysCompanyPersonnel.getName());
        hidDangerProcessDO.setOperatorOrganizationId(sysOrganization.getId());
        hidDangerProcessDO.setOperatorOrganizationName(sysOrganization.getOrganizationName());
        hidDangerProcessDO.setDealTime(time);
        hidDangerProcessDO.setDealWay("审核不通过");
        hidDangerProcessDO.setOrganizationId(hidDangerProcessDO2.getOperatorOrganizationId());
        hidDangerProcessDO.setOrganizationName(hidDangerProcessDO2.getOperatorOrganizationName());
        hidDangerProcessDO.setCorrectorId(hidDangerProcessDO2.getOperatorId());
        hidDangerProcessDO.setCorrectorName(hidDangerProcessDO2.getOperatorName());
        hidDangerProcessDO.setIdt(time);

        hidDangerDAO.auditFalse(hidDangerDO);
        hidDangerDAO.addProcess(hidDangerProcessDO);
    }


    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public String rectificationNotice(JSONObject json) {
        HidDangerDO hidDangerDO = JSON.toJavaObject(json,HidDangerDO.class);
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        String idt = DateUtil.date(DateUtil.FORMAT_PATTERN);
        String hidDangerCode = hidDangerDO.getHidDangerCode();
        Integer correctorId = hidDangerDO.getCorrectorId();
        try {
            SysCompanyPersonnel sysCompanyPersonnel = hidDangerDAO.findPersonnelById(personnelId);
            //进程表添加
            SysOrganization sysOrganization = hidDangerDAO.findAllByOrganizationId(sysCompanyPersonnel.getOrganizationId());
            HidDangerProcessDO hidDangerProcessDO = new HidDangerProcessDO();
            hidDangerProcessDO.setHidDangerCode(hidDangerCode);
            //操作人信息
            hidDangerProcessDO.setOperatorId(sysCompanyPersonnel.getId());
            hidDangerProcessDO.setOperatorName(sysCompanyPersonnel.getName());
            hidDangerProcessDO.setOperatorOrganizationId(sysOrganization.getId());
            hidDangerProcessDO.setOperatorOrganizationName(sysOrganization.getOrganizationName());
            //整改负责人
            SysCompanyPersonnel sysCompanyPersonnel2 = hidDangerDAO.findPersonnelById(correctorId);
            SysOrganization sysOrganization2 = hidDangerDAO.findAllByOrganizationId(sysCompanyPersonnel2.getOrganizationId());
            hidDangerProcessDO.setCorrectorId(sysCompanyPersonnel2.getId());
            hidDangerProcessDO.setCorrectorName(sysCompanyPersonnel2.getName());
            hidDangerProcessDO.setOrganizationId(sysOrganization2.getId());
            hidDangerProcessDO.setOrganizationName(sysOrganization2.getOrganizationName());
            hidDangerProcessDO.setIfDeal("");
            hidDangerProcessDO.setDealWay("通知整改");
            hidDangerProcessDO.setDealTime(idt);//提交时间
            hidDangerProcessDO.setIdt(idt);

            //修改hid_danger
            hidDangerDO.setCorrectorName(sysCompanyPersonnel2.getName());
            HidDangerDO hidDangerDO2 = hidDangerDAO.findAllByHidDangerCode(hidDangerCode);
            if (StringUtils.isBlank(hidDangerDO2.getRectificationNoticeTime())){
                hidDangerDO.setRectificationNoticeTime(idt);
            }else {
                hidDangerDO.setRectificationNoticeTime(hidDangerDO2.getRectificationNoticeTime());
            }
            hidDangerDO.setProcessingStatus("3");
            hidDangerDAO.updateNotice(hidDangerDO);
            hidDangerDAO.deleteAfterPictureByHidDangerCode(hidDangerDO.getHidDangerCode());
            hidDangerDAO.addProcess(hidDangerProcessDO);
            return "1000";
        }catch (NullPointerException e){
            return "没有创建完整的单位和对应的角色用户";
        }catch (NumberFormatException e){
            return "数据格式错误";
        }catch (IndexOutOfBoundsException e){
            return "数组溢出";
        }
    }

    @Override
    public Map<String, Object> findCorrector() {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        Integer userId = currentUser.getId();
        SysCompanyPersonnel sysCompanyPersonnel = hidDangerDAO.findPersonnelById(personnelId);
        SysRole sysRole = hidDangerDAO.findRoleByUserId(userId);
        List<SysPersonnelDTO> sysCompanyPersonnels = new ArrayList<>();
        sysCompanyPersonnels = hidDangerDAO.findPersonnelByOrganizationId(sysCompanyPersonnel.getOrganizationId(),sysRole.getLevel());
        List<SysPersonnelDTO> sysCompanyPersonnels1 = hidDangerDAO.findAllFirstUserByOrganizationId(sysCompanyPersonnel.getOrganizationId());
        for (int i = 0; i< sysCompanyPersonnels1.size(); i++){
            sysCompanyPersonnels.add(sysCompanyPersonnels1.get(i));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("data",sysCompanyPersonnels);
        return map;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public String report(JSONObject json) {
        HidDangerDO hidDangerDO = JSON.toJavaObject(json,HidDangerDO.class);
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        Integer userId = currentUser.getId();
        hidDangerDO.setProcessingStatus("1");
        String hidDangerCode  = hidDangerDO.getHidDangerCode();
        try {
            SysCompanyPersonnel sysCompanyPersonnel = hidDangerDAO.findPersonnelById(personnelId);
            String idt = DateUtil.date(DateUtil.FORMAT_PATTERN);
            //进程表添加
            SysRole sysRole = hidDangerDAO.findRoleByUserId(userId);
            SysOrganization sysOrganization = hidDangerDAO.findAllByOrganizationId(sysCompanyPersonnel.getOrganizationId());
            HidDangerProcessDO hidDangerProcessDO = new HidDangerProcessDO();
            if (sysRole.getLevel() == 1) {//判断角色权限等级
                //上报组织
                SysOrganization sysOrganization2 = hidDangerDAO.findAllByOrganizationId(sysOrganization.getParentId());
                hidDangerProcessDO.setOrganizationId(sysOrganization2.getId());
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
            //操作人信息
            hidDangerProcessDO.setOperatorId(sysCompanyPersonnel.getId());
            hidDangerProcessDO.setOperatorName(sysCompanyPersonnel.getName());
            hidDangerProcessDO.setOperatorOrganizationId(sysOrganization.getId());
            hidDangerProcessDO.setOperatorOrganizationName(sysOrganization.getOrganizationName());
            //处理方式
            hidDangerProcessDO.setIfDeal("否");
            hidDangerProcessDO.setDealWay("上报");
            hidDangerProcessDO.setDealTime(idt);
            hidDangerProcessDO.setIdt(idt);
            hidDangerDAO.updateProcessingStatus(hidDangerDO);
            hidDangerDAO.addProcess(hidDangerProcessDO);
            return "1000";
        }catch (NullPointerException e){
            System.out.println("错误："+e);
            return "没有创建完整的单位和对应的角色用户";
        }catch (NumberFormatException e){
            System.out.println("错误："+e);
            return "数据格式错误";
        }catch (IndexOutOfBoundsException e){
            System.out.println("错误："+e);
            return "数组溢出";
        }
    }

    @Override
    public void deletePlan(String hidDangerCode) {
        hidDangerDAO.deletePlan(hidDangerCode);
    }

    @Override
    public void deleteReport(String hidDangerCode) {
        hidDangerDAO.deleteReport(hidDangerCode);
    }

    @Override
    public void deletePicture(Integer id) {
        hidDangerDAO.deletePicture(id);
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
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public String rectifyImmediately(HidDangerDO hidDangerDO, MultipartFile[] beforeImg, MultipartFile[] afterImg) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        Integer userId = currentUser.getId();
        try {
            SysCompanyPersonnel sysCompanyPersonnel = hidDangerDAO.findPersonnelById(personnelId);
            String idt = DateUtil.date(DateUtil.FORMAT_PATTERN);
            String hidDangerCode = DateUtil.timeStamp();
            hidDangerDO.setHidDangerCode(hidDangerCode);
            hidDangerDO.setCopyOrganizationId(123);
            hidDangerDO.setCopyOrganizationName("安防部");
            hidDangerDO.setHidDangerType("3");
            hidDangerDO.setIdt(idt);
            hidDangerDO.setHidDangerGrade("Ⅴ");
            hidDangerDO.setIfDeal("是");
            hidDangerDO.setCorrectorId(sysCompanyPersonnel.getId());
            hidDangerDO.setCorrectorName(sysCompanyPersonnel.getName());
            hidDangerDO.setIfRectificationPlan("有");
            hidDangerDO.setIfControlMeasures("有");
            hidDangerDO.setProcessingStatus("5");//已处理待审核
            hidDangerDO.setCompletionTime(idt);

            //排查前照片添加
            if (beforeImg != null){
                if (beforeImg.length > 6) {
                    return "排查前照片数量不能大于6张";
                }
                if (beforeImg.length > 0) {
                    for (int i = 0; i < beforeImg.length; i++) {
                        String contentType = beforeImg[i].getContentType();
                        if (contentType.startsWith("image")) {
                            String timestamps = DateUtil.timeStamp();
                            String newFileName = timestamps + new Random().nextInt() + ".jpg";
                            FileUtils.copyInputStreamToFile(beforeImg[i].getInputStream(), new File(path+hiddenPath, newFileName));
                            hidDangerDAO.addBeforeImg(hidDangerCode,hiddenPath+newFileName);
                        }
                    }
                }
            }
            //排查后照片添加
            if(afterImg!= null){
                if (afterImg.length > 6) {
                    return "排查后照片数量不能大于6张";
                }
                if (afterImg.length > 0) {
                    for (int i = 0; i < afterImg.length; i++) {
                        String contentType = afterImg[i].getContentType();
                        if (contentType.startsWith("image")) {
                            String timestamps = DateUtil.timeStamp();
                            String newFileName = timestamps + new Random().nextInt() + ".jpg";
                            FileUtils.copyInputStreamToFile(afterImg[i].getInputStream(), new File(path+hiddenPath, newFileName));
                            hidDangerDAO.addAfterImg(hidDangerCode,hiddenPath+newFileName);
                        }
                    }
                }
            }
//        进程表添加
            HidDangerProcessDO hidDangerProcessDO = new HidDangerProcessDO();
            SysOrganization sysOrganization = hidDangerDAO.findAllByOrganizationId(sysCompanyPersonnel.getOrganizationId());
            hidDangerProcessDO.setHidDangerCode(hidDangerCode);
            //操作人信息
            hidDangerProcessDO.setOperatorId(sysCompanyPersonnel.getId());
            hidDangerProcessDO.setOperatorName(sysCompanyPersonnel.getName());
            hidDangerProcessDO.setOperatorOrganizationId(sysOrganization.getId());
            hidDangerProcessDO.setOperatorOrganizationName(sysOrganization.getOrganizationName());
            //处理方式
            hidDangerProcessDO.setIfDeal("是");
            hidDangerProcessDO.setDealWay("立即整改");
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
        }catch (NullPointerException e){
            return "没有创建完整的单位和对应的角色用户";
        }catch (NumberFormatException e){
            return "数据格式错误";
        }catch (IndexOutOfBoundsException e){
            return "数组溢出，配置了多个上级负责人";
        }
    }

    @Override
    public Map<String, Object> findByGrade() {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        SysCompanyPersonnel sysCompanyPersonnel = hidDangerDAO.findPersonnelById(personnelId);
        SysOrganization sysOrganization = hidDangerDAO.findAllByOrganizationId(sysCompanyPersonnel.getOrganizationId());

        List<SystemSettingDTO> systemSettingDTO = hidDangerDAO.findChoose("HID_GRADE");
        Map<String, Object> map = new HashMap<>();
        if (sysOrganization.getLevel() == 1){
            for (int i=0;i<systemSettingDTO.size();i++){
                int num = hidDangerDAO.findByGrade(systemSettingDTO.get(i).getSettingCode());
                map.put(systemSettingDTO.get(i).getSettingName(),num);
            }
            return map;
        }else {
            //隐患所属组织表
            Integer factoryId = null;
            int level = sysOrganization.getLevel();
            if (level == 2 ){
                factoryId = (sysOrganization.getId());
            }
            Integer parentId = sysOrganization.getParentId();
            level = level -1;
            while (level !=1){
                SysOrganization sysOrganization3 = hidDangerDAO.findAllByOrganizationId(parentId);
                if (level == 2 ){
                    factoryId = (sysOrganization3.getId());
                }
                parentId = sysOrganization3.getParentId();
                level=level - 1;
            }
            SysOrganization organization = hidDangerDAO.findAllByOrganizationId(factoryId);
            if (null!=organization.getSecurity()){
                for (int i=0;i<systemSettingDTO.size();i++){
                    int num = hidDangerDAO.findByGrade(systemSettingDTO.get(i).getSettingCode());
                    map.put(systemSettingDTO.get(i).getSettingName(),num);
                }
                return map;
            }else {
                for (int i=0;i<systemSettingDTO.size();i++){
                    int num = hidDangerDAO.findFactoryByGrade(systemSettingDTO.get(i).getSettingCode(),factoryId);
                    map.put(systemSettingDTO.get(i).getSettingName(),num);
                }
                return map;
            }
        }
    }

    @Override
    public Map<String, Object> findByType() {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        SysCompanyPersonnel sysCompanyPersonnel = hidDangerDAO.findPersonnelById(personnelId);
        SysOrganization sysOrganization = hidDangerDAO.findAllByOrganizationId(sysCompanyPersonnel.getOrganizationId());

        Map<String, Object> map = new HashMap<>();
        if (sysOrganization.getLevel() == 1){
            int num1 = hidDangerDAO.findByThing();
            int num2 = hidDangerDAO.findByPerson();
            int num3 = hidDangerDAO.findBymanage();
            map.put("物",num1);
            map.put("人",num2);
            map.put("管理",num3);
            return map;
        }else {
            //隐患所属组织表
            Integer factoryId = null;
            int level = sysOrganization.getLevel();
            if (level == 2 ){
                factoryId = (sysOrganization.getId());
            }
            Integer parentId = sysOrganization.getParentId();
            level = level -1;
            while (level !=1){
                SysOrganization sysOrganization3 = hidDangerDAO.findAllByOrganizationId(parentId);
                if (level == 2 ){
                    factoryId = (sysOrganization3.getId());
                }
                parentId = sysOrganization3.getParentId();
                level=level - 1;
            }
            SysOrganization organization = hidDangerDAO.findAllByOrganizationId(factoryId);
            if (null!=organization.getSecurity()){
                int num1 = hidDangerDAO.findByThing();
                int num2 = hidDangerDAO.findByPerson();
                int num3 = hidDangerDAO.findBymanage();
                map.put("物",num1);
                map.put("人",num2);
                map.put("管理",num3);
                return map;
            }else {
                int num1 = hidDangerDAO.findByThing2(parentId);
                int num2 = hidDangerDAO.findByPerson2(parentId);
                int num3 = hidDangerDAO.findBymanage2(parentId);
                map.put("物",num1);
                map.put("人",num2);
                map.put("管理",num3);
                return map;
            }
        }
    }

    @Override
    public Map<String, Object> findByMonth() {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        SysCompanyPersonnel sysCompanyPersonnel = hidDangerDAO.findPersonnelById(personnelId);
        SysOrganization sysOrganization = hidDangerDAO.findAllByOrganizationId(sysCompanyPersonnel.getOrganizationId());
        Map<String, Object> map = new HashMap<>();
        if (sysOrganization.getLevel() == 1){
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            while (month != 0){
                String month2 = null;
                if (month / 10 < 1){
                    month2 = "0"+month;
                }else {
                    month2 = String.valueOf(month);
                }
                String time = year+"-"+month2;
                int num = hidDangerDAO.findMonthNum(time);
                map.put(month+"月",num);
                month = month-1;
            }
            return map;
        }else {
            //隐患所属组织表
            Integer factoryId = null;
            int level = sysOrganization.getLevel();
            if (level == 2 ){
                factoryId = (sysOrganization.getId());
            }
            Integer parentId = sysOrganization.getParentId();
            level = level -1;
            while (level !=1){
                SysOrganization sysOrganization3 = hidDangerDAO.findAllByOrganizationId(parentId);
                if (level == 2 ){
                    factoryId = (sysOrganization3.getId());
                }
                parentId = sysOrganization3.getParentId();
                level=level - 1;
            }
            SysOrganization organization = hidDangerDAO.findAllByOrganizationId(factoryId);
            if (null!=organization.getSecurity()){
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH) + 1;
                while (month != 0){
                    String month2 = null;
                    if (month / 10 < 1){
                        month2 = "0"+month;
                    }else {
                        month2 = String.valueOf(month);
                    }
                    String time = year+"-"+month2;
                    int num = hidDangerDAO.findMonthNum(time);
                    map.put(month+"月",num);
                    month = month-1;
                }
                return map;
            }else {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH) + 1;
                while (month != 0){
                    String month2 = null;
                    if (month / 10 < 1){
                        month2 = "0"+month;
                    }else {
                        month2 = String.valueOf(month);
                    }
                    String time = year+"-"+month2;
                    int num = hidDangerDAO.findMonthNum2(time,factoryId);
                    map.put(month+"月",num);
                    month = month-1;
                }
                return map;
            }
        }
    }
}
