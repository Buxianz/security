package com.rbi.security.web.controller.health;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.rbi.security.entity.web.health.OccHealthProject;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.DAO.health.HealthProjectDAO;
import com.rbi.security.web.service.HealthProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName HealthProjectController
 * @Description TODO
 * @Author muyizg
 * @Date 2020/6/19 11:45
 * @Version 1.0
 **/
@RestController
@RequestMapping("/health_project")
public class HealthProjectController {

    @Autowired
    HealthProjectService healthProjectService;

    @PostMapping("/getPage")
    public ResponseModel<PageData<OccHealthProject>> getPageHealthPro(@RequestBody JSONObject jsonObject){
        try {
            int pageNO = jsonObject.getInteger("pageNo");
            int pageSize = jsonObject.getInteger("pageSize");
            int startIndex = (pageNO-1)*pageSize;
            PageData<OccHealthProject> pageData =healthProjectService.getPageHealthPro(pageNO,pageSize,startIndex);
            return ResponseModel.build("1000","查询成功",pageData);
        }catch (Exception e){
            return ResponseModel.build("1001",e.getMessage());
        }
    }

    @PostMapping("/insert")
    public ResponseModel insertHealthPro(@RequestBody JSONObject jsonObject){
        try {
            OccHealthProject occHealthProject = JSONObject.parseObject(jsonObject.toJSONString(),OccHealthProject.class);
            healthProjectService.insertHealthPro(occHealthProject);
            return ResponseModel.build("1000","添加成功");
        }catch (Exception e){
            return ResponseModel.build("1001","添加失败", e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseModel updateHealthPro(@RequestBody JSONObject jsonObject){
        try{
            OccHealthProject occHealthProject = JSONObject.parseObject(jsonObject.toJSONString(),OccHealthProject.class);
            healthProjectService.updateHealthPro(occHealthProject);
            return ResponseModel.build("1000","更新成功");
        }catch (Exception e){
            return ResponseModel.build("1001","更新失败",e.getMessage());
        }
    }

    @PostMapping("/delete")
    public ResponseModel deleteHealthPro(@RequestBody JSONObject jsonObject){
        try{
            List<String> ids = new ArrayList<>(Arrays.asList(jsonObject.getString("ids").split(",")));
            String idList = Joiner.on(",").join(ids).replaceAll("'","");
            healthProjectService.deleteHealthPro(idList);
            return ResponseModel.build("1000","删除成功");
        }catch (Exception e){
            return ResponseModel.build("1001","删除失败",e.getMessage());
        }
    }
}
