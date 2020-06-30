package com.rbi.security.web.service.imp;

import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.LearningContent;
import com.rbi.security.entity.web.LearningInformations;
import com.rbi.security.entity.web.safe.SafePersonalMistakes;
import com.rbi.security.entity.web.safe.content.SafeDataPlanDTO;
import com.rbi.security.entity.web.safe.content.SafeTrainingMaterials;
import com.rbi.security.entity.web.safe.examination.SafeAnswerRecord;
import com.rbi.security.entity.web.safe.task.SafeTrainingTasks;
import com.rbi.security.entity.web.safe.task.TestPaperInfo;
import com.rbi.security.entity.web.safe.testpaper.SafeTestQuestionOptions;
import com.rbi.security.entity.web.safe.testpaper.SafeTestQuestions;
import com.rbi.security.entity.web.safe.testpaper.TestPaper;
import com.rbi.security.tool.LocalDateUtils;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.safe.SafeAnserRecordDAO;
import com.rbi.security.web.DAO.safe.SafePersonalMistakesDAO;
import com.rbi.security.web.DAO.safe.SafeTestQaperDAO;
import com.rbi.security.web.DAO.safe.SafeTrainingTasksDAO;
import com.rbi.security.web.service.TaskManagerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    public PageData<LearningInformations> pagingLearningInformation(int pageNo, int startIndex, int pageSize) throws RuntimeException {
        try {
            Subject subject = SecurityUtils.getSubject();
            int companyPersonnelId = ((AuthenticationUserDTO) subject.getPrincipal()).getCompanyPersonnelId();
            List<LearningInformations> learningInformationList = safeTrainingTasksDAO.getLearningInformation(companyPersonnelId, startIndex, pageSize);
            int count = safeTrainingTasksDAO.getLearningInformationCount(companyPersonnelId);
            int totalPage;
            if (count % pageSize == 0) {
                totalPage = count / pageSize;
            } else {
                totalPage = count / pageSize + 1;
            }
            return new PageData<LearningInformations>(pageNo, pageSize, totalPage, count, learningInformationList);
        } catch (Exception e) {
            logger.error("分页获取自身学习信息失败，异常为{}", e);
            throw new RuntimeException("分页获取自身学习信息失败");
        }
    }

    /**
     * 根据id获取学习内容
     */
    public LearningContent getLearningContent(int id) throws RuntimeException {
        LearningContent learningContent = new LearningContent();
        try {
            List<SafeTrainingMaterials> safeTrainingMaterials = safeTrainingTasksDAO.getTrainingMaterials(id);
            for (int i = 0; i < safeTrainingMaterials.size(); i++) {
                SafeDataPlanDTO safeDataPlanDTO = new SafeDataPlanDTO();
                safeDataPlanDTO.setResourceName(safeTrainingMaterials.get(i).getResourceName());
                safeDataPlanDTO.setResourcePath(safeTrainingMaterials.get(i).getResourcePath());
                if (safeTrainingMaterials.get(i).getResourceType().equals("文件")) {
                    if (learningContent.getFile() == null) {
                        learningContent.setFile(new LinkedList<SafeDataPlanDTO>());
                    }
                    learningContent.getFile().add(safeDataPlanDTO);
                } else {
                    if (learningContent.getVideo() == null) {
                        learningContent.setVideo(new LinkedList<SafeDataPlanDTO>());
                    }
                    learningContent.getVideo().add(safeDataPlanDTO);
                }
            }
            return learningContent;
        } catch (Exception e) {
            logger.error("获取某计划学习内容失败，异常为{}", e);
            throw new RuntimeException("获取某计划学习内容失败");
        }
    }

    /**
     * 分页查看自身考试信息
     */
    public PageData<TestPaperInfo> pagingSpecialReview(int pageNo, int startIndex, int pageSize, int processingStatus) throws RuntimeException {
        try {
            Subject subject = SecurityUtils.getSubject();
            int companyPersonnelId = ((AuthenticationUserDTO) subject.getPrincipal()).getCompanyPersonnelId();
            int count = 0;
            List<TestPaperInfo> testPaperInfoList = null;
            if (processingStatus == 1) {
                testPaperInfoList = safeTrainingTasksDAO.pagingUnprocessedTestPaperInfo(companyPersonnelId, processingStatus, startIndex, pageSize);
                count = safeTrainingTasksDAO.getUnprocessedTestPaperCount(companyPersonnelId, processingStatus);
            } else {
                testPaperInfoList = safeTrainingTasksDAO.pagingProcessedTestPaperInfo(companyPersonnelId, startIndex, pageSize);
                count = safeTrainingTasksDAO.getProcessedTestPaperCount(companyPersonnelId);
            }
            int totalPage;
            if (count % pageSize == 0) {
                totalPage = count / pageSize;
            } else {
                totalPage = count / pageSize + 1;
            }
            return new PageData<TestPaperInfo>(pageNo, pageSize, totalPage, count, testPaperInfoList);
        } catch (Exception e) {
            logger.error("分页获取自身考试信息失败，异常为{}", e);
            throw new RuntimeException("分页获取自身考试信息失败");
        }
    }

    /**
     * 获取试卷内容 1:单选；2：多选；3：判断；4：填空
     */
    public TestPaper getTestPaper(int id) throws RuntimeException {
        TestPaper testPaper = null;
        List<SafeTestQuestions> safeTestQuestionsList = null;
        List<Integer> testQuestionsIds = new LinkedList<Integer>();
        List<SafeTestQuestionOptions> safeTestQuestionOptionsList = null;
        /**
         * 试卷的单选题目
         */
        List<SafeTestQuestions> singleChoiceQuestions = new LinkedList<SafeTestQuestions>();
        /**
         * 试卷的多选题目
         */
        List<SafeTestQuestions> multipleChoiceQuestions = new LinkedList<SafeTestQuestions>();
        /**
         * 试卷的判断题目
         */
        List<SafeTestQuestions> judgmentQuestions = new LinkedList<SafeTestQuestions>();
        /**
         * 试卷的填空题目
         */
        List<SafeTestQuestions> completion = new LinkedList<SafeTestQuestions>();
        try {
            testPaper = safeTestQaperDAO.getTestPaper(id);
            safeTestQuestionsList = safeTestQaperDAO.getTestQuestions(testPaper.getId());
            for (int i = 0; i < safeTestQuestionsList.size(); i++) {
                testQuestionsIds.add(safeTestQuestionsList.get(i).getId());
            }
            safeTestQuestionOptionsList = safeTestQaperDAO.getTestQuestionOptions(testQuestionsIds);
            /**
             * 整合题目与选项
             */
            for (int i = 0; i < safeTestQuestionsList.size(); i++) {
                for (int j = 0; j < safeTestQuestionOptionsList.size(); j++) {
                    if (safeTestQuestionsList.get(i).getId().intValue() == safeTestQuestionOptionsList.get(j).getSubjectId().intValue()) {
                        if (safeTestQuestionsList.get(i).getSafeTestQuestionOptionsList() == null) {
                            safeTestQuestionsList.get(i).setSafeTestQuestionOptionsList(new LinkedList<SafeTestQuestionOptions>());
                        }
                        safeTestQuestionsList.get(i).getSafeTestQuestionOptionsList().add(safeTestQuestionOptionsList.get(j));
                    }
                }
            }
            /**
             * 整合题目
             */
            for (int i = 0; i < safeTestQuestionsList.size(); i++) {
                int subjectType = safeTestQuestionsList.get(i).getSubjectType();
                switch (subjectType) {
                    case 1: {
                        singleChoiceQuestions.add(safeTestQuestionsList.get(i));
                        break; //单选
                    }
                    case 2: {
                        multipleChoiceQuestions.add(safeTestQuestionsList.get(i));
                        break; //可选
                    }
                    //你可以有任意数量的case语句
                    case 3: {
                        judgmentQuestions.add(safeTestQuestionsList.get(i));
                        break; //可选
                    }
                    case 4: {
                        completion.add(safeTestQuestionsList.get(i));
                        break; //可选
                    }

                }
            }
            testPaper.setCompletion(completion);
            testPaper.setJudgmentQuestions(judgmentQuestions);
            testPaper.setMultipleChoiceQuestions(multipleChoiceQuestions);
            testPaper.setSingleChoiceQuestions(singleChoiceQuestions);
        } catch (Exception e) {
            logger.error("获取试卷信息失败，异常为{}", e);
            throw new RuntimeException("获取试卷信息失败");
        }
        return testPaper;
    }

    /**
     * 处理考试结果
     */
    @Autowired
    SafeAnserRecordDAO safeAnserRecordDAO;
    @Autowired
    SafePersonalMistakesDAO safePersonalMistakesDAO;
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void completeTheExam(int personnelTrainingRecordId, List<SafeAnswerRecord> safeAnswerRecordList) throws RuntimeException {
        SafeTrainingTasks safeTrainingTasks = new SafeTrainingTasks();
        try {
            Subject subject = SecurityUtils.getSubject();
            int companyPersonnelId = ((AuthenticationUserDTO) subject.getPrincipal()).getCompanyPersonnelId();
            String idt = LocalDateUtils.localDateTimeFormat(LocalDateTime.now(), LocalDateUtils.FORMAT_PATTERN);
            List<SafePersonalMistakes> safePersonalMistakesList = new LinkedList<>();
            safeTrainingTasks.setId(personnelTrainingRecordId);
            Integer testResults = 0;
            for (int i = 0; i < safeAnswerRecordList.size(); i++) {
                safeAnswerRecordList.get(i).setPersonnelTrainingRecordId(personnelTrainingRecordId);
                if (safeAnswerRecordList.get(i).getAnswerResults().equals(safeAnswerRecordList.get(i).getRightKey())) {
                    safeAnswerRecordList.get(i).setCorrect(1);
                    testResults = testResults + safeAnswerRecordList.get(i).getScore();
                } else {
                    safeAnswerRecordList.get(i).setCorrect(0);
                    //做错了，存入错题表
                    if(safePersonalMistakesDAO.getSafePersonalMistakes(companyPersonnelId,safeAnswerRecordList.get(i).getTestUestionsId())==null){
                        safePersonalMistakesDAO.add(new SafePersonalMistakes(companyPersonnelId,safeAnswerRecordList.get(i).getTestUestionsId(),idt));
                    }
                }
            }
            safeTrainingTasks.setTestResults(testResults.toString());
            safeTrainingTasks.setProcessingStatus(2);
            safeTrainingTasksDAO.updateTrainingTasks(safeTrainingTasks);
            if (safeAnswerRecordList.size() != 0)
                safeAnserRecordDAO.insertAnserRecords(safeAnswerRecordList);
        } catch (Exception e) {
            logger.error("处理考试结果失败，异常为{}", e);
            throw new RuntimeException("处理考试结果失败");
        }
    }

    /**
     * 查看考试详情，根据考试结果和正确答案
     */
    public TestPaper getTheExamDetails(Integer testPapreId, Integer personnelTrainingRecordId) throws RuntimeException {
        TestPaper testPaper = null;
        try {
            Subject subject = SecurityUtils.getSubject();
            int companyPersonnelId = ((AuthenticationUserDTO) subject.getPrincipal()).getCompanyPersonnelId();
            List<SafeTestQuestions> safeTestQuestionsList = null;
            List<Integer> testQuestionsIds = new LinkedList<Integer>();
            List<SafeTestQuestionOptions> safeTestQuestionOptionsList = null;
            /**
             * 试卷的单选题目
             */
            List<SafeTestQuestions> singleChoiceQuestions = new LinkedList<SafeTestQuestions>();
            /**
             * 试卷的多选题目
             */
            List<SafeTestQuestions> multipleChoiceQuestions = new LinkedList<SafeTestQuestions>();
            /**
             * 试卷的判断题目
             */
            List<SafeTestQuestions> judgmentQuestions = new LinkedList<SafeTestQuestions>();
            /**
             * 试卷的填空题目
             */
            List<SafeTestQuestions> completion = new LinkedList<SafeTestQuestions>();
            testPaper = safeTestQaperDAO.getTestPaper(testPapreId);
            safeTestQuestionsList = safeTestQaperDAO.getTestQuestions(testPaper.getId());
            for (int i = 0; i < safeTestQuestionsList.size(); i++) {
                testQuestionsIds.add(safeTestQuestionsList.get(i).getId());
            }
            safeTestQuestionOptionsList = safeTestQaperDAO.getTestQuestionOptions(testQuestionsIds);
            /**
             * 整合题目与选项
             */
            for (int i = 0; i < safeTestQuestionsList.size(); i++) {
                for (int j = 0; j < safeTestQuestionOptionsList.size(); j++) {
                    if (safeTestQuestionsList.get(i).getId().intValue() == safeTestQuestionOptionsList.get(j).getSubjectId().intValue()) {
                        if (safeTestQuestionsList.get(i).getSafeTestQuestionOptionsList() == null) {
                            safeTestQuestionsList.get(i).setSafeTestQuestionOptionsList(new LinkedList<SafeTestQuestionOptions>());
                        }
                        safeTestQuestionsList.get(i).getSafeTestQuestionOptionsList().add(safeTestQuestionOptionsList.get(j));
                    }
                }
            }
            //根据人员培训记录id与试卷id获取答题记录
            List<SafeAnswerRecord> safeAnswerRecordList = null;
                safeAnswerRecordList = safeAnserRecordDAO.getAnserRecords(personnelTrainingRecordId, testPapreId);
                //将答题记录放在题目中去
                for (int i = 0; i < safeTestQuestionsList.size(); i++) {
                    for (int j = 0; j < safeAnswerRecordList.size(); j++) {
                        if (safeAnswerRecordList.get(j).getTestUestionsId().intValue() == safeTestQuestionsList.get(i).getId().intValue()) {
                            safeTestQuestionsList.get(i).setAnswerResults(safeAnswerRecordList.get(j).getAnswerResults());
                            safeTestQuestionsList.get(i).setCorrect(safeAnswerRecordList.get(j).getCorrect());
                        }
                    }
                }
            /**
             * 整合题目
             */
            for (int i = 0; i < safeTestQuestionsList.size(); i++) {
                int subjectType = safeTestQuestionsList.get(i).getSubjectType();
                switch (subjectType) {
                    case 1: {
                        singleChoiceQuestions.add(safeTestQuestionsList.get(i));
                        break; //单选
                    }
                    case 2: {
                        multipleChoiceQuestions.add(safeTestQuestionsList.get(i));
                        break; //可选
                    }
                    //你可以有任意数量的case语句
                    case 3: {
                        judgmentQuestions.add(safeTestQuestionsList.get(i));
                        break; //可选
                    }
                    case 4: {
                        completion.add(safeTestQuestionsList.get(i));
                        break; //可选
                    }

                }
            }
            testPaper.setCompletion(completion);
            testPaper.setJudgmentQuestions(judgmentQuestions);
            testPaper.setMultipleChoiceQuestions(multipleChoiceQuestions);
            testPaper.setSingleChoiceQuestions(singleChoiceQuestions);
        } catch (Exception e) {
            logger.error("获取考试结果详情失败，异常为{}", e);
            throw new RuntimeException("获取考试结果详情失败");
        }
        return testPaper;
    }
}
