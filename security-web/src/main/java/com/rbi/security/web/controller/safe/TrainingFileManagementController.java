package com.rbi.security.web.controller.safe;

import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.entity.SysUser;
import com.rbi.security.entity.web.safe.administrator.SafeAdministratorTrain;
import com.rbi.security.entity.web.safe.specialtype.PagingSpecialTraining;
import com.rbi.security.entity.web.safe.specialtype.SafeSpecialTrainingFiles;
import com.rbi.security.entity.web.user.PagingUser;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.TrainingFileManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PACKAGE_NAME: com.rbi.security.web.controller.safe
 * @NAME: TrainingFileManagementController（培训档案管理）
 * @USER: "吴松达"
 * @DATE: 2020/5/25
 * @TIME: 12:03
 * @YEAR: 2020
 * @MONTH: 05
 * @MONTH_NAME_SHORT: 5月
 * @MONTH_NAME_FULL: 五月
 * @DAY: 25
 * @DAY_NAME_SHORT: 周一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 12
 * @MINUTE: 03
 * @PROJECT_NAME: security
 **/
@RestController
@RequestMapping("/training")
public class TrainingFileManagementController {
    @Autowired
    TrainingFileManagementService trainingFileManagementService;
    /************************特种工作人员台账******************************/

    /**
     * 文件导入特种培训记录
     */

    /**
     * 增加特种培训记录
     */
    @RequestMapping("/insertSpecialTraining")
    public ResponseModel insertSpecialTraining(@RequestBody JSONObject date) {
        try{
            SafeSpecialTrainingFiles safeSpecialTrainingFiles= JSONObject.parseObject(date.toJSONString(), SafeSpecialTrainingFiles.class);
            trainingFileManagementService.insertSpecialTraining(safeSpecialTrainingFiles);
            return ResponseModel.build("1000", "添加成功");
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    /**
     * 删除特种培训记录
     */
    @RequestMapping("/deleteSpecialTraining")
    public ResponseModel deleteSpecialTraining(@RequestBody JSONObject date){

        Integer id =date.getInteger("id");
        try{

            return ResponseModel.build("1000", "删除成功");
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    /**
     * 更新特种培训记录
     */
    @RequestMapping("/updateSpecialTraining")
    public ResponseModel updateSpecialTraining(@RequestBody JSONObject date){

        try{
            SafeSpecialTrainingFiles safeSpecialTrainingFiles= JSONObject.parseObject(date.toJSONString(), SafeSpecialTrainingFiles.class);
            trainingFileManagementService.updateSpecialTraining(safeSpecialTrainingFiles);
            return ResponseModel.build("1000", "更新成功");
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    /**
     * 分页查看特种培训记录
     */
    @RequestMapping("/pagingSpecialTraining")
    public ResponseModel<PageData<PagingSpecialTraining>> getPageQueryUserInfo(@RequestBody JSONObject date){
        try {
            int pageNo = date.getInteger("pageNo");
            int pageSize = date.getInteger("pageSize");
            int startIndex=(pageNo-1)*pageSize;
            PageData<PagingSpecialTraining> data=trainingFileManagementService.pagingSpecialTraining(pageNo,pageSize,startIndex);
            return  ResponseModel.build("1000", "查询成功",data);
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    /**
     * 根据特种人员id查询记录
     */
    @RequestMapping("/getSpecialTrainingById")
    public ResponseModel<PagingSpecialTraining> getSpecialTrainingById(@RequestBody JSONObject date){
        try {
            int id = date.getInteger("id");
            PagingSpecialTraining data=trainingFileManagementService.getSpecialTrainingById(id);
            return  ResponseModel.build("1000", "查询成功",data);
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    /**************************主要负责人、安全生产管理人员培训台账****************************/
    /**
     * 文件导入特种培训记录
     */
    /**
     * 增加负责人、安全生产管理人员培训记录
     */
    @RequestMapping("/insertAdministratorTrain")
    public ResponseModel insertAdministratorTrain(@RequestBody JSONObject date) {
        try{
            SafeAdministratorTrain safeAdministratorTrain = JSONObject.parseObject(date.toJSONString(), SafeAdministratorTrain.class);
            trainingFileManagementService.insertAdministratorTrain(safeAdministratorTrain);
            return ResponseModel.build("1000", "添加成功");
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    /**
     * 删除负责人、安全生产管理人员培训记录
     */

    /**
     * 更新负责人、安全生产管理人员培训记录
     */

    /**
     * 分页查看负责人、安全生产管理人员培训记录
     */
    /*************************四级HSE教育培训台账****************************/
    /**
     * 文件导入特种培训记录
     */
    /**
     * 增加特种培训记录
     */

    /**
     * 删除特种培训记录
     */

    /**
     * 更新特种培训记录
     */

    /**
     * 分页查看特种培训记录
     */
    /*************************日常培训台账****************************/
    /**
     * 增加特种培训记录
     */

    /**
     * 删除特种培训记录
     */

    /**
     * 更新特种培训记录
     */

    /**
     * 分页查看特种培训记录
     */
}
