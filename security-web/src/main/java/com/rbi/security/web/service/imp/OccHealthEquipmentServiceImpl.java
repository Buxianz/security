package com.rbi.security.web.service.imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.health.OccHealthEquipment;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.health.OccHealthEquipmentDAO;
import com.rbi.security.web.service.OccHealthEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service.imp
 * @NAME: OccHealthEquipmentServiceImpl
 * @USER: "林新元"
 * @DATE: 2020/6/23
 * @TIME: 10:38
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 六月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 23
 * @DAY_NAME_SHORT: 星期二
 * @DAY_NAME_FULL: 星期二
 * @HOUR: 10
 * @MINUTE: 38
 * @PROJECT_NAME: security
 **/
@Service
public class OccHealthEquipmentServiceImpl implements OccHealthEquipmentService {
    @Autowired(required = false)
    OccHealthEquipmentDAO occHealthEquipmentDAO;

    @Override
    public PageData findOccHealthEquipmentByPage(JSONObject json) {
        int pageNo = json.getInteger("pageNo");
        int pageSize = json.getByteValue("pageSize");
        int recNo = pageSize * (pageNo - 1);
        int totalPage = 0;
        List<OccHealthEquipment> occHealthEquipmentList =occHealthEquipmentDAO.findOccHealthEquipmentByPage(recNo, pageSize);
        int count =occHealthEquipmentDAO.findNumOccHealthEquipment();
        if (0 == count % pageSize) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        return new PageData(pageNo, pageSize, totalPage, count, occHealthEquipmentList);
    }

    @Override
    public OccHealthEquipment findOccHealthEquipmentById(JSONObject json) {
        OccHealthEquipment occHealthEquipment=occHealthEquipmentDAO.findOccHealthEquipmentById(json.getInteger("id"));
        return occHealthEquipment;
    }

    @Override
    public String insertOccHealthEquipment(JSONObject json) {
        if (Objects.isNull(occHealthEquipmentDAO.findOccHealthEquipmentByHealthEquipmentName(json.getString("healthEquipmentName")))) {
            OccHealthEquipment occHealthEquipment = JSONObject.parseObject(json.toJSONString(), OccHealthEquipment.class);
            occHealthEquipmentDAO.insertOccHealthEquipment(occHealthEquipment);
            return "1000";
        }else {
            return "1001";
        }
    }

    @Override
    public String deleteOccHealthEquipment(JSONArray result) {
        String reT=null;
        for (int i=0;i<result.size();i++) {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result.get(i));
            Integer Id = jsonObject.getInteger("id");
            if (occHealthEquipmentDAO.findOccHealthEquipmentById(Id)!=null){
                occHealthEquipmentDAO.deleteOccHealthEquipment(Id);
                reT="1000";
            }else {
                return String.valueOf(Id);
            }
        }
        return reT;
    }

    @Override
    public String updateOccHealthEquipment(JSONObject json) {
        OccHealthEquipment occHealthEquipment= JSONObject.parseObject(json.toJSONString(), OccHealthEquipment.class);
        if (occHealthEquipmentDAO.findOccHealthEquipmentById(occHealthEquipment.getId())!=null) {
            if (!Objects.isNull(occHealthEquipmentDAO.findOccHealthEquipmentByHealthEquipmentName(occHealthEquipment.getHealthEquipmentName()))){
                if (occHealthEquipment.getId()!=occHealthEquipmentDAO.findOccHealthEquipmentByHealthEquipmentName(occHealthEquipment.getHealthEquipmentName()).getId()){
                    return "1001";
                }
            }

            occHealthEquipmentDAO.updateOccHealthEquipment(occHealthEquipment);
            return "1000";
        }else {
            return "1006";
        }
    }
}
