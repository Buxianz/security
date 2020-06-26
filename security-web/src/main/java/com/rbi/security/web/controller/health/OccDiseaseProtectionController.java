package com.rbi.security.web.controller.health;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.health.OccDiseaseProtection;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.OccDiseaseProtectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PACKAGE_NAME: com.rbi.security.web.controller.health
 * @NAME: OccDiseaseProtectionController
 * @USER: "谢青"
 * @DATE: 2020/6/26
 **/


@RestController
@RequestMapping("/diseaseProtection")
public class OccDiseaseProtectionController {
    @Autowired
    OccDiseaseProtectionService occDiseaseProtectionService;

    /**
     * 分页
     * */
    @PostMapping("/findByPage")
    public ResponseModel<PageData> findByPage(@RequestBody JSONObject json){
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            PageData pageData = occDiseaseProtectionService.findByPage(pageNo,pageSize);
            return ResponseModel.build("1000","分页查询成功！",pageData);
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 添加
     **/
    @PostMapping("/add")
    public ResponseModel add(@RequestBody JSONObject json){
        try {
            OccDiseaseProtection occDiseaseProtection = JSON.toJavaObject(json,OccDiseaseProtection.class);
            occDiseaseProtectionService.add(occDiseaseProtection);
            return ResponseModel.build("1000","添加成功！");
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 修改
     **/
    @PostMapping("/update")
    public ResponseModel update(@RequestBody JSONObject json){
        try {
            OccDiseaseProtection occDiseaseProtection = JSON.toJavaObject(json,OccDiseaseProtection.class);
            occDiseaseProtectionService.update(occDiseaseProtection);
            return ResponseModel.build("1000","修改成功！");
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 删除
     **/
    @PostMapping("/delete")
    public ResponseModel delete(@RequestBody JSONObject json){
        try {
            occDiseaseProtectionService.delete(json);
            return ResponseModel.build("1000","删除成功！");
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }


}
