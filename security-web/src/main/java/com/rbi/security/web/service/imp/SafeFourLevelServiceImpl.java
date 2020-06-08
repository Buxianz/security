package com.rbi.security.web.service.imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.entity.SafeFourLevel;
import com.rbi.security.entity.web.entity.SysCompanyPersonnel;
import com.rbi.security.entity.web.safe.PagingSafeFourLevel;
import com.rbi.security.tool.DateUtil;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.CompanyPersonnelDAO;
import com.rbi.security.web.DAO.safe.SafeFourLevelDAO;
import com.rbi.security.web.service.SafeFourLevelService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private static final Logger logger = LoggerFactory.getLogger(SafeFourLevelServiceImpl.class);
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
    public PageData findSafeFourLevelByOperatingStaff(JSONObject json) {
        int pageNo = json.getInteger("pageNo");
        int pageSize = json.getInteger("pageSize");
        int recNo = pageSize * (pageNo - 1);
        List<PagingSafeFourLevel> pagingSafeFourLevelList=new ArrayList<>();
        int count =0;
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        int personnelId  =  currentUser.getCompanyPersonnelId();

        pagingSafeFourLevelList = safeFourLevelDAO.findSafeFourLevelByOperatingStaff(personnelId,recNo, pageSize);
        count =safeFourLevelDAO.getCountSafeFourLevelByOperatingStaff(personnelId);

        int totalPage=0;
        if (0 == count % pageSize) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        return new PageData(pageNo, pageSize, totalPage, count, pagingSafeFourLevelList);
    }

    @Override
    public String insertSafeFourLevel(JSONObject json) {
        SafeFourLevel safeFourLevel = JSONObject.parseObject(json.toJSONString(), SafeFourLevel.class);

        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser = (AuthenticationUserDTO) subject.getPrincipal();
        int personnelId = currentUser.getCompanyPersonnelId();
        safeFourLevel.setOperatingStaff(personnelId);
        try {
            if (json.getString("idCardNo").length() == 18) {
                safeFourLevel.setIdt(DateUtil.date(DateUtil.FORMAT_PATTERN));
                safeFourLevel.setUdt(DateUtil.date(DateUtil.FORMAT_PATTERN));
                safeFourLevelDAO.insertSafeFourLevel(safeFourLevel);
                return "1000";
            } else {
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
}
