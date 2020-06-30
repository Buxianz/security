package com.rbi.security.web.controller.safe;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.safe.HandlePersonalMistakes;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.SafePersonalMistakesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @Autowired
    SafePersonalMistakesService safePersonalMistakesService;
    /**
     * 分页获取自身题目
     * */
    @PostMapping("/findByPage")
    public ResponseModel<PageData> findByPage(@RequestBody JSONObject json){
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            int startIndex=(pageNo-1)*pageSize;
            return ResponseModel.build("1000","分页查询成功！",safePersonalMistakesService.findPersonalMistakesByPage(pageNo,pageSize,startIndex));
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }
    /**
     * 处理错题记录
     */
    @PostMapping("/handlePersonalMistakes")
    public ResponseModel<PageData> handlePersonalMistakes(@RequestBody JSONObject json){
        try {
            List<HandlePersonalMistakes> handlePersonalMistakes= JSONArray.parseArray(json.getJSONArray("handlePersonalMistakes").toJSONString(),HandlePersonalMistakes.class);
            safePersonalMistakesService.handlePersonalMistakes(handlePersonalMistakes);
            return ResponseModel.build("1000","处理完成！");
        }catch (Exception e){
            return ResponseModel.build("1001","处理异常");
        }
    }
}
