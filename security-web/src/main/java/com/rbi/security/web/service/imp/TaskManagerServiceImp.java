package com.rbi.security.web.service.imp;

import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.safe.task.TestPaperInfo;
import com.rbi.security.entity.web.safe.testpaper.SafeTestQuestionOptions;
import com.rbi.security.entity.web.safe.testpaper.SafeTestQuestions;
import com.rbi.security.entity.web.safe.testpaper.TestPaper;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.safe.SafeTestQaperDAO;
import com.rbi.security.web.DAO.safe.SafeTrainingTasksDAO;
import com.rbi.security.web.service.TaskManagerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
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
    @Autowired
    SafeTestQaperDAO safeTestQaperDAO;
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
    /**
     * 获取试卷内容 1:单选；2：多选；3：判断；4：填空
     */
    public TestPaper getTestPaper(int id) throws RuntimeException{
        TestPaper testPaper=null;
        List<SafeTestQuestions> safeTestQuestionsList=null;
        List<Integer> testQuestionsIds=new LinkedList<Integer>();
        List<SafeTestQuestionOptions> safeTestQuestionOptionsList=null;
        /**
         * 试卷的单选题目
         */
        List<SafeTestQuestions> singleChoiceQuestions=new LinkedList<SafeTestQuestions>();
        /**
         * 试卷的多选题目
         */
        List<SafeTestQuestions> multipleChoiceQuestions=new LinkedList<SafeTestQuestions>();
        /**
         * 试卷的判断题目
         */
        List<SafeTestQuestions> judgmentQuestions=new LinkedList<SafeTestQuestions>();
        /**
         * 试卷的填空题目
         */
        List<SafeTestQuestions> completion=new LinkedList<SafeTestQuestions>();
        try{
            testPaper=safeTestQaperDAO.getTestPaper(id);
            safeTestQuestionsList=safeTestQaperDAO.getTestQuestions(testPaper.getId());
            for(int i=0;i<safeTestQuestionsList.size();i++){
                testQuestionsIds.add(safeTestQuestionsList.get(i).getId());
            }
            safeTestQuestionOptionsList=safeTestQaperDAO.getTestQuestionOptions(testQuestionsIds);
            /**
             * 整合题目与选项
             */
            for(int i=0;i<safeTestQuestionsList.size();i++){
                for(int j=0;j<safeTestQuestionOptionsList.size();j++){
                    if(safeTestQuestionsList.get(i).getId()==safeTestQuestionOptionsList.get(j).getSubjectId()){
                        if(safeTestQuestionsList.get(i).getSafeTestQuestionOptionsList()==null){
                            safeTestQuestionsList.get(i).setSafeTestQuestionOptionsList(new LinkedList<SafeTestQuestionOptions>());
                        }
                        safeTestQuestionsList.get(i).getSafeTestQuestionOptionsList().add(safeTestQuestionOptionsList.get(j));
                    }
                }
            }
            /**
             * 整合题目
             */
            for(int i=0;i<safeTestQuestionsList.size();i++){
                int subjectType=safeTestQuestionsList.get(i).getSubjectType();
                switch(subjectType){
                    case 1 : {
                        singleChoiceQuestions.add(safeTestQuestionsList.get(i));
                        break; //可选
                    }
                    case 2 : {
                        multipleChoiceQuestions.add(safeTestQuestionsList.get(i));
                        break; //可选
                    }
                    //你可以有任意数量的case语句
                    case 3 : {
                        judgmentQuestions.add(safeTestQuestionsList.get(i));
                        break; //可选
                    }
                    case 4 : {
                        completion.add(safeTestQuestionsList.get(i));
                        break; //可选
                    }

                }
            }
            testPaper.setCompletion(completion);
            testPaper.setJudgmentQuestions(judgmentQuestions);
            testPaper.setMultipleChoiceQuestions(multipleChoiceQuestions);
            testPaper.setSingleChoiceQuestions(singleChoiceQuestions);
        }catch (Exception e){
            logger.error("获取试卷信息失败，异常为{}",e);
            throw new RuntimeException("获取试卷信息失败");
        }
        return testPaper;
    }
}
