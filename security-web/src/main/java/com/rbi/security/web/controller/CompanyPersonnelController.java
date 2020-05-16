package com.rbi.security.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.entity.SysCompanyPersonnel;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.CompanyPersonnelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/company_personnel")
public class CompanyPersonnelController {

    private final static Logger logger = LoggerFactory.getLogger(CompanyPersonnelController.class);

    @Autowired
    CompanyPersonnelService companyPersonnelService;

    @PostMapping("/excel_import")
    public ResponseModel<Map<String, Object>> excelImport(MultipartFile file){
        try {
            if (file.getOriginalFilename().indexOf("xls")>-1||file.getOriginalFilename().indexOf("xlsx")>-1) {
                Map<String,Object> map = companyPersonnelService.excelImport(file);
                return ResponseModel.build("","导入完成",map);
            }else {
                return ResponseModel.build("1005","文件类型错误，请使用Excel文件！");
            }
        }catch (Exception e){
            logger.error("【公司人员信息Controller类】表格导入信息失败！，ERROR：{}",e);
            return ResponseModel.build("1005","服务器处理失败");
        }
    }


    @PostMapping("/query/page")
    public ResponseModel<PageData<SysCompanyPersonnel>> queryByPage(@RequestBody JSONObject jsonObject){
        try {
            PageData<SysCompanyPersonnel> pageData = companyPersonnelService.queryByPage(jsonObject);
            return ResponseModel.build("","",pageData);
        }catch (Exception e){
            logger.error("【公司人员信息Controller类】分页查询公司员工信息失败，ERROR：{}",e);
            return ResponseModel.build("", "服务器处理失败");
        }
    }

    @PostMapping("/add")
    public ResponseModel<String> add(@RequestBody JSONObject jsonObject){
        try {
            boolean isSuccess = companyPersonnelService.add(jsonObject);
            if (isSuccess){
                return ResponseModel.build("","新增成功");
            }else {
                return ResponseModel.build("","员工号或身份证号已存在");
            }
        }catch (Exception e){
            logger.error("【公司人员信息Controller类】新增公司员工信息失败，ERROR：{}",e);
            return ResponseModel.build("", "服务器处理失败");
        }
    }

    @PostMapping("/update")
    public ResponseModel<String> update(@RequestBody JSONObject jsonObject){
        try {
            boolean isSuccess = companyPersonnelService.update(jsonObject);
            if (isSuccess){
                return ResponseModel.build("","更新成功");
            }else {
                return ResponseModel.build("","员工号或身份证号已存在");
            }
        }catch (Exception e){
            logger.error("【公司人员信息Controller类】更新公司员工信息失败，ERROR：{}",e);
            return ResponseModel.build("", "服务器处理失败");
        }
    }

    @PostMapping("/delete")
    public ResponseModel<String> delete(@RequestBody JSONObject jsonObject){
        try {
            companyPersonnelService.delete(jsonObject);
            return ResponseModel.build("","删除成功");
        }catch (Exception e){
            logger.error("【公司人员信息Controller类】删除公司员工信息失败，ERROR：{}",e);
            return ResponseModel.build("", "服务器处理失败");
        }
    }

}
