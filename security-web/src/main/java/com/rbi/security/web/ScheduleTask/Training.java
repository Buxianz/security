package com.rbi.security.web.ScheduleTask;

import com.rbi.security.entity.web.safe.task.SafeTrainingTasks;
import com.rbi.security.web.DAO.safe.SafeTrainingTasksDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.LinkedList;
import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.ScheduleTask
 * @NAME: Training
 * @USER: "吴松达"
 * @DATE: 2020/7/1
 * @TIME: 15:19
 * @YEAR: 2020
 * @MONTH: 07
 * @MONTH_NAME_SHORT: 7月
 * @MONTH_NAME_FULL: 七月
 * @DAY: 01
 * @DAY_NAME_SHORT: 周三
 * @DAY_NAME_FULL: 星期三
 * @HOUR: 15
 * @MINUTE: 19
 * @PROJECT_NAME: security
 **/
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class Training {
    @Autowired
    SafeTrainingTasksDAO safeTrainingTasksDAO;
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    // @Scheduled(cron = "0 0 */3 * * ?")
    /*@Scheduled(cron="0 0/2 * * * ?")
    private void configureTask() {
        try{
            List<SafeTrainingTasks> safeTrainingTaskss=new LinkedList<>();
            List<SafeTrainingTasks> safeTrainingTasksList=safeTrainingTasksDAO.getSafeTrainingTasks();

        }catch (Exception e){

        }
    }*/

}
