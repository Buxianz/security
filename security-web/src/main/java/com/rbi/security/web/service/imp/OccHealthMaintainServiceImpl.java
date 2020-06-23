package com.rbi.security.web.service.imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.health.OccHealthMaintain;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.health.OccHealthMaintainDAO;
import com.rbi.security.web.service.OccHealthMaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service.imp
 * @NAME: OccHealthMaintainServiceImpl
 * @USER: "林新元"
 * @DATE: 2020/6/23
 * @TIME: 10:37
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 六月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 23
 * @DAY_NAME_SHORT: 星期二
 * @DAY_NAME_FULL: 星期二
 * @HOUR: 10
 * @MINUTE: 37
 * @PROJECT_NAME: security
 **/
@Service
public class OccHealthMaintainServiceImpl implements OccHealthMaintainService {
    @Autowired(required = false)
    OccHealthMaintainDAO occHealthMaintainDAO;


    @Override
    public PageData findOccHealthMaintainByPage(JSONObject json) {
        int pageNo = json.getInteger("pageNo");
        int pageSize = json.getByteValue("pageSize");
        int recNo = pageSize * (pageNo - 1);
        int totalPage = 0;
        List<OccHealthMaintain> occHealthMaintainList =occHealthMaintainDAO.findOccHealthMaintainByPage(recNo, pageSize);
        int count =occHealthMaintainDAO.findNumOccHealthMaintain();
        if (0 == count % pageSize) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        return new PageData(pageNo, pageSize, totalPage, count, occHealthMaintainList);
    }

    @Override
    public OccHealthMaintain findOccHealthMaintainById(JSONObject json) {
        OccHealthMaintain occHealthMaintain=occHealthMaintainDAO.findOccHealthMaintainById(json.getInteger("id"));
        return occHealthMaintain;
    }

    @Override
    public String insertOccHealthMaintain(JSONObject json) {
        if (Objects.isNull(occHealthMaintainDAO.findOccHealthMaintainByHealthMaintainName(json.getString("healthMaintainName")))) {
            OccHealthMaintain occHealthMaintain = JSONObject.parseObject(json.toJSONString(), OccHealthMaintain.class);
            occHealthMaintainDAO.insertOccHealthMaintain(occHealthMaintain);
            return "1000";
        }else {
            return "1001";
        }
    }

    @Override
    public String deleteOccHealthMaintain(JSONArray result) {
        String reT=null;
        for (int i=0;i<result.size();i++) {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result.get(i));
            Integer Id = jsonObject.getInteger("id");
            if (occHealthMaintainDAO.findOccHealthMaintainById(Id)!=null){
                occHealthMaintainDAO.deleteOccHealthMaintain(Id);
                reT="1000";
            }else {
                return String.valueOf(Id);
            }
        }
        return reT;
    }

    @Override
    public String updateOccHealthMaintain(JSONObject json) {
        OccHealthMaintain occHealthMaintain= JSONObject.parseObject(json.toJSONString(), OccHealthMaintain.class);
        if (occHealthMaintainDAO.findOccHealthMaintainById(occHealthMaintain.getId())!=null) {
            occHealthMaintainDAO.updateOccHealthMaintain(occHealthMaintain);
            return "1000";
        }else {
            return "1006";
        }
    }
}
