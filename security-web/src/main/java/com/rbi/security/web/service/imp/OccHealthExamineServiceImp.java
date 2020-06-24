package com.rbi.security.web.service.imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.health.OccDiseaseFactors;
import com.rbi.security.entity.web.health.OccHealthExamine;
import com.rbi.security.tool.DateUtil;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.health.OccHealthExamineDAO;
import com.rbi.security.web.service.OccHealthExamineService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service.imp
 * @NAME: OccHealthExamineServiceImp
 * @USER: "谢青"
 * @DATE: 2020/6/24
 * @TIME: 10:30
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 24
 * @DAY_NAME_SHORT: 周三
 * @DAY_NAME_FULL: 星期三
 * @HOUR: 10
 * @MINUTE: 30
 * @PROJECT_NAME: security
 **/
@Service
public class OccHealthExamineServiceImp implements OccHealthExamineService {
    @Autowired
    OccHealthExamineDAO occHealthExamineDAO;


    @Override
    public PageData findByPage(int pageNo, int pageSize) {
        int pageNo2 = pageSize * (pageNo - 1);
        List<OccHealthExamine> occHealthExamine = occHealthExamineDAO.findByPage(pageNo2,pageSize);
        int totalPage = 0;
        int count = occHealthExamineDAO.findByPageNum();
        if (0 == count % pageSize) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        return new PageData(pageNo, pageSize, totalPage, count, occHealthExamine);
    }

    @Override
    public String add(OccHealthExamine occHealthExamine) {
        int num = occHealthExamineDAO.findNum(occHealthExamine.getIdNum());
        if (num != 0){
            return "身份证号重复添加";
        }
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        occHealthExamine.setOperatingStaff(personnelId);
        String idt = DateUtil.date(DateUtil.FORMAT_PATTERN);
        occHealthExamine.setIdt(idt);
        occHealthExamineDAO.add(occHealthExamine);
        return "1000";
    }

    @Override
    public String update(OccHealthExamine occHealthExamine) {
        int num = occHealthExamineDAO.findNumExceptId(occHealthExamine.getIdNum(),occHealthExamine.getId());
        if (num != 0){
            return "身份证号重复";
        }
        String udt = DateUtil.date(DateUtil.FORMAT_PATTERN);
        occHealthExamine.setUdt(udt);
        occHealthExamineDAO.update(occHealthExamine);
        return "1000";
    }

    @Override
    public void delete(JSONObject json) {
        JSONArray array = json.getJSONArray("data");
        for (int i=0;i< array.size();i++){
            JSONObject result = (JSONObject) array.get(i);
            int id = result.getInteger("id");
            occHealthExamineDAO.delete(id);
        }
    }
}
