package com.rbi.security.web.service.imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.entity.SafeSubject;
import com.rbi.security.entity.web.entity.SafeSubjectOption;
import com.rbi.security.entity.web.safe.PagingSafe;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.SafeSubjectDAO;
import com.rbi.security.web.DAO.SafeSubjectOptionDAO;
import com.rbi.security.web.service.SafeSubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service.imp
 * @NAME: SafeSubjectServiceImpl
 * @USER: "林新元"
 * @DATE: 2020/5/25
 * @TIME: 18:03
 * @YEAR: 2020
 * @MONTH: 05
 * @MONTH_NAME_SHORT: 五月
 * @MONTH_NAME_FULL: 五月
 * @DAY: 25
 * @DAY_NAME_SHORT: 星期一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 18
 * @MINUTE: 03
 * @PROJECT_NAME: security
 **/
@Service
public class SafeSubjectServiceImpl implements SafeSubjectService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImp.class);
    @Autowired(required = false)
    SafeSubjectDAO safeSubjectDAO;
    @Autowired(required = false)
    SafeSubjectOptionDAO safeSubjectOptionDAO;


    @Override
    public void insertSafeSubject(JSONObject json) {
        SafeSubject safeSubject= JSONObject.parseObject(json.toJSONString(), SafeSubject.class);
        safeSubject.setRightKey(json.getString("rightKey"));
        safeSubject.setSubject(json.getString("subject"));
        safeSubject.setSubjectType(json.getInteger("subjectType"));
        safeSubject.setSubjectStoreId(json.getInteger("subjectStoreId"));
        safeSubject.setScore(json.getInteger("score"));
        safeSubjectDAO.insertSafeSubject(safeSubject);
        List<String> options = new ArrayList<>(Arrays.asList(json.getString("option").split("#")));
        List<String> orders = new ArrayList<>(Arrays.asList(json.getString("order").split("#")));
        for (int i=0;i<options.size();i++){
            safeSubjectOptionDAO.insertSafeSubjectOption(options.get(i),Integer.valueOf(orders.get(i)),safeSubject.getId());
        }
    }

    @Override
    public PageData getSafeSubjectByPage(JSONObject json)  throws RuntimeException{
        int totalPage=0;
        int count=0;
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            int recNo = pageSize * (pageNo - 1);
            List<SafeSubject> safeSubjectList = safeSubjectDAO.getSafeSubjectByPage(recNo, pageSize);
            List<PagingSafe> pagingSafeArrayList = new ArrayList<>();
            for (int i = 0; i < safeSubjectList.size(); i++) {
                PagingSafe pagingSafe = new PagingSafe();
                pagingSafe.setSafeSubject(safeSubjectList.get(i));
                List<SafeSubjectOption> safeSubjectOptionList = safeSubjectOptionDAO.getSafeSubjectOptionBySubjectId(safeSubjectList.get(i).getId());
                for (int m = 0; m < safeSubjectOptionList.size(); m++) {
                    pagingSafe.setSafeSubjectOptionList(safeSubjectOptionList);
                }
                pagingSafeArrayList.add(pagingSafe);
            }
            count = safeSubjectDAO.getCountSafeSubject();
            if (count % pageSize == 0) {
                totalPage = count / pageSize;
            } else {
                totalPage = count / pageSize + 1;
            }
            return new PageData(pageNo, pageSize, totalPage, count, pagingSafeArrayList);
        } catch (Exception e) {
        logger.error("查询信息异常，异常信息为{}", e);
        throw new RuntimeException(e.getMessage());
    }
    }

    @Override
    public String updateSafeSubjectById(JSONObject json) {
        SafeSubject safeSubject= JSONObject.parseObject(json.toJSONString(), SafeSubject.class);
        if (safeSubjectDAO.getSafeSubjectById(json.getInteger("id"))!=null) {
            safeSubject.setId(json.getInteger("id"));
            safeSubject.setRightKey(json.getString("rightKey"));
            safeSubject.setSubject(json.getString("subject"));
            safeSubject.setSubjectType(json.getInteger("subjectType"));
            safeSubject.setSubjectStoreId(json.getInteger("subjectStoreId"));
            safeSubject.setScore(json.getInteger("score"));
            safeSubjectDAO.updateSafeSubjectById(safeSubject);
            List<String> options = new ArrayList<>(Arrays.asList(json.getString("option").split("#")));
            List<String> orders = new ArrayList<>(Arrays.asList(json.getString("order").split("#")));
                safeSubjectOptionDAO.deleteSafeSubjectOptionBySubjectId(json.getInteger("id"));
            for (int i = 0; i < options.size(); i++) {
                safeSubjectOptionDAO.insertSafeSubjectOption(options.get(i), Integer.valueOf(orders.get(i)), safeSubject.getId());
            }
            return "1000";
        }else {
            return "1001";
        }
    }

    @Override
    public String deleteSafeSubjectById(JSONArray result) {
        String reT=null;
        for (int i=0;i<result.size();i++) {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result.get(i));
            Integer Id = jsonObject.getInteger("id");
            if (safeSubjectDAO.getSafeSubjectById(Id)!=null){
                safeSubjectDAO.deleteSafeSubjectById(Id);
                List<SafeSubjectOption> safeSubjectOptionList=safeSubjectOptionDAO.getSafeSubjectOptionBySubjectId(Id);
                for (SafeSubjectOption safeSubjectOption:safeSubjectOptionList){
                    safeSubjectOptionDAO.deleteSafeSubjectOptionById(safeSubjectOption.getId());
                }
                reT="1000";
            }else {
                return String.valueOf(Id);
            }
        }
        return reT;
    }
}
