package com.rbi.security.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.entity.SysCompanyPersonnel;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.CompanyPersonnelService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
@RestController
@RequestMapping("/company_personnel")
public class CompanyPersonnelController {

    private final static Logger logger = LoggerFactory.getLogger(CompanyPersonnelController.class);

    @Autowired
    CompanyPersonnelService companyPersonnelService;
    @RequiresPermissions("companyStaff:import")
    @PostMapping("/excel_import")
    public ResponseModel<Map<String, Object>> excelImport(MultipartFile file){
        try {
            if (file.getOriginalFilename().indexOf("xls")>-1||file.getOriginalFilename().indexOf("xlsx")>-1) {
                Map<String,Object> map = companyPersonnelService.excelImport(file);
                return ResponseModel.build("1000","导入完成",map);
            }else {
                return ResponseModel.build("1001","文件类型错误，请使用Excel文件！");
            }
        }catch (Exception e){
            logger.error("【公司人员信息Controller类】表格导入信息失败！，ERROR：{}",e);
            return ResponseModel.build("1001","服务器处理失败");
        }
    }
//此接口被多个地方调用，有冲突，需重新设置权限
//    @RequiresPermissions("companyStaff:page")
    @PostMapping("/query/page")
    public ResponseModel<PageData<SysCompanyPersonnel>> queryByPage(@RequestBody JSONObject jsonObject){
        try {
            PageData<SysCompanyPersonnel> pageData = companyPersonnelService.queryByPage(jsonObject);
            return ResponseModel.build("1000","查询成功",pageData);
        }catch (Exception e){
            logger.error("【公司人员信息Controller类】分页查询公司员工信息失败，ERROR：{}",e);
            return ResponseModel.build("1001", "服务器处理失败");
        }
    }
    @RequiresPermissions("companyStaff:add")
    @PostMapping("/add")
    public ResponseModel<String> add(@RequestBody JSONObject jsonObject){
        try {
            boolean isSuccess = companyPersonnelService.add(jsonObject);
            if (isSuccess){
                return ResponseModel.build("1000","新增成功");
            }else {
                return ResponseModel.build("1001","员工号或身份证号已存在");
            }
        }catch (Exception e){
            logger.error("【公司人员信息Controller类】新增公司员工信息失败，ERROR：{}",e);
            return ResponseModel.build("1001", "服务器处理失败");
        }
    }
    @RequiresPermissions("companyStaff:update")
    @PostMapping("/update")
    public ResponseModel<String> update(@RequestBody JSONObject jsonObject){
        try {
            boolean isSuccess = companyPersonnelService.update(jsonObject);
            if (isSuccess){
                return ResponseModel.build("1000","更新成功");
            }else {
                return ResponseModel.build("1001","员工号或身份证号已存在");
            }
        }catch (Exception e){
            logger.error("【公司人员信息Controller类】更新公司员工信息失败，ERROR：{}",e);
            return ResponseModel.build("1001", "服务器处理失败");
        }
    }
    @RequiresPermissions("companyStaff:delete")
    @PostMapping("/delete")
    public ResponseModel<String> delete(@RequestBody JSONObject jsonObject){
        try {
            companyPersonnelService.delete(jsonObject);
            return ResponseModel.build("1000","删除成功");
        }catch (Exception e){
            logger.error("【公司人员信息Controller类】删除公司员工信息失败，ERROR：{}",e);
            return ResponseModel.build("1001", "服务器处理失败");
        }
    }

}
