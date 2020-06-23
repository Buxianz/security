package com.rbi.security.web.service.imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.health.OccHealthEndanger;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.health.OccHealthEndangerDAO;
import com.rbi.security.web.service.OccHealthEndangerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service.imp
 * @NAME: OccHealthEndangerServiceImpl
 * @USER: "林新元"
 * @DATE: 2020/6/23
 * @TIME: 10:34
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 六月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 23
 * @DAY_NAME_SHORT: 星期二
 * @DAY_NAME_FULL: 星期二
 * @HOUR: 10
 * @MINUTE: 34
 * @PROJECT_NAME: security
 **/
@Service
public class OccHealthEndangerServiceImpl implements OccHealthEndangerService {

    @Autowired(required = false)
    OccHealthEndangerDAO occHealthEndangerDAO;


    @Override
    public PageData findOccHealthEndangerByPage(JSONObject json) {
        int pageNo = json.getInteger("pageNo");
        int pageSize = json.getByteValue("pageSize");
        int recNo = pageSize * (pageNo - 1);
        int totalPage = 0;
        List<OccHealthEndanger> occHealthEndangerList =occHealthEndangerDAO.findOccHealthEndangerByPage(recNo, pageSize);
        int count =occHealthEndangerDAO.findNumOccHealthEndanger();
        if (0 == count % pageSize) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        return new PageData(pageNo, pageSize, totalPage, count, occHealthEndangerList);
    }

    @Override
    public OccHealthEndanger findOccHealthEndangerById(JSONObject json) {
        OccHealthEndanger occHealthEndanger=occHealthEndangerDAO.findOccHealthEndangerById(json.getInteger("id"));
        return occHealthEndanger;
    }

    @Override
    public void insertOccHealthEndanger(JSONObject json) {
        OccHealthEndanger occHealthEndanger= JSONObject.parseObject(json.toJSONString(), OccHealthEndanger.class);
        occHealthEndangerDAO.insertOccHealthEndanger(occHealthEndanger);
    }

    @Override
    public String deleteOccHealthEndanger(JSONArray result) {
        String reT=null;
        for (int i=0;i<result.size();i++) {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result.get(i));
            Integer Id = jsonObject.getInteger("id");
            if (occHealthEndangerDAO.findOccHealthEndangerById(Id)!=null){
                occHealthEndangerDAO.deleteOccHealthEndanger(Id);
                reT="1000";
            }else {
                return String.valueOf(Id);
            }
        }
        return reT;
    }

    @Override
    public String updateOccHealthEndanger(JSONObject json) {
        OccHealthEndanger occHealthEndanger= JSONObject.parseObject(json.toJSONString(), OccHealthEndanger.class);
        if (occHealthEndangerDAO.findOccHealthEndangerById(occHealthEndanger.getId())!=null) {
            occHealthEndangerDAO.updateOccHealthEndanger(occHealthEndanger);
            return "1000";
        }else {
            return "1006";
        }
    }
}
