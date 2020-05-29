package com.rbi.security.web.service.imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.entity.SafeFourLevel;
import com.rbi.security.entity.web.entity.SysCompanyPersonnel;
import com.rbi.security.tool.DateUtil;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.CompanyPersonnelDAO;
import com.rbi.security.web.DAO.safe.SafeFourLevelDAO;
import com.rbi.security.web.service.SafeFourLevelService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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
    @Autowired(required = false)
    SafeFourLevelDAO safeFourLevelDAO;
    @Autowired(required = false)
    CompanyPersonnelDAO companyPersonnelDAO;

    @Override
    public PageData findSafeFourLevelByPage(JSONObject json) {
        int totalPage=0;
        int count=0;
        int pageNo = json.getInteger("pageNo");
        int pageSize = json.getInteger("pageSize");
        int recNo = pageSize * (pageNo - 1);
        List<SafeFourLevel> safeFourLevelList=safeFourLevelDAO.getSafeFourLevelByPage(recNo, pageSize);
        count =safeFourLevelDAO.getCountSafeFourLevel();
        if (0 == count % pageSize) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        return new PageData(pageNo, pageSize, totalPage, count, safeFourLevelList);
    }

    @Override
    public SafeFourLevel getSafeFourLevelById(JSONObject json) {
        SafeFourLevel safeFourLevel=safeFourLevelDAO.getSafeFourLevelById(json.getInteger("id"));
        return safeFourLevel;
    }

    @Override
    public void insertSafeFourLevel(JSONObject json) {
        SafeFourLevel safeFourLevel= JSONObject.parseObject(json.toJSONString(), SafeFourLevel.class);
//        safeFourLevel.setIdCardNo(json.getString("idCardNo"));
//        safeFourLevel.setOrganizationName(json.getString("organizationName"));
//        safeFourLevel.setCompanyEducationTime(json.getString("companyEducationTime"));
//        safeFourLevel.setCompanyFraction(json.getDouble("companyFraction"));
//        safeFourLevel.setFactoryEducationTime(json.getString("factoryEducationTime"));
//        safeFourLevel.setFactoryFraction(json.getDouble("factoryFraction"));
//        safeFourLevel.setWorkshopEducationTime(json.getString("workshopEducationTime"));
//        safeFourLevel.setWorkshopFraction(json.getDouble("workshopFraction"));
//        safeFourLevel.setClassEducationTime(json.getString("classEducationTime"));
//        safeFourLevel.setClassFraction(json.getDouble("classFraction"));

        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        int personnelId  =  currentUser.getCompanyPersonnelId();
        safeFourLevel.setOperatingStaff(personnelId);

        SysCompanyPersonnel sysCompanyPersonnel=companyPersonnelDAO.getSysCompanyPersonnelByIdCardNo(json.getString("idCardNo"));
        safeFourLevel.setName(sysCompanyPersonnel.getName());
        safeFourLevel.setGender(sysCompanyPersonnel.getGender());
        safeFourLevel.setDateOfBirth(sysCompanyPersonnel.getDateOfBirth());
        safeFourLevel.setEntryTime(sysCompanyPersonnel.getEntryTime());
        safeFourLevel.setWorkType(sysCompanyPersonnel.getWorkType());
        safeFourLevel.setJobNature(sysCompanyPersonnel.getPosition());

        safeFourLevel.setIdt(DateUtil.date(DateUtil.FORMAT_PATTERN));
        safeFourLevel.setUdt(DateUtil.date(DateUtil.FORMAT_PATTERN));
        safeFourLevelDAO.insertSafeFourLevel(safeFourLevel);
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
            SafeFourLevel safeFourLevel1=safeFourLevelDAO.getSafeFourLevelById(json.getInteger("id"));
            safeFourLevel.setIdCardNo(safeFourLevel1.getIdCardNo());
            safeFourLevel.setOrganizationName(safeFourLevel1.getOrganizationName());
            safeFourLevel.setName(safeFourLevel1.getName());
            safeFourLevel.setGender(safeFourLevel1.getGender());
            safeFourLevel.setDateOfBirth(safeFourLevel1.getDateOfBirth());
            safeFourLevel.setEntryTime(safeFourLevel1.getEntryTime());
            safeFourLevel.setWorkType(safeFourLevel1.getWorkType());
            safeFourLevel.setJobNature(safeFourLevel1.getJobNature());

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
}
