package com.rbi.security.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.safe.SafeSubjectStore;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.SafeSubjectStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName TemporarySubjectController
 * @Description TODO
 * @Author muyizg
 * @Date 2020/5/26 17:08
 * @Version 1.0
 **/
@RestController
public class SafeSubjectStoreController {

    @Autowired
    private SafeSubjectStoreService safeSubjectStoreService;


    /**
     * 查询所有题库名称（下拉菜单）
     */
    @PostMapping("/getAllSubjectStoreName")
    public ResponseModel<List<SafeSubjectStore>> getAllSubjectStoreName(){

        try {
            return ResponseModel.build("1000","查询成功",safeSubjectStoreService.getAllSafeSubjectStore());
        } catch (Exception e) {
            return ResponseModel.build("1001","查询失败",e.getMessage());
        }

    }

    /**
     * 添加题库名称
     */
    @PostMapping("/insertSubjectStoreName")
    public ResponseModel insertSubjectStoreName(@RequestBody JSONObject data){
        try {
            SafeSubjectStore safeSubjectStore = JSONObject.parseObject(data.toJSONString(),SafeSubjectStore.class);
            safeSubjectStoreService.insertSafeSubjectStore(safeSubjectStore);
            return ResponseModel.build("1000", "添加成功");
        }catch (Exception e){
            return ResponseModel.build("1001","添加失败", e.getMessage());
        }

    }

    /**
     * 修改题库名称
     */
    @PostMapping("/updateSubjectStoreName")
    public ResponseModel updateSubjectStoreName(@RequestBody JSONObject data){
        try {
            SafeSubjectStore safeSubjectStore = JSONObject.parseObject(data.toJSONString(),SafeSubjectStore.class);
            safeSubjectStoreService.updateSafeSubjectStore(safeSubjectStore);
            return ResponseModel.build("1000","修改成功");
        } catch (Exception e) {
            return ResponseModel.build("1001","修改失败", e.getMessage());
        }
    }

    /**
     * 删除题库名称
     */
    @PostMapping("/deleteSubjectStoreName")
    public ResponseModel deleteSubjectStoreName(@RequestBody JSONObject data){
        try {
            SafeSubjectStore safeSubjectStore = JSONObject.parseObject(data.toJSONString(),SafeSubjectStore.class);
            safeSubjectStoreService.deleteSafeSubjectStore(safeSubjectStore.getId());
            return ResponseModel.build("1000","删除成功");
        } catch (Exception e) {
            return ResponseModel.build("1001", "删除失败",e.getMessage());
        }
    }



}
