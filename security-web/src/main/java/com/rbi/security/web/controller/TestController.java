//package com.rbi.security.web.controller;
//
//import com.rbi.security.entity.web.safe.administrator.SafeAdministratorReview;
//import com.rbi.security.tool.ResponseModel;
//import com.rbi.security.web.ScheduleTask.AdministratorReviewSchedule;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Calendar;
//import java.util.Date;
//
///**
// * @PACKAGE_NAME: com.rbi.security.web.controller
// * @NAME: TestController
// * @USER: "谢青"
// * @DATE: 2020/6/8
// * @TIME: 22:01
// * @YEAR: 2020
// * @MONTH: 06
// * @MONTH_NAME_SHORT: 6月
// * @MONTH_NAME_FULL: 六月
// * @DAY: 08
// * @DAY_NAME_SHORT: 周一
// * @DAY_NAME_FULL: 星期一
// * @HOUR: 22
// * @MINUTE: 01
// * @PROJECT_NAME: security
// **/
//@RestController
//public class TestController {
//    @Autowired
//    AdministratorReviewSchedule administratorReviewSchedule;
//
//    @Value("${hiddenPath}")
//    private String hiddenPath;
//
//
//    @PostMapping("test1")
//    public ResponseModel test(){
//        try {
//            administratorReviewSchedule.test1();
//            System.out.println("值："+hiddenPath);
//            return null;
//        }catch (Exception e){
//            System.out.println("错误："+e);
//            return null;
//        }
//    }


//    public static void main(String[] args) {
//        Date date=new Date();
//        System.out.println("date:"+date);
//        Calendar cal=Calendar.getInstance();
//        int year = cal.get(Calendar.YEAR);
//        System.out.println("year"+year);
//
//    }


//}
