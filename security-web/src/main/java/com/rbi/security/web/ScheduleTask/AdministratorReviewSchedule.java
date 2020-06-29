package com.rbi.security.web.ScheduleTask;


import com.rbi.security.entity.web.safe.administrator.SafeAdministratorReview;
import com.rbi.security.entity.web.safe.administrator.SafeAdministratorTrain;
import com.rbi.security.tool.DateUtil;
import com.rbi.security.tool.StringUtils;
import com.rbi.security.web.DAO.safe.PlatformSettingsDAO;
import com.rbi.security.web.DAO.safe.SafeAdministratorReviewDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.Date;
import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.ScheduleTask
 * @USER: "谢青"
 * @DATE: 2020/6/8
 **/

@Configuration
//@EnableScheduling
//@Service
public class AdministratorReviewSchedule {
    private static final Logger logger = LoggerFactory.getLogger(AdministratorReviewSchedule.class);
    @Autowired
    SafeAdministratorReviewDAO safeAdministratorReviewDAO;

    @Autowired
    PlatformSettingsDAO platformSettingsDAO;

    @Scheduled(cron="0/5 * * * * ? ")
    public void test1() {
        try {
            int specialDay=platformSettingsDAO.getSpecialDay();
//            System.out.println("开始");
            List<SafeAdministratorTrain>  safeAdministratorTrains = safeAdministratorReviewDAO.findAll();
            String idt  = DateUtil.date(DateUtil.FORMAT_PATTERN);
            Date nowTime = DateUtil.StringToDate(DateUtil.date(DateUtil.YMD));
            int nowYear = DateUtil.DategetYear(nowTime);
            for (int i= 0; i< safeAdministratorTrains.size(); i++) {
                String time = null;
                if (StringUtils.isNotBlank(safeAdministratorTrains.get(i).getThreeTrainingTime())) {
                    time = safeAdministratorTrains.get(i).getThreeTrainingTime();
                }
                if (StringUtils.isBlank(safeAdministratorTrains.get(i).getThreeTrainingTime())) {
                    time = safeAdministratorTrains.get(i).getTwoTrainingTime();
                }
                if (StringUtils.isBlank(safeAdministratorTrains.get(i).getTwoTrainingTime())) {
                    time = safeAdministratorTrains.get(i).getOneTrainingTime();
                }
                if (StringUtils.isBlank(time)) {
//                    System.out.println("存在培训时间为空");
                    continue;
                }
                Date date3 = null;
                try {
                    String time2 = time.substring(0, time.indexOf("至"));
                    Date date1 = DateUtil.StringToDate(time2);
                    Date date2 = DateUtil.dateAddYears(date1, 1);
                    date3 = DateUtil.dateAddDays(date2, specialDay);
                }catch (Exception e){
//                    System.out.println("时间格式错误");
                    continue;
                }
                if (date3.toString().equals(nowTime.toString())) {
                    List<SafeAdministratorReview> safeAdministratorReviews = safeAdministratorReviewDAO.findReviewBySafeAdministratorId(safeAdministratorTrains.get(i).getId());
                    int num = 0;
                    for (int j = 0; j < safeAdministratorReviews.size(); j++) {
                        Date reviewIdt = DateUtil.StringToDate(safeAdministratorReviews.get(j).getIdt());
                        int year = DateUtil.DategetYear(reviewIdt);
                        if (year == nowYear) {
                            num = 1;
                        }
                    }
                    if (num == 0) {
                        SafeAdministratorReview safeAdministratorReview = new SafeAdministratorReview();
                        safeAdministratorReview.setCompletionStatus(1);
                        safeAdministratorReview.setIdt(idt);
                        safeAdministratorReview.setSafeAdministratorId(safeAdministratorTrains.get(i).getId());
                        safeAdministratorReviewDAO.addReview(safeAdministratorReview);
//                        System.out.println("添加一项");
                    }else {
//                        System.out.println("已存在");
                    }
                }
            }
        }catch (Exception e){
            System.out.println("培训档案处理失败"+ e);
        }
    }

}