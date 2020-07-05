package com.rbi.security.web.service.imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.entity.SafeFourLevel;
import com.rbi.security.entity.web.entity.SafeFourLevelDTO;
import com.rbi.security.entity.web.entity.SysCompanyPersonnel;
import com.rbi.security.entity.web.importlog.LogAdministratorTrain;
import com.rbi.security.entity.web.importlog.LogForLevel;
import com.rbi.security.entity.web.safe.PagingSafeFourLevel;
import com.rbi.security.entity.web.safe.administrator.SafeAdministratorTrain;
import com.rbi.security.entity.web.safe.administrator.SafeAdministratorTrainDTO;
import com.rbi.security.tool.*;
import com.rbi.security.web.DAO.CompanyPersonnelDAO;
import com.rbi.security.web.DAO.safe.SafeFourLevelDAO;
import com.rbi.security.web.service.SafeFourLevelService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service.imp
 * @NAME: SafeFourLevelServiceImpl
 * @USER: "林新元"
 * @DATE: 2020/5/29
 * @TIME: 10:24
 * @YEAR: 2020
 * @MONTH: 05
 * @MONTH_NAME_SHORT: 五月
 * @MONTH_NAME_FULL: 五月
 * @DAY: 29
 * @DAY_NAME_SHORT: 星期五
 * @DAY_NAME_FULL: 星期五
 * @HOUR: 10
 * @MINUTE: 24
 * @PROJECT_NAME: security
 **/
@Service
public class SafeFourLevelServiceImpl implements SafeFourLevelService {
    private static final Logger logger = LoggerFactory.getLogger(SafeFourLevelServiceImpl.class);
    @Autowired(required = false)
    SafeFourLevelDAO safeFourLevelDAO;
    @Autowired(required = false)
    CompanyPersonnelDAO companyPersonnelDAO;
    @Value("${uploadfile.ip}")
    private String fileIp;
    @Value("${excelWritePath}")
    private String excelWritePath;

    @Override
    public PageData findSafeFourLevelByPage(JSONObject json) {
        int totalPage=0;
        int count=0;
        int pageNo = json.getInteger("pageNo");
        int pageSize = json.getInteger("pageSize");
        int recNo = pageSize * (pageNo - 1);
        List<PagingSafeFourLevel> pagingSafeFourLevelList=safeFourLevelDAO.getSafeFourLevelByPage(recNo, pageSize);
        count =safeFourLevelDAO.getCountSafeFourLevel();
        if (0 == count % pageSize) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        return new PageData(pageNo, pageSize, totalPage, count, pagingSafeFourLevelList);
    }

    @Override
    public PagingSafeFourLevel getSafeFourLevelById(JSONObject json) {
        PagingSafeFourLevel pagingSafeFourLevel=safeFourLevelDAO.getSafeFourLevelById(json.getInteger("id"));
        return pagingSafeFourLevel;
    }

    @Override
    public PageData findSafeFourLevel(JSONObject json) {
        int pageNo = json.getInteger("pageNo");
        int pageSize = json.getInteger("pageSize");
        int recNo = pageSize * (pageNo - 1);
        List<PagingSafeFourLevel> pagingSafeFourLevelList=new ArrayList<>();
        int count =0;
        if (json.getInteger("key")==0) {
            pagingSafeFourLevelList = safeFourLevelDAO.findSafeFourLevelByName(json.getString("name"),recNo, pageSize);
            count =safeFourLevelDAO.getCountSafeFourLevelByName(json.getString("name"));
        }else if (json.getInteger("key")==1) {
            pagingSafeFourLevelList = safeFourLevelDAO.findSafeFourLevelByWorkType(json.getString("workType"),recNo, pageSize);
            count =safeFourLevelDAO.getCountSafeFourLevelByWorkType(json.getString("workType"));
        }
        int totalPage=0;
        if (0 == count % pageSize) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        return new PageData(pageNo, pageSize, totalPage, count, pagingSafeFourLevelList);
    }

    @Override
    public PagingSafeFourLevel findSafeFourLevelByOperatingStaff() {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        int personnelId  =  currentUser.getCompanyPersonnelId();
        return safeFourLevelDAO.findSafeFourLevelByOperatingStaff(personnelId);
    }

    @Override
    public String insertSafeFourLevel(JSONObject json) {
        SafeFourLevel safeFourLevel = JSONObject.parseObject(json.toJSONString(), SafeFourLevel.class);

        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser = (AuthenticationUserDTO) subject.getPrincipal();
        int personnelId = currentUser.getCompanyPersonnelId();
        safeFourLevel.setOperatingStaff(personnelId);
        try {
                if (companyPersonnelDAO.getPersonnelByIdCardNo(json.getString("idCardNo"))!=null) {
                    if (safeFourLevelDAO.findIdNumNumber(safeFourLevel.getIdCardNo())==0) {
                        safeFourLevel.setIdt(DateUtil.date(DateUtil.FORMAT_PATTERN));
                        safeFourLevel.setUdt(DateUtil.date(DateUtil.FORMAT_PATTERN));
                        safeFourLevelDAO.insertSafeFourLevel(safeFourLevel);
                        return "1000";
                    }else {
                        return "1003";
                    }
                }else {
                    return "1002";
                }
        } catch (NullPointerException e) {
            logger.error("添加异常，异常信息为{}", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String deleteSafeFourLevelById(JSONArray result) {
        String reT=null;
        for (int i=0;i<result.size();i++) {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result.get(i));
            Integer Id = jsonObject.getInteger("id");
            if (safeFourLevelDAO.getSafeFourLevelById(Id)!=null){
            safeFourLevelDAO.deleteSafeFourLevelById(Id);
            reT="1000";
            }else {
                return String.valueOf(Id);
            }
        }
        return reT;
    }

    @Override
    public String updateSafeFourLevel(JSONObject json) {

        SafeFourLevel safeFourLevel= JSONObject.parseObject(json.toJSONString(), SafeFourLevel.class);
        if (safeFourLevelDAO.getSafeFourLevelById(json.getInteger("id"))!=null) {
            PagingSafeFourLevel pagingSafeFourLevel=safeFourLevelDAO.getSafeFourLevelById(json.getInteger("id"));
            safeFourLevel.setIdCardNo(pagingSafeFourLevel.getIdCardNo());
            safeFourLevel.setOrganizationName(pagingSafeFourLevel.getOrganizationName());

            Subject subject = SecurityUtils.getSubject();
            AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
            int personnelId  =  currentUser.getCompanyPersonnelId();
            safeFourLevel.setOperatingStaff(personnelId);

            safeFourLevel.setUdt(DateUtil.date(DateUtil.FORMAT_PATTERN));
            safeFourLevelDAO.updateSafeFourLevelById(safeFourLevel);
            return "1000";
        }else {
            return "1001";
        }
    }

    @Override
    public Map<String, Object> excelImport(MultipartFile file) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        String idt = DateUtil.date(DateUtil.FORMAT_PATTERN);
        List<LogForLevel> log = new ArrayList<>();
        List<String> list = ExcelRead.readExcel(file, 11);
        int importCount = 0;
        String idCardNo2 = null;
        for (int i=0;i<list.size();i++){
            try {
                List<String> list1 = Arrays.asList(StringUtils.split(list.get(i), ","));
                String idCardNo = list1.get(0);
                String organizationName = list1.get(1);
                String companyEducationTime = list1.get(2);
                String companyFraction = list1.get(3);
                String factoryEducationTime = list1.get(4);
                String factoryFraction = list1.get(5);
                String workshopEducationTime = list1.get(6);
                String workshopFraction = list1.get(7);
                String classEducationTime = list1.get(8);
                String classFraction = list1.get(9);
                String remarks = list1.get(10);
                LogForLevel logForLevel = new LogForLevel();
                if (idCardNo.equals("blank") || organizationName.equals("blank")) {
                    logForLevel.setCode(importCount + 2);
                    logForLevel.setIdNum(idCardNo);
                    logForLevel.setResult("失败");
                    logForLevel.setReason("必填字段不能为空");
                    logForLevel.setIdt(idt);
                    safeFourLevelDAO.addLogForLevel(logForLevel);
                    log.add(logForLevel);
                    continue;
                }
                idCardNo2 = idCardNo;
                int num1 = safeFourLevelDAO.findPersonnelByIdCardNum(idCardNo);
                if (num1 == 0) {
                    logForLevel.setCode(importCount + 2);
                    logForLevel.setIdNum(idCardNo);
                    logForLevel.setResult("失败");
                    logForLevel.setReason("系统没有此身份证号对应的人员信息");
                    logForLevel.setIdt(idt);
                    safeFourLevelDAO.addLogForLevel(logForLevel);
                    log.add(logForLevel);
                    continue;
                }
                if (companyEducationTime.equals("blank")) {
                    companyEducationTime = null;
                }
                if (companyFraction.equals("blank")) {
                    companyFraction = null;
                }
                if (factoryEducationTime.equals("blank")) {
                    factoryEducationTime = null;
                }
                if (factoryFraction.equals("blank")) {
                    factoryFraction = null;
                }
                if (workshopEducationTime.equals("blank")) {
                    workshopEducationTime = null;
                }
                if (workshopFraction.equals("blank")) {
                    workshopFraction = null;
                }
                if (classEducationTime.equals("blank")) {
                    classEducationTime = null;
                }if (classFraction.equals("blank")) {
                    classFraction = null;
                }
                if (remarks.equals("blank")) {
                    remarks = null;
                }
                SafeFourLevel safeFourLevel = new SafeFourLevel();
                safeFourLevel.setIdCardNo(idCardNo);
                safeFourLevel.setOrganizationName(organizationName);
                safeFourLevel.setCompanyEducationTime(companyEducationTime);
                if (companyFraction == null){
                    safeFourLevel.setCompanyFraction(null);
                }else {
                    safeFourLevel.setCompanyFraction(Double.valueOf(companyFraction));
                }
                safeFourLevel.setFactoryEducationTime(factoryEducationTime);
                if (factoryFraction == null){
                    safeFourLevel.setFactoryFraction(null);
                }else {
                    safeFourLevel.setFactoryFraction(Double.valueOf(factoryFraction));
                }
                safeFourLevel.setWorkshopEducationTime(workshopEducationTime);
                if (workshopFraction == null){
                    safeFourLevel.setWorkshopFraction(null);
                }else {
                    safeFourLevel.setWorkshopFraction(Double.valueOf(workshopFraction));
                }
                safeFourLevel.setClassEducationTime(classEducationTime);
                if (classFraction == null){
                    safeFourLevel.setClassFraction(null);
                }else {
                    safeFourLevel.setClassFraction(Double.valueOf(classFraction));
                }
                safeFourLevel.setRemarks(remarks);
                safeFourLevel.setIdt(idt);
                safeFourLevel.setOperatingStaff(personnelId);

                int num2 = safeFourLevelDAO.findIdCardNoNum(idCardNo);
                if (num2 >= 1) {
                    safeFourLevelDAO.updateSafeFourLevelByIdNum(safeFourLevel);
                    logForLevel.setCode(importCount + 2);
                    logForLevel.setIdNum(idCardNo);
                    logForLevel.setResult("更新成功");
                    logForLevel.setIdt(idt);
                    safeFourLevelDAO.addLogForLevel(logForLevel);
                    log.add(logForLevel);
                } else {
                    safeFourLevelDAO.insertSafeFourLevel(safeFourLevel);
                    logForLevel.setCode(importCount + 2);
                    logForLevel.setIdNum(idCardNo);
                    logForLevel.setResult("添加成功");
                    logForLevel.setIdt(idt);
                    safeFourLevelDAO.addLogForLevel(logForLevel);
                    log.add(logForLevel);
                }
                importCount = importCount +1;
            }catch (NumberFormatException e){
                LogForLevel logForLevel = new LogForLevel();
                logForLevel.setCode(importCount + 2);
                logForLevel.setIdNum(idCardNo2);
                logForLevel.setResult("失败");
                logForLevel.setReason("数字转换错误，请注意填数字");
                logForLevel.setIdt(idt);
                safeFourLevelDAO.addLogForLevel(logForLevel);
                log.add(logForLevel);
            } catch (NullPointerException e){
                LogForLevel logForLevel = new LogForLevel();
                logForLevel.setCode(importCount + 2);
                logForLevel.setIdNum(idCardNo2);
                logForLevel.setResult("失败");
                logForLevel.setReason("空指针异常");
                logForLevel.setIdt(idt);
                safeFourLevelDAO.addLogForLevel(logForLevel);
                log.add(logForLevel);
        }catch (Exception e){
                System.out.println("异常"+e);
                LogForLevel logForLevel = new LogForLevel();
                logForLevel.setCode(importCount + 2);
                logForLevel.setIdNum(idCardNo2);
                logForLevel.setResult("失败");
                logForLevel.setReason("处理异常");
                logForLevel.setIdt(idt);
                safeFourLevelDAO.addLogForLevel(logForLevel);
                log.add(logForLevel);
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("totalNumber",list.size()); //导入数据条数
        map.put("realNumber",importCount); //导入成功条数
        map.put("log",log);//日志列表
        return map;
    }

    @Override
    public Map<String, Object> excelwrite() throws IOException {
            String filepath = excelWritePath + "四级台账.xlsx";
            String sheetName = "sheet1";
            String findPath = fileIp + filepath;
            List<String> titles = new ArrayList<>();
            titles.add("姓名");
            titles.add("身份证号");
            titles.add("单位组织");
            titles.add("性别");
            titles.add("出生年月");
            titles.add("入厂时间");
            titles.add("工种");
            titles.add("岗位");
            titles.add("分公司教育时间");
            titles.add("分公司成绩");
            titles.add("厂(矿)教育时间");
            titles.add("厂(矿)成绩");
            titles.add("车间教育时间");
            titles.add("车间成绩");
            titles.add("班组教育时间");
            titles.add("班组成绩");
            titles.add("备注");
            List<Map<String, Object>> values = new ArrayList<>();
            List<SafeFourLevelDTO> safeFourLevelDTOS = safeFourLevelDAO.findAll();
            for (int i = 0; i < safeFourLevelDTOS.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("姓名", safeFourLevelDTOS.get(i).getName());
                map.put("身份证号", safeFourLevelDTOS.get(i).getIdCardNo());
                map.put("单位组织", safeFourLevelDTOS.get(i).getOrganizationName());
                map.put("性别", safeFourLevelDTOS.get(i).getGender());
                map.put("出生年月", safeFourLevelDTOS.get(i).getDateOfBirth());
                map.put("入厂时间", safeFourLevelDTOS.get(i).getEntryTime());
                map.put("工种", safeFourLevelDTOS.get(i).getWorkType());
                map.put("岗位", safeFourLevelDTOS.get(i).getPosition());
                map.put("分公司教育时间", safeFourLevelDTOS.get(i).getCompanyEducationTime());
                map.put("分公司成绩", safeFourLevelDTOS.get(i).getCompanyFraction());
                map.put("厂(矿)教育时间", safeFourLevelDTOS.get(i).getFactoryEducationTime());
                map.put("厂(矿)成绩", safeFourLevelDTOS.get(i).getFactoryFraction());
                map.put("车间教育时间", safeFourLevelDTOS.get(i).getWorkshopEducationTime());
                map.put("车间成绩", safeFourLevelDTOS.get(i).getWorkshopFraction());
                map.put("班组教育时间", safeFourLevelDTOS.get(i).getClassEducationTime());
                map.put("班组成绩", safeFourLevelDTOS.get(i).getClassFraction());
                map.put("备注", safeFourLevelDTOS.get(i).getRemarks());
                values.add(map);
            }
            ExcelWrite.writeExcel(filepath, sheetName, titles, values);
            Map<String, Object> map = new HashMap<>();
            map.put("path", findPath);
            return map;
        }
}


