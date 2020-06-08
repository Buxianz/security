package com.rbi.security.web.controller.safe;

import com.alibaba.fastjson.JSONObject;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.SafeAdministratorReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PACKAGE_NAME: com.rbi.security.web.controller.safe
 * @NAME: SafeAdministratorReviewController
 * @USER: "谢青"
 * @DATE: 2020/6/8
 * @TIME: 16:11
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 08
 * @DAY_NAME_SHORT: 周一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 16
 * @MINUTE: 11
 * @PROJECT_NAME: security
 **/
@RequestMapping("administratorReview")
@RestController
public class SafeAdministratorReviewController {
    @Autowired
    SafeAdministratorReviewService safeAdministratorReviewService;

    /**
     * 分页查看负责人、安全生产管理人员培训记录
     */
    @PostMapping("/findByPage")
    public ResponseModel<PageData> findByPage(@RequestBody JSONObject json){
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            PageData pageData = safeAdministratorReviewService.findByPage(pageNo,pageSize);
            return ResponseModel.build("1000","分页查询成功！",pageData);
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }


}
