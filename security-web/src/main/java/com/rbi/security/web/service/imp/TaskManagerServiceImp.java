package com.rbi.security.web.service.imp;

import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.safe.task.TestPaperInfo;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.safe.SafeTrainingTasksDAO;
import com.rbi.security.web.service.TaskManagerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service.imp
 * @NAME: TaskManagerImp
 * @USER: "吴松达"
 * @DATE: 2020/6/5
 * @TIME: 10:56
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 05
 * @DAY_NAME_SHORT: 周五
 * @DAY_NAME_FULL: 星期五
 * @HOUR: 10
 * @MINUTE: 56
 * @PROJECT_NAME: security
 **/
@Service
public class TaskManagerServiceImp implements TaskManagerService {
    private static final Logger logger = LoggerFactory.getLogger(TaskManagerServiceImp.class);
    @Autowired
    SafeTrainingTasksDAO safeTrainingTasksDAO;
    /**
     * 分页查看自身学习信息
     */
    /**
     * 分页查看自身考试信息
     */
    public PageData<TestPaperInfo> pagingSpecialReview(int pageNo,int startIndex, int pageSize, int processingStatus) throws RuntimeException{
           try {
               Subject subject = SecurityUtils.getSubject();
               int companyPersonnelId= ((AuthenticationUserDTO)subject.getPrincipal()).getCompanyPersonnelId();
               int count =0;
               List<TestPaperInfo> testPaperInfoList=null;
               if(processingStatus==1) {
                   testPaperInfoList = safeTrainingTasksDAO.pagingUnprocessedTestPaperInfo(companyPersonnelId, processingStatus, startIndex, pageSize);
                    count=safeTrainingTasksDAO.getUnprocessedTestPaperCount(companyPersonnelId, processingStatus);
                }else {
                   testPaperInfoList=safeTrainingTasksDAO.pagingProcessedTestPaperInfo(companyPersonnelId,startIndex,pageSize);
                   count=safeTrainingTasksDAO.getProcessedTestPaperCount(companyPersonnelId);
                }
               int totalPage;
               if (count%pageSize==0){
                   totalPage = count/pageSize;
               }else {
                   totalPage = count/pageSize+1;
               }
               return new PageData<TestPaperInfo>(pageNo,pageSize,totalPage,count,testPaperInfoList);
           }catch (Exception e){
               logger.error("分页获取自身考试信息失败，异常为{}",e);
               throw new RuntimeException("分页获取自身考试信息失败");
           }
    }
}
