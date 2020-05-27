package com.rbi.security.web.ScheduleTask;

import com.rbi.security.entity.web.safe.specialtype.SafeSpecialReview;
import com.rbi.security.entity.web.safe.specialtype.SafeSpecialTrainingFiles;
import com.rbi.security.entity.web.system.SystemFile;
import com.rbi.security.tool.LocalDateUtils;
import com.rbi.security.tool.StringUtils;
import com.rbi.security.web.DAO.safe.SafeSpecialReviewDAO;
import com.rbi.security.web.DAO.safe.SafeSpecialTrainingFilesDao;
import com.rbi.security.web.service.imp.UserServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * @PACKAGE_NAME: com.rbi.security.web.ScheduleTask
 * @NAME: SaticScheduleTask
 * @USER: "吴松达"
 * @DATE: 2020/5/26
 * @TIME: 17:28
 * @YEAR: 2020
 * @MONTH: 05
 * @MONTH_NAME_SHORT: 5月
 * @MONTH_NAME_FULL: 五月
 * @DAY: 26
 * @DAY_NAME_SHORT: 周二
 * @DAY_NAME_FULL: 星期二
 * @HOUR: 17
 * @MINUTE: 28
 * @PROJECT_NAME: security
 **/
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {
    private static final Logger logger = LoggerFactory.getLogger(SaticScheduleTask.class);
    @Autowired
    SafeSpecialTrainingFilesDao safeSpecialTrainingFilesDao;
    @Autowired
    SafeSpecialReviewDAO safeSpecialReviewDAO;
    /*1.特种人员复审定时任务
      每3小时执行一次
     */
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
   // @Scheduled(cron = "0 0 */3 * * ?")
    @Scheduled(cron="0 0/2 * * * ?")
    private void configureTasks() {
        List<SafeSpecialTrainingFiles> safeSpecialTrainingFilesList=null;
        List<SafeSpecialReview> safeSpecialReviewList=new LinkedList<>();
        try{
            safeSpecialTrainingFilesList=safeSpecialTrainingFilesDao.getAllSpecialTraining();
            //筛选
            safeSpecialReviewList=screen(safeSpecialTrainingFilesList);
            //批量创建任务
            if(safeSpecialReviewList.size()!=0)
            safeSpecialReviewDAO.insertSpecialReviews(safeSpecialReviewList);
        }catch (Exception e){
            logger.error("定时巡查特种复审任务执行失败"+e);
        }
    }
    /**
     * 从list集合中筛选出符合规则的集合
     */
    List<SafeSpecialReview> screen(List<SafeSpecialTrainingFiles> safeSpecialTrainingFilesList) throws RuntimeException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String newyear=format.format(new Date());
        String idt = LocalDateUtils.localDateTimeFormat(LocalDateTime.now(), LocalDateUtils.FORMAT_PATTERN);
        List<SafeSpecialTrainingFiles> newSpecialTrainingFiles=new LinkedList<SafeSpecialTrainingFiles>();
        List<SafeSpecialReview> safeSpecialReviewList=new LinkedList<>();
        String oneReviewTime=null;
        String towReviewTime=null;
        String threeReviewTime=null;
        String fourReviewTime=null;
        String fiveReviewTime=null;
        String sixReviewTime=null;
        try {
            Date date2 = format.parse(newyear);
            for (int i = 0; i < safeSpecialTrainingFilesList.size(); i++) {
                    sixReviewTime = safeSpecialTrainingFilesList.get(i).getSixReviewTime();
                    Boolean s=StringUtils.isNotBlank(sixReviewTime);
                    if (StringUtils.isNotBlank(sixReviewTime)) {
                        if (judge(sixReviewTime)) {
                            SafeSpecialReview safeSpecialReview= new SafeSpecialReview();
                            safeSpecialReview.setSpecialPersonnelId(safeSpecialTrainingFilesList.get(i).getId());
                            safeSpecialReview.setDeadline(threeYearsTime(sixReviewTime,"yyyy-MM-dd"));
                            safeSpecialReview.setCompletionStatus(1);
                            safeSpecialReview.setIdt(idt);
                            safeSpecialReviewList.add(safeSpecialReview);
                            continue;
                        }
                    }
                    fiveReviewTime = safeSpecialTrainingFilesList.get(i).getFiveReviewTime();
                    if (StringUtils.isNotBlank(fiveReviewTime)) {
                        if (judge(fiveReviewTime)) {
                            SafeSpecialReview safeSpecialReview= new SafeSpecialReview();
                            safeSpecialReview.setSpecialPersonnelId(safeSpecialTrainingFilesList.get(i).getId());
                            safeSpecialReview.setDeadline(threeYearsTime(fiveReviewTime,"yyyy-MM-dd"));
                            safeSpecialReview.setCompletionStatus(1);
                            safeSpecialReview.setIdt(idt);
                            safeSpecialReviewList.add(safeSpecialReview);
                            continue;
                        }
                    }
                    fourReviewTime = safeSpecialTrainingFilesList.get(i).getFourReviewTime();
                    if (StringUtils.isNotBlank(fourReviewTime)) {
                        if (judge(fourReviewTime)) {
                            SafeSpecialReview safeSpecialReview= new SafeSpecialReview();
                            safeSpecialReview.setSpecialPersonnelId(safeSpecialTrainingFilesList.get(i).getId());
                            safeSpecialReview.setDeadline(threeYearsTime(fourReviewTime,"yyyy-MM-dd"));
                            safeSpecialReview.setCompletionStatus(1);
                            safeSpecialReview.setIdt(idt);
                            safeSpecialReviewList.add(safeSpecialReview);

                            continue;
                        }
                    }
                    threeReviewTime = safeSpecialTrainingFilesList.get(i).getThreeReviewTime();
                    if (StringUtils.isNotBlank(threeReviewTime)) {
                        if (judge(threeReviewTime)) {
                            SafeSpecialReview safeSpecialReview= new SafeSpecialReview();
                            safeSpecialReview.setSpecialPersonnelId(safeSpecialTrainingFilesList.get(i).getId());
                            safeSpecialReview.setDeadline(threeYearsTime(threeReviewTime,"yyyy-MM-dd"));
                            safeSpecialReview.setCompletionStatus(1);
                            safeSpecialReview.setIdt(idt);
                            safeSpecialReviewList.add(safeSpecialReview);
                            continue;
                        }
                    }
                    towReviewTime = safeSpecialTrainingFilesList.get(i).getTowReviewTime();
                    if (StringUtils.isNotBlank(towReviewTime)) {
                        if (judge(towReviewTime)) {
                            SafeSpecialReview safeSpecialReview= new SafeSpecialReview();
                            safeSpecialReview.setSpecialPersonnelId(safeSpecialTrainingFilesList.get(i).getId());
                            safeSpecialReview.setDeadline(threeYearsTime(towReviewTime,"yyyy-MM-dd"));
                            safeSpecialReview.setCompletionStatus(1);
                            safeSpecialReview.setIdt(idt);
                            safeSpecialReviewList.add(safeSpecialReview);
                            continue;
                        }
                    }
                    oneReviewTime = safeSpecialTrainingFilesList.get(i).getOneReviewTime();
                    if (StringUtils.isNotBlank(oneReviewTime)) {
                        if (judge(oneReviewTime)) {
                            SafeSpecialReview safeSpecialReview= new SafeSpecialReview();
                            safeSpecialReview.setSpecialPersonnelId(safeSpecialTrainingFilesList.get(i).getId());
                            safeSpecialReview.setDeadline(threeYearsTime(oneReviewTime,"yyyy-MM-dd"));
                            safeSpecialReview.setCompletionStatus(1);
                            safeSpecialReview.setIdt(idt);
                            safeSpecialReviewList.add(safeSpecialReview);
                            continue;
                        }
                    }
                    if(judge(safeSpecialTrainingFilesList.get(i).getDateOfIssue())){
                        SafeSpecialReview safeSpecialReview= new SafeSpecialReview();
                        safeSpecialReview.setSpecialPersonnelId(safeSpecialTrainingFilesList.get(i).getId());
                        safeSpecialReview.setDeadline(threeYearsTime(safeSpecialTrainingFilesList.get(i).getDateOfIssue(),"yyyy-MM-dd"));
                        safeSpecialReview.setCompletionStatus(1);
                        safeSpecialReview.setIdt(idt);
                        safeSpecialReviewList.add(safeSpecialReview);
                        continue;
                    }

            }
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new RuntimeException();
        }
        return safeSpecialReviewList;
    }
    /**
     * 计算两时间只差
     */
    private Boolean judge(String reviewTime) throws RuntimeException{
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        c.setTime(new Date());
        c.add(Calendar.YEAR, -3);
        Date y = c.getTime();
        String newyear=format.format(new Date());
        String year = format.format(y);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = format1.parse(year);
            Date date2 = format2.parse(newyear);
            Date date3 =format3.parse(reviewTime);
            //计算当前时间与三年前相差的天数
           int difference1= differentDays(date1,date2);
           //计算上次审核与当前时间相差的天数
           int difference2=differentDays(date3,date2);
           int difference=difference1-difference2;
           if(difference<=7){
               return true;
           }else {
               return  false;
           }
        } catch (ParseException e) {
            logger.error("判断证书是否过期失败"+e);
            throw new RuntimeException();
        }

    }

    public static int differentDays(Date date1,Date date2){
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);

        int day1 = calendar1.get(Calendar.DAY_OF_YEAR);
//        System.out.println(day1);
        int day2 = calendar2.get(Calendar.DAY_OF_YEAR);
//        System.out.println(day2);
        int year1 = calendar1.get(Calendar.YEAR);
        int year2 = calendar2.get(Calendar.YEAR);

        if (year1 != year2)  //不同年
        {
            int timeDistance = 0;
            for (int i = year1 ; i < year2 ;i++){ //闰年
                if (i%4==0 && i%100!=0||i%400==0){
                    timeDistance += 366;
                }else { // 不是闰年
                    timeDistance += 365;
                }
            }
            return  timeDistance + (day2-day1);
        }else{// 同年
            return day2-day1;
        }

    }
    /**
     *获取三年后的日期
     */

    public static String threeYearsTime(String date,String fmt) {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = format3.parse(date);
            cal.setTime(date1);
            int year = cal.get(Calendar.YEAR) + 3;//yy  直接计算年数+3
            int month = cal.get(Calendar.MONTH) + 1;//MM
            int day = cal.get(Calendar.DATE);//dd
            if (fmt.indexOf("yyyy") != -1) {
                fmt = fmt.replaceAll("yyyy", String.valueOf(year).substring(0,4));
            }
            if (fmt.indexOf("MM") != -1) {
                fmt = fmt.replaceAll("MM", month < 10 ? "0" + String.valueOf(month)
                        : String.valueOf(month));
            }
            if (fmt.indexOf("dd") != -1) {
                fmt = fmt.replaceAll("dd", day < 10 ? "0" + String.valueOf(day)
                        : String.valueOf(day));
            }
        }catch (Exception e){

        }
        return fmt;
    }
}