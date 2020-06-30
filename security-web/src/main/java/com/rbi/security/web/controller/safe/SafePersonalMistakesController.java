package com.rbi.security.web.controller.safe;

import com.alibaba.fastjson.JSONObject;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PACKAGE_NAME: com.rbi.security.web.controller.safe
 * @NAME: SafePersonalMistakesController
 * @USER: "吴松达"
 * @DATE: 2020/6/30
 * @TIME: 9:47
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 30
 * @DAY_NAME_SHORT: 周二
 * @DAY_NAME_FULL: 星期二
 * @HOUR: 09
 * @MINUTE: 47
 * @PROJECT_NAME: security
 **/
@RestController
public class SafePersonalMistakesController {
    /**
     * 分页
     * */
    @PostMapping("/findByPage")
    public ResponseModel<PageData> findByPage(@RequestBody JSONObject json){
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            PageData pageData = null;
            return ResponseModel.build("1000","分页查询成功！",pageData);
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }
}
