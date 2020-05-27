package com.rbi.security.web.controller.safe;

import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.safe.specialtype.PagingSpecialReview;
import com.rbi.security.entity.web.safe.specialtype.PagingSpecialTraining;
import com.rbi.security.entity.web.safe.specialtype.SafeSpecialReview;
import com.rbi.security.entity.web.safe.specialtype.SafeSpecialTrainingFiles;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.SpecialReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PACKAGE_NAME: com.rbi.security.web.controller.safe
 * @NAME: SpecialReviewController,特种人员复审处理模块
 * @USER: "吴松达"
 * @DATE: 2020/5/27
 * @TIME: 16:21
 * @YEAR: 2020
 * @MONTH: 05
 * @MONTH_NAME_SHORT: 5月
 * @MONTH_NAME_FULL: 五月
 * @DAY: 27
 * @DAY_NAME_SHORT: 周三
 * @DAY_NAME_FULL: 星期三
 * @HOUR: 16
 * @MINUTE: 21
 * @PROJECT_NAME: security
 **/
@RestController
public class SpecialReviewController {
    @Autowired
    SpecialReviewService specialReviewService;
    /**
     * 分页查看复审名单
     */
    @RequestMapping("/pagingSpecialReview")
    public ResponseModel<PageData<PagingSpecialReview>> pagingSpecialReview(@RequestBody JSONObject date){
        try {
            int pageNo = date.getInteger("pageNo");
            int pageSize = date.getInteger("pageSize");
            int startIndex=(pageNo-1)*pageSize;
            PageData<PagingSpecialReview> data=specialReviewService.pagingSpecialReview(pageNo,pageSize,startIndex);
            return  ResponseModel.build("1000", "查询成功",data);
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    /**
     * 处理复审情况
     */
    @RequestMapping("/handleSpecialReview")
    public ResponseModel handleSpecialReview(@RequestBody JSONObject date) {
        try{
            SafeSpecialReview safeSpecialReview=JSONObject.parseObject(date.toJSONString(), SafeSpecialReview.class);
            specialReviewService.handleSpecialReview(safeSpecialReview);
            return ResponseModel.build("1000", "处理成功");
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    /**
     * 删除复审情况
     */
    @RequestMapping("/deleteSpecialReview")
    public ResponseModel deleteSpecialReview(@RequestBody JSONObject date) {
        try{
            return ResponseModel.build("1000", "删除成功");
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    /**
     * 修改复审情况
     */
    @RequestMapping("/updateSpecialReview")
    public ResponseModel updateSpecialReview(@RequestBody JSONObject date) {
        try{
            return ResponseModel.build("1000", "更新成功");
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }

}
